package evaluation.client;

import evaluation.client.logger.Logger;
import evaluation.roles.AES;
import evaluation.roles.LZ;
import evaluation.roles.LZX;
import evaluation.server.AppState;
import evaluation.server.Channel;
import net.role4j.Compartment;
import net.role4j.Registry;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by nguonly on 5/10/17.
 */
public class ClientService extends Thread {
    ClientCommunicator communicator = new ClientCommunicator();
    String[] channelBehavior;
    Channel channel;
    Compartment compartment;
    int index = 0;

    public ClientService(int index, String[] channelBehavior){
        this.index = index;
        this.channelBehavior = channelBehavior;
    }

    public void run(){
        try {
            Socket socket = new Socket("127.0.0.1", 33333);
            communicator.setSocket(socket);
            String msg;

//            if(!sendAdaptation("ADP:" + channelBehavior[0])) return;

//            communicator.send("ADP:" + channelBehavior);
//            String msg = communicator.receive();
//            System.out.println(msg);
//            if(!msg.contains("ADP:OK")) {
//                return; //disconnect
//            }

            try {
//                compartment = Registry.getRegistry().newCompartment(Compartment.class);
//                compartment.activate();
//                channel = Registry.getRegistry().newCore(Channel.class);
//
//
//                adapt(channelBehavior);

                communicator.setChannel(channel);

                //int N = Integer.parseInt(msg);
                int ITERATION = 3;
                int N = 500000;

                int loop=0;
                boolean isFailed=false;
                while(loop<ITERATION) {
                    if(!sendAdaptation("ADP:"  + channelBehavior[loop])) return;

                    communicator.send("GET");
                    System.out.println("send GET");

//                    msg = communicator.receive();
//                    System.out.println(msg);

                    for (int i = 0; i < N; i++) {
                        msg = communicator.receive();
                        if(msg.contains("ROLLBACK")){
                            isFailed=true;
                            break;
                        }

                        String clientStr = socket.getInetAddress().toString() + ":" + socket.getLocalPort();
                        Logger.log(clientStr, msg.getBytes().length);
                    }
                    if(!isFailed) loop++;

//                    Thread.sleep(200);
//                    communicator.send("ADP:LZX-AES");
//                    System.out.println("Adapt to ::: LZ-AES");
//                    msg = communicator.receive();
//                    if(!msg.contains("ADP:OK")) {
//                        return; //disconnect
//                    }


                }
                communicator.send("QUIT");
                System.out.println("send: QUIT");

//                compartment.deactivate();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean sendAdaptation(String operation){
        communicator.send(operation);
        System.out.println("Adapt to ::: " + operation);
        String res = communicator.receive();
        return res.contains("ADP:OK");
    }

    public void adapt(String operation) throws Throwable {
        switch (operation){
            case "AES":
                channel.bind(AES.class);
                break;
            case "LZ":
                channel.bind(LZ.class);
                break;
            case "LZX":
                channel.bind(LZX.class);
                break;
        }
    }
}
