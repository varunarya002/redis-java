package enums;

import model.command.BaseCommand;
import model.command.EchoCommand;
import model.command.PingCommand;

import java.util.Objects;

public enum RedisCommandEnums
{
  PING("PING") {
    @Override
    BaseCommand getInstance()
    {
      return new PingCommand();
    }
  },
  ECHO("ECHO") {
    @Override
    BaseCommand getInstance()
    {
      return new EchoCommand();
    }
  };

  private String name;
  RedisCommandEnums(String name)
  {
    this.name = name;
  }

  public static BaseCommand from(String commandName) throws Exception
  {
    for (RedisCommandEnums command : RedisCommandEnums.values()) {
      if (Objects.equals(command.name, commandName)) {
        return command.getInstance();
      }
    }

    throw new Exception("Command: " + commandName + " is not a valid command.");
  }

  abstract BaseCommand getInstance();
}
