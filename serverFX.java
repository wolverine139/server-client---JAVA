package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;



public class FXNet extends Application{

	
	private ServerSide  conn;

	public ServerSide getConn() {
		return conn;
	}

	public void setConn(ServerSide conn) {
		this.conn = conn;
	}

	static TextArea messages = new TextArea();

	private int portNumber = 0;

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public int getPortNumber() {
		return portNumber;
	}

	static Stage mainStage;

	VBox v1 = new VBox();
	VBox v2 = new VBox();
	static VBox v3 = new VBox();
	static BorderPane pane = new BorderPane();
	static HBox center = new HBox();


	//Left and Right for player points
	static Label p1PointsLabel = new Label("Player 1 Points:");
	static Text c1Points = new Text("0");
	static VBox vLeft = new VBox(p1PointsLabel,c1Points);

	static Label p2PointsLabel = new Label("Player 2 Points:");
	static Text c2Points = new Text("0");
	static VBox vRight = new VBox(p2PointsLabel,c2Points);

	static Label numClientsLabel = new Label("# of Clients Connected:");
	static Text numClients = new Text();


	static void setNumClients(Integer nClients){
		numClients.setText(nClients.toString());
	}

	public Integer getNumClients(){
		return Integer.parseInt(numClients.getText());
	}

	static void setPoints(Integer c1points, Integer c2points){
		c1Points.setText(c1points.toString());
		c2Points.setText(c2points.toString());
	}

//	static void endGame(){
//
//		Button quit = new Button("Quit");
//		Button playAgain = new Button("Play Again");
//
//		quit.setOnAction(e->{
//			mainStage.close();
//		});
//
//		playAgain.setOnAction(e -> {
//			ServerSide.c1Points = 0;
//			ServerSide.c2Points = 0;
//			messages.clear();
//			pane.setCenter(messages);
//		});
//
//		HBox hbox = new HBox();
//		hbox.getChildren().addAll(playAgain,quit);
//		hbox.setSpacing(20);
//		hbox.setAlignment(Pos.CENTER);
//
//		Platform.runLater(() ->{
//			pane.setCenter(hbox);
//		});
////		System.out.println("in the endGame");
//	}

	private Parent createContent() {
//		messages.setPrefHeight(250);
//		TextField input = new TextField();
//
//		input.setOnAction(event -> {
//			String message = "Server: ";
//			message += input.getText();
//			input.clear();
//
//			messages.appendText(message + "\n");
//			try {
//				conn.send(message);
//			}
//			catch(Exception e) {
//
//			}
//
//		});
//
//		VBox root = new VBox(20, messages, input);
//		root.setPrefSize(300, 300);
//
//		return root;

		HBox top = new HBox();

		Label portNumLabel = new Label("Port Number:");
		TextField portNum = new TextField ();
		portNum.setPromptText("eg. 5555");
		portNum.setStyle("-fx-prompt-text-fill: black");
		v1.getChildren().addAll(portNumLabel,portNum);
		v1.setSpacing(10);
		v1.setAlignment(Pos.CENTER);

		Label serverStateLabel = new Label("Server State:");
		HBox v2Hbox = new HBox();
		v2Hbox.getChildren().addAll(serverStateLabel);
		Button serverON = new Button("ON");
		Button serverOFF = new Button("OFF");
		//serverOFF.setDisable(true);

		serverON.setOnAction(e -> {
			serverOFF.setDisable(false);
			serverON.setDisable(true);
			portNum.setDisable(true);

			//more server code
			try {
				portNumber = Integer.parseInt(portNum.getText());
				conn = createServer();
//				conn.startGame();
			}
			catch (Exception i){
				serverON.setDisable(false);
				serverOFF.setDisable(true);
				portNum.setDisable(false);
				portNum.clear();
				Platform.runLater(()->{
					messages.appendText("Wrong port number." + "\n");
				});
				System.out.println("Wrong port number.");
			}
		});
		serverOFF.setOnAction(e -> {
			mainStage.close();
//			serverON.setDisable(false);
//			serverOFF.setDisable(true);
//			portNum.setDisable(false);
//			portNum.clear();
//			try{
//				stop();
//			}
//			catch (Exception i){
//
//			}
		});

		HBox v2HboxBTN = new HBox();
		v2HboxBTN.getChildren().addAll(serverON,serverOFF);
		v2HboxBTN.setSpacing(10);
		v2HboxBTN.setAlignment(Pos.CENTER);
		v2.getChildren().addAll(v2Hbox,v2HboxBTN);

		v3.getChildren().addAll(numClientsLabel,numClients);
		v3.setAlignment(Pos.CENTER);
		v3.setSpacing(10);

		vLeft.setAlignment(Pos.CENTER);
		vRight.setAlignment(Pos.CENTER);

		//center gameplay of each player

//		VBox p1Center = new VBox();
//		VBox p2Center = new VBox();
//
//		Label player1Game = new Label("Player 1 GamePlay:");
//		Label player2Game = new Label("Player 2 GamePlay:");
//
//		p1Center.getChildren().addAll(player1Game);
//		p1Center.setAlignment(Pos.CENTER);
//		p2Center.getChildren().addAll(player2Game);
//		p2Center.setAlignment(Pos.CENTER);
//
//		center.getChildren().addAll(p1Center,p2Center);
//		center.setSpacing(10);
//		center.setAlignment(Pos.CENTER);

		top.getChildren().addAll(v1,v2,v3);
		top.setAlignment(Pos.CENTER);
		top.setSpacing(10);

		//messaging box
		messages.setPrefHeight(100);
		messages.setPrefWidth(400);

		pane.setTop(top);
		pane.setLeft(vLeft);
		pane.setRight(vRight);
		pane.setCenter(messages);
		//pane.setBottom(messages);

		//pane.setPrefHeight(300);

		return pane;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();
		mainStage = primaryStage;
	}
	
//	@Override
//	public void init() throws Exception{
//		conn.startGame();
//	}
	
//	@Override
//	public void stop() throws Exception{
//		conn.closeGame();
//	}
	
	private ServerSide createServer() {
		return new ServerSide(portNumber, data-> {
			Platform.runLater(()->{
				messages.appendText(data.toString() + "\n");
			});
		});
	}

//    private ServerSide createServer() {
//        return new ServerSide(portNumber);
//    }

}
