import handler.event_loop.EventLoop;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Main {
  static int PORT = 6379;
  public static void main(String[] args){
    System.out.println("Logs from your program will appear here!");

    try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
      serverChannel.configureBlocking(false);
      serverChannel.bind(new InetSocketAddress(PORT));

      Selector selector = Selector.open();
      // Register the server channel with the selector to accept connections
      serverChannel.register(selector, SelectionKey.OP_ACCEPT);
      System.out.println("Server started. Listening on port " + PORT);

      EventLoop eventLoop = new EventLoop(selector);
      eventLoop.start();

    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
