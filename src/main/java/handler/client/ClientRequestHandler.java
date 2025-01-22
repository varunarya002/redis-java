package handler.client;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ClientRequestHandler
{
  private final SocketChannel clientChannel;
  private ByteBuffer buffer;
  private final PayloadParser payloadParser;

  public ClientRequestHandler(SocketChannel clientChannel)
  {
    this.buffer = ByteBuffer.allocate(1024);
    this.clientChannel = clientChannel;
    this.payloadParser = new PayloadParser();
  }

  public void run() throws Exception
  {
    while (clientChannel.read(buffer) > 0) {
      buffer.flip();
      String receivedPayload =  StandardCharsets.UTF_8.decode(buffer).toString();
      buffer.clear();

      List<String> commands = payloadParser.parse(receivedPayload);
      processMessage(commands);
    }
  }

  private void processMessage(List<String> commands) throws Exception
  {
    try {
      ClientRequest clientRequest = ClientRequest.builder().withCommand(commands.removeFirst()).withArguments(commands).build();
      String response = clientRequest.execute().serialize();
      clientChannel.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      System.out.println("ERROR: " + e.getMessage());
      throw new Exception(e.getMessage());
    }
  }
}
