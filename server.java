package sample;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ServerSide {
    ArrayList<clientThread> cl = new ArrayList<clientThread>();

    Consumer<Serializable> callback;

    int port;

    serverThread s;

    clientThread c1;
    clientThread c2;

    ServerSide(int port,Consumer<Serializable> callback){
        this.port = port;
        this.callback = callback;
        s = new serverThread();
        s.start();
    }

    class serverThread extends Thread{
        int clientNum = 1;

        public void run() {

            try(ServerSocket server = new ServerSocket(port)){
                callback.accept("Connection Estb.");
                while(true) {
                    clientThread c = new clientThread(server.accept(),clientNum);
                    FXNet.setNumClients(clientNum++);
                    cl.add(c);
                    c.start();
                }
            }
            catch(Exception e) {
                callback.accept("Connection Closed Server Thread");
            }
        }
    }

    public void sendList() throws Exception{
        ArrayList<Integer> l = new ArrayList<Integer>();

        for(clientThread p: cl){

            l.add(p.clientNum);
        }
        int i = 0;
        for(clientThread p: cl){
            System.out.println("Send in array to client");

            p.out.writeObject("ArrayList");
            System.out.println("After Send in array to client");
            p.out.writeObject(l);

//            if(i != cl.indexOf(p)){
//                p.out.writeObject("Array");
//                System.out.println("After Send in array to client");
//                p.out.writeObject(l);
//            }
//            i++;

        }

    }
    class clientThread extends Thread{
        int clientNum;

        Socket socket;

        ObjectOutputStream out;
        ObjectInputStream in;

        clientThread(Socket socket,int clientNum){
            this.socket = socket;
            this.clientNum = clientNum;
        }


        public void run() {
            try(ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){

                this.out = out;
                this.in = in;
                callback.accept("Client Connected " + clientNum);
                //out.writeObject("Connected to server.");
                sendList();
                while(true) {
                    Serializable data = (Serializable) in.readObject();
                    //callback.accept((Serializable) data);

                    if(data.toString().equals("--")){
                        Serializable tem = (Serializable) in.readObject();
                        Serializable temp = (Serializable) in.readObject();
                        System.out.println("========= "+ temp.toString());
                    }

                    System.out.println("After Send in array to client thread");

                    //game goes here
                }
            }catch(Exception e) {
                callback.accept("Connection Closed Client Thread");
            }
        }
    }
}
