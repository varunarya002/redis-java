package handler.event_loop;

import handler.client.ClientRequestHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class EventLoop
{
  private final Selector selector;

  public EventLoop(Selector selector) throws IOException
  {
    this.selector = selector;
  }

  public void start() throws Exception
  {
    while (true) {
      selector.select();

      Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        iterator.remove();

        if (!key.isValid()) {
          continue;
        }

        if (key.isAcceptable()) {
          handleAccept(key);
        } else if (key.isReadable()) {
          handleRead(key);
        }
      }
    }
  }

  private void handleAccept(SelectionKey key) throws IOException
  {
    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
    SocketChannel clientChannel = serverChannel.accept();
    clientChannel.configureBlocking(false);
    clientChannel.register(selector, SelectionKey.OP_READ);
    System.out.println("Accepted new connection from client: " + clientChannel.getRemoteAddress());
  }

  private void handleRead(SelectionKey key) throws Exception
  {
    SocketChannel clientChannel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    StringBuilder messageBuilder = new StringBuilder();

    while (clientChannel.read(buffer) > 0) {
      buffer.flip();
      while (buffer.hasRemaining()) {
        char ch = (char) buffer.get();
        if (ch == '\n') {
          String response = ClientRequestHandler.processMessage(messageBuilder.toString().trim());
          if (response != null) {
            clientChannel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
          }
          messageBuilder.setLength(0);
        } else {
          messageBuilder.append(ch);
        }
      }
      buffer.clear();
    }
  }
}
