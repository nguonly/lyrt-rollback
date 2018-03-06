package evaluation.client.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nguonly on 5/11/17.
 */
public class Logger {
    static BufferedWriter out = null;

    static Logger logger = null;

    public synchronized static Logger getInstance(){
        if(logger==null) {
            logger = new Logger();

            //Open file
            FileWriter fwriter = null;
            try {
                fwriter = new FileWriter("out.txt", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            out = new BufferedWriter(fwriter);
        }
        return logger;
    }

    public static void log(String client, long msgSize)  {
        long millis = System.currentTimeMillis();
//        long second = (millis / 1000) % 60;
//        long minute = (millis / (1000 * 60)) % 60;
//        long hour = (millis / (1000 * 60 * 60)) % 24;
//
//        String time = String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);

        Date date = new Date(millis);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
        String time = formatter.format(date);

//        String log = String.format("%s %s %d\n", time, client, msgSize);
        String log = String.format("%s %d %s %d\n", time, millis, client, msgSize);
        try {
            out.write(log);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeFile(){
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
