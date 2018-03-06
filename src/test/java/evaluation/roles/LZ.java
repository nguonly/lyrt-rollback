package evaluation.roles;

import evaluation.server.IChannel;
import net.role4j.IRole;

/**
 * Created by nguonly on 5/11/17.
 */
public class LZ implements IChannel, IRole{
    String wrapper = "<LZ>";

    public String prepareChannelForReceiving(String data){
        int idx = data.indexOf(wrapper);
        int lastIdx = data.lastIndexOf(wrapper);

        return data.substring(idx+ wrapper.length(), lastIdx);
//        return data;
    }

    public String prepareChannelForSending(String data){
        String fMsg = getPlayer(IChannel.class).prepareChannelForSending(data);
        int factor = getPlayer(IChannel.class).factor();
        double i = 1/Math.pow(2, factor); //there is no chance of divide-by-zero error
        return wrapper + fMsg + wrapper;
    }

    public int factor(){
        return 0;
    }
}
