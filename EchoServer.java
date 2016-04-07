
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class EchoServer 
{
    public static void main(String[] args) throws Exception 
    {
        try (ServerSocket serverSocket = new ServerSocket(22222)) 
        {
            BufferedReader buff_reader = null;
           // OutputStreamWriter out_writer = null;
           // BufferedWriter buff_writer = null;
            //PrintWriter print_writer = null;
            PrintStream out = null;
            String message = "";

            Socket socket = serverSocket.accept();
            String address = socket.getInetAddress().getHostAddress();
            System.out.printf("Client connected: %s%n", address);
            
            buff_reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new PrintStream(socket.getOutputStream(), true, "UTF-8");
            //out_writer = new OutputStreamWriter(os, "UTF-8");
           // buff_writer = new BufferedWriter(out_writer);
           // print_writer = new PrintWriter(buff_writer);
            try{
                while ((message = buff_reader.readLine()) != null) 
                {

                    if ((message.trim()).equals("exit"))
                    {
                        System.out.printf("Client disconnected: %s%n", address);
                        break;
                    }
                    else
                        out.println(message);
                }
            } catch (IOException ex) {
                System.out.println("User has disconnected");
            }
            //finally
           // {
                System.out.println("close");
                out.close();
                buff_reader.close();
                socket.close();
            //}
        }
    }
}
