import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public final class EchoClient 
{
    public static void main(String[] args) throws Exception 
    {
        String message = "";
        Scanner scanner = new Scanner(System.in);
        
        Socket socket = new Socket("127.0.0.1", 22222);
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = socket.getOutputStream();
        PrintStream out = new PrintStream(os, true, "UTF-8");

        Runnable RetrieveMessages = () -> 
        { 
            try 
            {
                String serv_message="";
                while ((serv_message = br.readLine()) != null) 
                   System.out.println("Server> " + serv_message);
            } 
            catch (IOException ex) 
            {
                System.out.println("Lost Server Connection");
                System.exit(0);
            }
        };

        Thread getMessages = new Thread(RetrieveMessages);
        getMessages.start();

        while(true)
        {
            System.out.print("Client> ");
            message = scanner.nextLine();
            out.println(message);
            Thread.sleep(20);
            if(((message.trim()).equals("exit")))
                break;
        }

        br.close();
        out.close();
        socket.close();
    }
}











