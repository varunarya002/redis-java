package handler.client;

import enums.RedisCommandEnums;
import model.command.BaseCommand;

import java.util.ArrayList;

public class ClientRequest
{
  private BaseCommand command;
  private ArrayList<String> arguments;

  private ClientRequest(Builder builder)
  {
    this.command = builder.command;
    this.arguments = builder.arguments;
  }

  public static Builder builder() {
    return new Builder();
  }

  public BaseCommand getCommand()
  {
    return command;
  }

  public static class Builder {
    private BaseCommand command;
    private ArrayList<String> arguments = new ArrayList<>();

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

    public Builder withArguments(ArrayList<String> arguments)
    {
      this.arguments = arguments;
      return this;
    }

    public ClientRequest build() {
      return new ClientRequest(this);
    }
  }
}
