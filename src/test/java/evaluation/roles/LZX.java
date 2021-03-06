package evaluation.roles;

import evaluation.server.IChannel;
import net.role4j.IRole;

/**
 * Created by nguonly on 5/11/17.
 */
public class LZX implements IChannel, IRole {
    String wrapper = "<LZX>";

    public String prepareChannelForReceiving(String data){
        int idx = data.indexOf(wrapper);
        int lastIdx = data.lastIndexOf(wrapper);

        return data.substring(idx+ wrapper.length(), lastIdx);
//        return data;
    }

    public String prepareChannelForSending(String data){
        String fMsg = getPlayer(IChannel.class).prepareChannelForSending(data);
        return wrapper + fMsg + wrapper;
    }

    public int factor(){return 1;}
}
