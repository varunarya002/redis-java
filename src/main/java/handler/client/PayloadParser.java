package handler.client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PayloadParser
{
  private final String ARRAY_LENGTH_FLAG = "*";
  private final String COMMAND_LENGTH_FLAG = "$";

  public List<String> parse(String payload) throws Exception
  {
    List<String> commands = new ArrayList<>();
    String[] lines = payload.split("\r\n");
    int arrayLength = 0;
    int expectedCommandLength = 0;

    for (String line : lines) {
      if (line.startsWith(ARRAY_LENGTH_FLAG))
      {
        arrayLength = getArrayLength(line, arrayLength);
      } else if (line.startsWith(COMMAND_LENGTH_FLAG))
      {
        expectedCommandLength = extractLength(line, Pattern.compile("\\$(\\d+)"));
      } else if (!line.isEmpty())
      {
        if (expectedCommandLength != line.length()) {
          throw new IllegalArgumentException("Command length does not match the expected value");
        }
        commands.add(line);
        expectedCommandLength = 0;
      }
    }

    validateArrayLength(commands, arrayLength);
    return commands;
  }

  private int getArrayLength(String line, int currArrayLength) throws Exception
  {
    if (!line.startsWith(ARRAY_LENGTH_FLAG)) {
      return currArrayLength;
    }
    return extractLength(line, Pattern.compile("\\*(\\d+)"));
  }

  private static void validateArrayLength(List<String> commands, int arrayLength)
  {
    if (commands.size() != arrayLength) {
      throw new IllegalArgumentException("Array size does not match the number of commands");
    }
  }

  private String extractStringWithRegex(String message, Pattern pattern) throws Exception
  {
    Matcher matcher = pattern.matcher(message);

    if (!matcher.find()) {
      throw new Exception("Invalid command!");
    }
    return matcher.group(1);
  }

  private int extractLength(String message, Pattern pattern) throws Exception
  {
    return Integer.parseInt(extractStringWithRegex(message, pattern));
  }
}
