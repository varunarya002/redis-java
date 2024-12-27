package handler.client;

import handler.server.ServerResponse;
import model.command.BaseCommand;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientRequestHandler
{
  private Socket socket;
  public ClientRequestHandler(Socket socket)
  {
    this.socket = socket;
  }

  public ClientRequest run() throws Exception
  {
    try {
      InputStream inputStream = this.socket.getInputStream();
      OutputStream output = socket.getOutputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      String message;
      while ((message = reader.readLine()) != null)
      {
        if (message.startsWith("*") || message.startsWith("$")) {
          continue;
        }

        ClientRequest clientRequest = ClientRequest.builder().withCommand(message).withArguments(new ArrayList<>()).build();
        String serializeResponse = this.sendResponse(clientRequest.getCommand()).serialize();
        output.write(serializeResponse.getBytes());
        output.close();
      }
    } catch (Exception e) {
      System.out.println("ERROR: " + e.getMessage());
    }
    return null;
  }

  public ServerResponse sendResponse(BaseCommand command) {
    return command.execute();
  }

}
