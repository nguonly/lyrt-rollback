package evaluation.server;

/**
 * Created by nguonly on 5/10/17.
 */
public interface IChannel {
    String prepareChannelForSending(String data);
    String prepareChannelForReceiving(String data);
    int factor();
}
