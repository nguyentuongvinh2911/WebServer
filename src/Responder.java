import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Responder implements Runnable {
    // Variables
    private Socket requestHandler;
    private Scanner requestReader;
    private Scanner pageReader;
    private DataOutputStream pageWriter;
    private String HTTPMessage;
    private String requestedURL;
    private String requestedFile;

    public Responder(Socket requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try {
            //create Scanner to read the file of html
            requestReader = new Scanner(
                    new InputStreamReader(
                            requestHandler.getInputStream()));
            //create file writer to write log file
            File file;
            FileWriter fw = new FileWriter("Logs\\MyLogFile.log", true);
            // create timeStamp
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

            int lineCount = 0;
            // Loop through each line of the HTTP messages to get the HTTP request
            do {
                lineCount++; //This will be used later
                HTTPMessage = requestReader.nextLine();
                if (HTTPMessage.equals("GET / HTTP/1.1") || HTTPMessage.equals("GET /subdir HTTP/1.1")) {
                    HTTPMessage = "GET /Default.htm HTTP/1.1";
                }

                // Take the 1st line which contains GET /testpage.htm HTTP/1.1
                if (lineCount == 1) {
                    if (HTTPMessage.equals("GET /doSERVICE?Criteria=&Field=id&Submit=Run+Service HTTP/1.1")) {
                        requestedFile = HTTPMessage.substring(4, HTTPMessage.indexOf("HTTP/1.1") - 1);
                    } else {
                        requestedFile = "WebRoot\\"
                                + HTTPMessage.substring(5, HTTPMessage.indexOf("HTTP/1.1") - 1); // extract information from position 5 (the “t”), and ends 1 position before the beginning of “HTTP/1.1”, in this case position 17
                    }
                }
                //trim the gap of string in the requestedFile
                requestedFile = requestedFile.trim();
                //print the html file to console
                System.out.println(HTTPMessage);
                //
                try {
                    pageReader = new Scanner(new File(requestedFile));
                } catch (FileNotFoundException e) {
                    // if there is a wrong/un-existing website -> go to this error site
                    requestedFile = "WebRoot\\Util\\Error404.htm";
                }
            } while (HTTPMessage.length() != 0);
            // write log file
            fw.write(requestedFile + ": " + timeStamp);
            fw.write(System.lineSeparator());
            fw.close();
            //reads the requested file. Loops through the file and outputs it to the console.
            pageReader = new Scanner(new File(requestedFile));
            pageWriter = new DataOutputStream(requestHandler.getOutputStream());
            pageWriter.flush();
            // if a request string contains the line “doService”
            if (requestedFile.contains("doSERVICE")) {
                Service s = new SQLSelectService(pageWriter, requestedFile);
                s.doWork();
            } else { // send a page normally
                while (pageReader.hasNext()) {
                    String s = pageReader.nextLine();
                    // write web page
                    pageWriter.writeBytes(s);
                }
            }
            // When there is no line left -> Tells the Browser we’re done sending
            pageReader.close(); //stop the scanner
            pageWriter.close(); //stop outsourcing the execution to the website
            requestHandler.close();// close the socket
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("\n");
            e.printStackTrace();
        }
    }
}
