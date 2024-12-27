package enums;

import model.command.BaseCommand;
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
        BaseCommand commandInstance = command.getInstance();
        commandInstance.validate();
        return commandInstance;
      }
    }

    throw new Exception("Command: " + commandName + " is not a valid command.");
  }

  abstract BaseCommand getInstance();
}
