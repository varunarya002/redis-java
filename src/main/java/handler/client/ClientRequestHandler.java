package handler.client;

import java.util.ArrayList;

public class ClientRequestHandler
{
  public static String processMessage(String message) throws Exception
  {
    try {
        if (message.startsWith("*") || message.startsWith("$")) {
          return null;
        }

        ClientRequest clientRequest = ClientRequest.builder().withCommand(message).withArguments(new ArrayList<>()).build();
        return clientRequest.getCommand().execute().serialize();
    } catch (Exception e) {
      System.out.println("ERROR: " + e.getMessage());
      throw new Exception(e.getMessage());
    }
  }
}
