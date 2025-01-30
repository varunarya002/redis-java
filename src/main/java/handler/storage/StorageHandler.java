package handler.storage;

import model.storage.InMemoryStorage;
import model.storage.Storage;
import model.storage.value.SingleValueStorage;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class StorageHandler
{
  private static final Storage<String, SingleValueStorage<String>> inMemoryStorage = new InMemoryStorage<>();
  private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  private static final ExecutorService workerExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  private static final AtomicBoolean isProcessRunning = new AtomicBoolean(false);

  static {
    scheduler.scheduleAtFixedRate(StorageHandler::cleanupExpiredKeys, 0, 1, TimeUnit.MINUTES);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      scheduler.shutdown();
      workerExecutor.shutdown();
      try {
        if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
          System.err.println("Scheduler did not terminate in time.");
        }
      } catch (InterruptedException e)
      {
        System.err.println("Scheduler shutdown interrupted.");
        Thread.currentThread().interrupt();
      }
    }));
  }

  private static boolean isKeyExpired(Date createdAt, long ttl) {
    if (ttl == -1) return false;
    return new Date().getTime() - createdAt.getTime() > ttl;
  }

  public String get(String key) {
    SingleValueStorage<String> valueStorage = inMemoryStorage.get(key);
    if (valueStorage == null) return "";

    if (isKeyExpired(valueStorage.getCreatedAt(), valueStorage.getTtl())) {
      inMemoryStorage.remove(key);
      return "";
    }
    return valueStorage.getValue();
  }

  public void set(String key, String value, long ttl) {
    inMemoryStorage.set(key, SingleValueStorage.<String>builder().withValue(value).withTtl(ttl).build());
  }

  private static void processKeysFromQueue(ConcurrentLinkedQueue<String> queue) {
    while (!queue.isEmpty()) {
      String key = queue.poll();
      if (key == null) break;

      SingleValueStorage<String> valueStorage = inMemoryStorage.get(key);
      if (valueStorage != null && isKeyExpired(valueStorage.getCreatedAt(), valueStorage.getTtl())) {
        inMemoryStorage.remove(key);
        System.out.println("Removed key: " + key + " from storage.");
      }
    }
  }

  private static void cleanupExpiredKeys() {
    if (!isProcessRunning.compareAndSet(false, true)) {
      System.out.println("Cleanup already in progress. Skipping.");
      return;
    }
    try {
      System.out.println("Cleanup started.");
      ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>(inMemoryStorage.getAllKeys());

      int availableWorkers = Runtime.getRuntime().availableProcessors();
      CountDownLatch latch = new CountDownLatch(availableWorkers);

      for (int i = 0; i < availableWorkers; i++) {
        workerExecutor.submit(() -> {
          try {
            processKeysFromQueue(queue);
          } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
          }
        latch.countDown();
        });
      }

      latch.await();
      System.out.println("Cleanup completed.");
    } catch (Exception e)
    {
      Thread.currentThread().interrupt();
    } finally
    {
      isProcessRunning.set(false);
    }
  }
}
