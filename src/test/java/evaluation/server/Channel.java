package evaluation.server;

import net.role4j.IPlayer;

/**
 * Created by nguonly on 5/10/17.
 */
public class Channel implements IChannel, IPlayer {
    public String prepareChannelForSending(String data){
        return data;
    }

    public String prepareChannelForReceiving(String data){ return data;}

    public int factor(){
        return 10;
    }
}
