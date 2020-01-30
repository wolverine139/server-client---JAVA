package sample;

import javafx.application.Platform;
import javafx.scene.text.Text;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

//CLIENT CODE

public abstract class NetworkConnection {
	
	private ConnThread connthread = new ConnThread();
	private Consumer<Serializable> callback;

	String turn;
	public NetworkConnection(Consumer<Serializable> callback) {
		this.callback = callback;
		connthread.setDaemon(true);
		
	}
	
	public void startConn() throws Exception{
		connthread.start();
	}
	
	public void send(Serializable data) throws Exception{
		connthread.out.writeObject(data);
	}
	
	public void closeConn() throws Exception{
	    try {
            connthread.socket.close();
        }
	    catch (Exception e){
			System.out.println("Connection did not close properly.");
        }
	}

	abstract protected String getIP();
	abstract protected int getPort();
	
	class ConnThread extends Thread{
		private Socket socket;
		private ObjectOutputStream out;
		
		public void run() {
			try(
					Socket socket = new Socket(getIP(), getPort());
					ObjectOutputStream out = new ObjectOutputStream( socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){

				this.socket = socket;
				this.out = out;
				socket.setTcpNoDelay(true);

				while(true) {
					Serializable data = (Serializable) in.readObject();
					//callback.accept(data);
					if(data.toString().equals("ArrayList")){
						FXNet.clientListID = (ArrayList<Integer>) in.readObject();
						FXNet.clientList.getItems().clear();
						for(int i : FXNet.clientListID){
							FXNet.clientList.getItems().addAll("Client "+i);
						}

					}
					else if(data.toString().equals("-)")){

						Serializable temp = (Serializable) in.readObject();
						//callback.accept("~backend~ "+temp.toString());
//						Platform.runLater(() -> {
//							FXNet.getBTN(temp.toString());
//						});

					}
					else if(data.toString().equals("-*")){
						Serializable temp1 = (Serializable) in.readObject();
						Serializable temp2 = (Serializable) in.readObject();
						Platform.runLater(() -> {
							FXNet.setPoints((Integer) temp1,(Integer) temp2);
						});
					}
					else if(data.toString().equals("-!")){
						Platform.runLater(() -> {
							FXNet.disableBTNs();
						});
					}
					else if(data.toString().equals("-&")){
						Platform.runLater(() -> {
							FXNet.enableBTNs();
						});
					}
					else if(data.toString().equals("-$")){
						Platform.runLater(() -> {
							FXNet.disableBTNs();
							FXNet.endGame();
						});
					}
					else{
						callback.accept(data);
					}

				}
				
			}
			catch(Exception e) {
				callback.accept("Connection Closed");
			}
		}
	}
	
}	

