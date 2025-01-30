package enums;

import handler.command.*;
import handler.storage.StorageHandler;
import model.protocol.BulkStringProtocol;
import model.protocol.SimpleStringProtocol;

import java.util.Objects;

public enum RedisCommandEnums
{
  PING("PING") {
    @Override
    BaseCommandHandler getInstance()
    {
      return new PingCommandHandler(new SimpleStringProtocol());
    }
  },
  ECHO("ECHO") {
    @Override
    BaseCommandHandler getInstance()
    {
      return new EchoCommandHandler(new BulkStringProtocol());
    }
  },
  SET("SET") {
    @Override
    BaseCommandHandler getInstance()
    {
      return new SetCommandHandler(new SimpleStringProtocol(), new StorageHandler());
    }
  },
  GET("GET") {
    @Override
    BaseCommandHandler getInstance()
    {
      return new GetCommandHandler(new BulkStringProtocol(), new StorageHandler());
    }
  };

  private final String name;
  RedisCommandEnums(String name)
  {
    this.name = name;
  }

  public static BaseCommandHandler from(String commandName) throws Exception
  {
    for (RedisCommandEnums command : RedisCommandEnums.values()) {
      if (Objects.equals(command.name, commandName)) {
        return command.getInstance();
      }
    }

    throw new Exception("Command: " + commandName + " is not a valid command.");
  }

  abstract BaseCommandHandler getInstance();
}
