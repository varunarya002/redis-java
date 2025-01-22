package model.command;

import handler.server.ServerResponse;

import java.util.List;

public abstract class BaseCommand<T>
{
  public abstract ServerResponse<T> execute(List<String> arguments);
}
