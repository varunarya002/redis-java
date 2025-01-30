package handler.command;

import model.server.ServerResponse;
import model.protocol.BaseProtocol;

import java.util.List;

public abstract class BaseCommandHandler<T>
{
  protected final BaseProtocol<String> protocol;

  public BaseCommandHandler(BaseProtocol<String> protocol)
  {
    this.protocol = protocol;
  }

  protected ServerResponse<T> buildResponse(T payload) {
    return ServerResponse.<T>builder().withPayload(payload).withProtocol(protocol).build();
  }

  public abstract ServerResponse<T> execute(List<String> arguments);
}
