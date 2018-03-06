package evaluation.client.logger;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nguonly on 5/11/17.
 */
public class ProcessLogMain {
    public static void main(String... args) throws IOException {
        String fileName = "out.txt";
        Path path = FileSystems.getDefault().getPath(".", fileName);

        BufferedReader br = new BufferedReader(new FileReader(path.toFile()));

        //File writer
        FileWriter fwriter = new FileWriter("out_process.txt");
        BufferedWriter out = new BufferedWriter(fwriter);

        int granularity = 100; //milliseconds

        DateFormat sdf = new SimpleDateFormat("hh:mm:ss:SSS");
        Date startDate = new Date(System.currentTimeMillis());
        Date currentDate;

        long start = 0;
        long current = 0;

        int counter = 0;
        boolean resetStartDate = true;
        int sum = 0;
        String line;
        while ((line = br.readLine())!=null){
            String[] messages = line.split(" ");

            //currentDate = sdf.parse(messages[0]);
            current = Long.parseLong(messages[1]);

            if(resetStartDate) {
                //startDate = sdf.parse(messages[0]);
                start = Long.parseLong(messages[1]);
            }

            if(current - start <= 100){
                sum += Integer.parseInt(messages[3]);
                resetStartDate = false;
            }else{
                String ff = String.format("%d %d\n", ++counter, sum);
                out.write(ff);
                out.flush();
                resetStartDate = true;
                sum = 0;
            }

            //System.out.println(messages[2]);
            //sum +=
        }

        br.close();
        out.close();
    }
}
