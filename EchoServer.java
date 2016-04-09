import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer 
{
    public static void main(String[] args) throws Exception 
    {
        try (ServerSocket serverSocket = new ServerSocket(22222)) 
        {
            while(true)
            {
                Socket socket = serverSocket.accept();
                
                // does  runnable get created every time too?
                Runnable ServerConnections = () -> 
                { 
                    String message = "";
                    String address = socket.getInetAddress().getHostAddress();
                    System.out.printf("Client connected: %s%n", address);
                    BufferedReader buff_reader = null;
                    PrintStream out = null;
                    
                    try 
                    {
                        buff_reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                        out = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                    }
                    catch (IOException ex)
                    {
                        System.out.printf("Problems creating streams");
                    }

                    try
                    {
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
                    } 
                    catch (IOException ex)
                    {
                        System.out.printf("Client disconnected: %s%n", address);
                    }

                    try
                    {
                        out.close();
                        buff_reader.close();
                        socket.close();
                    } 
                    catch (IOException ex)
                    {
                        System.out.printf("A stream/socket already closed");
                    }
                };
                
                Thread thread = new Thread(ServerConnections);
                thread.start();
            }
        }
        catch (IOException ex) 
        {
            System.out.println("Error Creating Socket");
            System.exit(0);
        }
    }
}
