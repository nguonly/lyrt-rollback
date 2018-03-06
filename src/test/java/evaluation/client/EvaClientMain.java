package evaluation.client;

import evaluation.client.logger.Logger;

/**
 * Created by nguonly on 5/10/17.
 */
public class EvaClientMain {
    public static void main(String... args) throws InterruptedException {
        int n = 5;
        ClientService[] clients = new ClientService[n];

        Logger.getInstance();

        clients[0] = new ClientService(0, new String[]{"LZ", "AES", "LZX"});
        clients[1] = new ClientService(1, new String[]{"LZX", "LZ", "AES"});
        clients[2] = new ClientService(2, new String[]{"AES", "LZX", "LZX"});
//        clients[3] = new ClientService(3, new String[]{"LZX-AES", "LZX", "LZ"});
//        clients[4] = new ClientService(4,new String[]{"AES-LZX", "LZX-AES", "LZX"});
//
//        clients[5] = new ClientService(5, "LZ");
//        clients[6] = new ClientService(6, "LZX");
//        clients[7] = new ClientService(7,"AES");
//        clients[8] = new ClientService(8, "LZX-AES");
//        clients[9] = new ClientService(9,"AES-LZX");

        for(int i=0; i<n; i++){
            clients[i].start();
            //Thread.sleep(100);
        }
    }
}
