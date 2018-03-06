package evaluation.client;

import evaluation.server.AppState;
import evaluation.server.Channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by nguonly on 5/10/17.
 */
public class ClientCommunicator {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private Channel channel;

    public void setSocket(Socket socket) {
        this.socket = socket;

        try {
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            output = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void setChannel(Channel channel){
        this.channel = channel;
    }

    public void send(String data) {
        //String fMsg = AppState.channel.prepareChannelForSending(data);
        output.println(data);
    }

    public String receive() {
        try {
            String data = input.readLine();
            return data;
//            return channel.prepareChannelForReceiving(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
