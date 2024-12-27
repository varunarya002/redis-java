import handler.client.ClientRequest;
import handler.client.ClientRequestHandler;
import handler.server.ServerResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    //  Uncomment this block to pass the first stage
        ServerSocket serverSocket;
        Socket clientSocket = null;
        int port = 6379;
        try {
          serverSocket = new ServerSocket(port);
          // Since the tester restarts your program quite often, setting SO_REUSEADDR
          // ensures that we don't run into 'Address already in use' errors
          serverSocket.setReuseAddress(true);
          // Wait for connection from client.
          clientSocket = serverSocket.accept();
          System.out.println("Client connected!");

          ClientRequest clientRequest = ClientRequestHandler.deserialize(clientSocket.getInputStream());

          OutputStream output = clientSocket.getOutputStream();
          PrintWriter writer = new PrintWriter(output, true);

          String serializeResponse = ClientRequestHandler.sendResponse(clientRequest.getCommand()).serialize();
          writer.print(serializeResponse);
          writer.close();
        } catch (IOException e) {
          System.out.println("IOException: " + e.getMessage());
        } catch (Exception e)
        {
          throw new RuntimeException(e);
        } finally {
          try {
            if (clientSocket != null) {
              clientSocket.close();
            }
          } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
          }
        }
  }
}
