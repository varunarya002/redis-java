package handler.storage;

import storage.InMemoryStorage;
import storage.Storage;

public class StorageHandler
{
  private static final Storage<String, String> inMemoryStorage = new InMemoryStorage<>();

  public String get(String key) {
    String response = inMemoryStorage.get(key);
    if (response == null) {
      return "";
    }
    return response;
  }

  public void set(String key, String value) {
    inMemoryStorage.set(key, value);
  }
}
