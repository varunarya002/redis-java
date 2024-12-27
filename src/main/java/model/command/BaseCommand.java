package model.command;

import handler.server.ServerResponse;

public abstract class BaseCommand
{
  public abstract void validate() throws Exception;

  public abstract ServerResponse execute();
}
