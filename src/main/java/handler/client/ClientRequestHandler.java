package handler.client;

import handler.server.ServerResponse;
import model.command.BaseCommand;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientRequestHandler
{
  private ClientRequestHandler()
  {
  }

  private static void validateInput(Queue<String> messageList) throws Exception
  {
    if (messageList.isEmpty()) {
      throw new Exception("Empty command passed!");
    }
  }

  public static String extractValue(String text, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(text);

    if (matcher.find()) {
      return  matcher.group(1);
    } else {
      throw new IllegalArgumentException("Invalud input string.");
    }
  }

  public static int extractNumber(String text, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(text);

    if (matcher.find()) {
      String numberString = matcher.group(1);
      try {
        return Integer.parseInt(numberString);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Extracted number is not a valid integer: " + numberString);
      }
    } else {
      throw new IllegalArgumentException("No number found in the input string.");
    }
  }

  private static ClientRequest extractCommandWithArguments(Queue<String> messageList) throws Exception
  {
    int totalParameters = extractNumber(messageList.poll(), "\\*(\\d+)");

    int commandLength = extractNumber(messageList.poll(), "\\$(\\d+)");
    String command = extractValue(messageList.poll(), "^(.+?)(?:)?$");

    if (commandLength != command.length()) {
      throw new Exception("Actual command length does not match with the given command length!");
    }

    ArrayList<String> arguments = new ArrayList<>();

    while (!messageList.isEmpty()) {
      int argumentLength = extractNumber(messageList.poll(), "\\$(\\d+)\\r\\n");
      String argument = extractValue(messageList.poll(), "^(.+?)(?:\\r\\n)?$");

      if (argumentLength != argument.length()) {
        throw new Exception("Actual argument length does not match with the given argument length!");
      }

      arguments.add(argument);
    }

    return ClientRequest.builder().withCommand(command).withArguments(arguments).build();
  }

  public static ClientRequest deserialize(InputStream input) throws Exception
  {
    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    Queue<String> messageList = new LinkedList<>();

    String message;
    while ((message = reader.readLine()) != null)
    {
      messageList.add(message);
    }

    validateInput(messageList);
    return extractCommandWithArguments(messageList);
  }

  public static ServerResponse sendResponse(BaseCommand command) {
    return command.execute();
  }

}
