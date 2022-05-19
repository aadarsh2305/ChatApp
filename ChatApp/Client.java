import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;

public class Client extends JFrame{
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Client(){
        try {
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("Connection established");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
        public void startReading(){
            Runnable r1=()->{
                System.out.println("Writer Started");
                try{
                while(true){
                    String msg=br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server :"+msg);
                }
                }
                catch (Exception e) {
                    System.out.println("Connection Closed");
                }
            };
            new Thread(r1).start();
        }
        public void startWriting(){
            Runnable r2=()->{
                try {
                while(!socket.isClosed()){
                        BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                        String content=br1.readLine();
                        out.println(content);
                        out.flush();
                    } 
                }
                catch (Exception e) {
                    System.out.println("Connection Closed");
                }
            };
            new Thread(r2).start();
    
        }
    public static void main(String[] args) {
        System.out.println("This is client ...");
        new Client();
    }
}
