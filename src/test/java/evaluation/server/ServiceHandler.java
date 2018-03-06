package evaluation.server;

import evaluation.roles.AES;
import evaluation.roles.LZ;
import evaluation.roles.LZX;
import net.role4j.Compartment;
import net.role4j.Registry;
import net.role4j.rollback.AdaptationTransaction;

import java.net.Socket;

/**
 * Created by nguonly on 5/10/17.
 */
public class ServiceHandler extends Thread {
    private Socket client;
    private Communicator communicator;

    public ServiceHandler(Socket client){
        this.client = client;
        this.communicator = new Communicator();
        this.communicator.setSocket(this.client);
    }

    public void run(){
        //Thread.currentThread().setUncaughtExceptionHandler(new BugSensor(client));
        Compartment compartment = null;
        try {
            compartment = Registry.getRegistry().newCompartment(Compartment.class);
            compartment.activate();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        while(true) {
            String msg = communicator.receive();
            System.out.println(msg);

            if(msg.equals("QUIT")) {
                try {
                    compartment.deactivate();
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                break;
            }else if(msg.contains("ADP")) {

                processCommand(msg); //Adaptation

                communicator.send("ADP:OK");
            }else if(msg.contains("GET")){
                int N = 500000;
                for (int i = 0; i < N; i++) {
                    communicator.send("Data");
                    //Thread.sleep(10);
                }
            }

//            msg = communicator.receive();
//            System.out.println("receive:" + msg);

            //int ITERATION = 3;


//        for(int j=0; j<ITERATION; j++) {
//            communicator.send("" + N);
//
//
//            //Adaptation
//            msg = communicator.receive();
//            System.out.println(msg);
//            processCommand(msg);
//        }
//


        }

    }

    private void processCommand(String command) {
        String[] commands = command.split(":");
        if(commands[0].equals("ADP")){
            //Thread.sleep(100);
            adapt(commands[1]);
            System.out.println(commands[1]);
        }
    }

    public void adapt(String operation)  {
        //TODO: Switch to automate
        try {
            AppState.channel.unbindAll();
            try (AdaptationTransaction ac = new AdaptationTransaction()) {
                switch (operation) {
                    case "AES":
                        AppState.channel.bind(AES.class);
                        break;
                    case "LZ":
                        AppState.channel.bind(LZ.class);
                        break;
                    case "LZX":
                        AppState.channel.bind(LZX.class);
                        break;
                    case "LZ-AES":
                        AppState.channel.bind(LZ.class).bind(AES.class);
                        break;
                    case "AES-LZ":
                        AppState.channel.bind(AES.class).bind(LZ.class);
                        break;
                    case "LZX-AES":
                        AppState.channel.bind(LZX.class).bind(AES.class);
                        break;
                    case "AES-LZX":
                        AppState.channel.bind(AES.class).bind(LZX.class);
                        break;
                }
            }
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
}
