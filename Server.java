import java.io.*;
import java.net.*;
 


public class Server {
 
    public static void main(String[] args) {
        if (args.length < 1) return;
 
        int port = Integer.parseInt(args[0]);
 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // create a new thread object
                ServerThread clientSock = new ServerThread(socket);
 
                new Thread(clientSock).start();
                
                
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
} 

 class ServerThread extends Thread {
    private Socket socket;
 
    public ServerThread(Socket socket) {
        this.socket = socket;
        
    }
 
    public void run() {

        
        try {
            
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            
 
            
            String text;
 
            do {
                text = reader.readLine(); // reads text from client
                Process p = Runtime.getRuntime().exec(text);
                BufferedReader stdout = new BufferedReader(new InputStreamReader(p.getInputStream()));

                
                

                String outputLine;
                while ((outputLine = stdout.readLine()) != null) { // while serverMsg is not empty keep printing
                    writer.println(outputLine);

                    
            
                    
                }

                
                stdout.close();

                writer.println("ENDCMD");
                // Text here should just write back directly what the server is reading...?
            }

            

            while (!text.toLowerCase().equals("exit"));

            
 
            socket.close();
        } 
        
        
        
        catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }

        
    }
}