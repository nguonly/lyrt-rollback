package evaluation.server;

import net.role4j.Registry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nguonly on 5/10/17.
 */
public class EvaServerMain {


    public EvaServerMain() throws IOException {
    }

    public static void main(String... args) throws Throwable {
        Registry reg = Registry.getRegistry();
        AppState.channel = reg.newCore(Channel.class);
        ServerSocket serverSocket = new ServerSocket(33333);

        while (true) {
            System.out.println("Waiting for client...");

            Socket client = serverSocket.accept();
            System.out.println("Got connection from " + client.getInetAddress().toString() + " port:" + client.getPort() + "\n");

            String clientName = client.getInetAddress() + ":" + client.getPort();
            ServiceHandler handler = new ServiceHandler(client);
            handler.setName(clientName);
            handler.start();
        }
    }
}
