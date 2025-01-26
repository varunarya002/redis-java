package handler.client;

import enums.RedisCommandEnums;
import handler.server.ServerResponse;
import handler.command.BaseCommandHandler;

import java.util.ArrayList;
import java.util.List;

public class ClientRequest
{
  private BaseCommandHandler command;
  private List<String> arguments;

  private ClientRequest(Builder builder)
  {
    this.command = builder.command;
    this.arguments = builder.arguments;
  }

  public static Builder builder() {
    return new Builder();
  }

  public ServerResponse execute() {
    return command.execute(arguments);
  }

  public static class Builder {
    private BaseCommandHandler command;
    private List<String> arguments = new ArrayList<>();

    public Builder withCommand(String command) throws Exception
    {
      this.command = RedisCommandEnums.from(command.toUpperCase());
      return this;
    }

    public Builder withArgument(String argument)
    {
      this.arguments.add(argument);
      return this;
    }

    public Builder withArguments(List<String> arguments)
    {
      this.arguments = arguments;
      return this;
    }

    public ClientRequest build() {
      return new ClientRequest(this);
    }
  }
}
