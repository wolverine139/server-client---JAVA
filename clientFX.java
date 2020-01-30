package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;


public class FXNet extends Application{

	
	private NetworkConnection  conn;
	private TextArea messages = new TextArea();

	static ArrayList<Integer> clientListID = new ArrayList<Integer>();

	static ComboBox clientList = new ComboBox();

	String ip = "127.0.0.1";
	int portNumber = 5555;

	static VBox bottom = new VBox();

	static Button rock = new Button("Rock");
	static Button paper = new Button("Paper");
	static Button scissors = new Button("Scissors");
	static Button spock = new Button("Spock");
	static Button lizard = new Button("Lizard");

	static void getBTN(String b){
		System.out.println("in getbtn");
		if(b.equals("Rock")){
			p2Center.getChildren().addAll(rock);

		}
		else if(b.equals("Paper")){
			p2Center.getChildren().addAll(paper);

		}
		else if(b.equals("Scissors")){
			p2Center.getChildren().addAll(scissors);

		}
		else if(b.equals("Spock")){
			p2Center.getChildren().addAll(spock);

		}
		else if(b.equals("Lizard")){
			p2Center.getChildren().addAll(lizard);

		}

	}

	public void createBTNs(){
		//Buttons to play
		ImageView imgView1 = new ImageView(new Image("rock.png"));
		{
			imgView1.setFitWidth(50);
			imgView1.setFitHeight(50);
			imgView1.setPreserveRatio(true);
		}
		rock.setGraphic(imgView1);

		ImageView imgView2 = new ImageView(new Image("paper.jpg"));
		{
			imgView2.setFitWidth(50);
			imgView2.setFitHeight(50);
			imgView2.setPreserveRatio(true);
		}
		paper.setGraphic(imgView2);

		ImageView imgView3 = new ImageView(new Image("scissors.png"));
		{
			imgView3.setFitWidth(50);
			imgView3.setFitHeight(50);
			imgView3.setPreserveRatio(true);
		}
		scissors.setGraphic(imgView3);

		ImageView imgView4 = new ImageView(new Image("spock.jpg"));
		{
			imgView4.setFitWidth(50);
			imgView4.setFitHeight(50);
			imgView4.setPreserveRatio(true);
		}
		spock.setGraphic(imgView4);

		ImageView imgView5 = new ImageView(new Image("lizard.png"));
		{
			imgView5.setFitWidth(50);
			imgView5.setFitHeight(50);
			imgView5.setPreserveRatio(true);
		}
		lizard.setGraphic(imgView5);
	}



	static void disableBTNs(){
		rock.setDisable(true);
		paper.setDisable(true);
		scissors.setDisable(true);
		spock.setDisable(true);
		lizard.setDisable(true);
	}

	static void enableBTNs(){
		rock.setDisable(false);
		paper.setDisable(false);
		scissors.setDisable(false);
		spock.setDisable(false);
		lizard.setDisable(false);
	}

	static VBox p1Center = new VBox();
	static VBox p2Center = new VBox();

	//Left and Right for player points
	static Label p1PointsLabel = new Label("Player 1 Points:");
	static Text c1Points = new Text("0");
	static VBox vLeft = new VBox(p1PointsLabel,c1Points);

	static Label p2PointsLabel = new Label("Player 2 Points:");
	static Text c2Points = new Text("0");
	static VBox vRight = new VBox(p2PointsLabel,c2Points);


	static void setPoints(Integer c1points, Integer c2points){
		c1Points.setText(c1points.toString());
		c2Points.setText(c2points.toString());
	}

	static Stage mainStage;

	static void endGame(){

		Button quit = new Button("Quit");
		Button playAgain = new Button("Play Again");
		HBox hbox = new HBox();
		quit.setOnAction(e->{
			mainStage.close();
		});

		playAgain.setOnAction(e -> {
			c1Points.setText("0");
			c2Points.setText("0");
			bottom.getChildren().removeAll(hbox);
			enableBTNs();
		});

		hbox.getChildren().addAll(playAgain,quit);
		hbox.setSpacing(20);
		hbox.setAlignment(Pos.CENTER);

		Platform.runLater(() ->{
			bottom.getChildren().addAll(hbox);
		});
//		System.out.println("in the endGame");
	}

	private Parent createContent() {

		vLeft.setAlignment(Pos.CENTER);
		vRight.setAlignment(Pos.CENTER);


		HBox cards = new HBox();
		cards.getChildren().addAll(rock,paper,scissors,spock,lizard);
		cards.setAlignment(Pos.CENTER);
		cards.setSpacing(5);

		//server messages
		bottom.getChildren().addAll(cards);

		//center gameplay of each player
		HBox center = new HBox();


		messages.setPrefHeight(80);
		messages.setPrefWidth(400);
		center.getChildren().addAll(messages);
		center.setAlignment(Pos.CENTER);


		//Button play on action
		rock.setOnAction(e -> {
			//disableBTNs();
			//p1Center.getChildren().addAll(rock);
			try {
				conn.send(rock.getText());
			}
			catch(Exception i) {
				System.out.println("Data not sent");
			}
		});
		paper.setOnAction(e -> {
			//disableBTNs();
			//p1Center.getChildren().addAll(paper);
			try {
				conn.send(paper.getText());
			}
			catch (Exception i){
				System.out.println("Data not sent");
			}
		});
		scissors.setOnAction(e -> {
			//disableBTNs();
			//p1Center.getChildren().addAll(scissors);
			try {
				conn.send(scissors.getText());
			}
			catch (Exception i){
				System.out.println("Data not sent");
			}
		});
		spock.setOnAction(e -> {
			//disableBTNs();
			//p1Center.getChildren().addAll(spock);
			try{
				conn.send(spock.getText());
			}
			catch (Exception i){
				System.out.println("Data not sent");
			}
		});
		lizard.setOnAction(e -> {
			//disableBTNs();
			//p1Center.getChildren().addAll(lizard);
			try {
				conn.send(lizard.getText());
			}
			catch (Exception i){
				System.out.println("Data not sent");
			}
		});



		BorderPane pane = new BorderPane();
		//pane.setTop(top);
		pane.setLeft(vLeft);
		pane.setRight(vRight);
		pane.setBottom(bottom);
		pane.setCenter(center);

		pane.setPrefHeight(300);

		return pane;

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//primaryStage.setScene(new Scene(createContent()));
		primaryStage.setScene((new Scene(createClientList())));
		primaryStage.show();
		mainStage = primaryStage;
	}
	
	@Override
	public void init() throws Exception{
		createBTNs();
		//conn.startConn();
	}
	
	@Override
	public void stop() throws Exception{
		conn.closeConn();
	}




	Button challenge = new Button("Challenge");

	public Parent createClientList(){

		HBox top = new HBox();
		VBox v1 = new VBox();
		VBox v2 = new VBox();
		VBox v3 = new VBox();

		Label portNumLabel = new Label("Port Number:");
		TextField portNum = new TextField ();
		portNum.setPromptText("eg. 5555");
		portNum.setStyle("-fx-prompt-text-fill: black");
		v1.getChildren().addAll(portNumLabel,portNum);
		v1.setSpacing(10);
		v1.setAlignment(Pos.CENTER);

		Label ipAddLabel = new Label("IP Address:");
		TextField ipAddress = new TextField();
		ipAddress.setPromptText("eg. 127.0.0.1");
		ipAddress.setStyle("-fx-prompt-text-fill: black");
		v2.getChildren().addAll(ipAddLabel,ipAddress);
		v2.setSpacing(10);
		v2.setAlignment(Pos.CENTER);

		Button connect = new Button("Connect");
		connect.setOnAction(e -> {
			portNumber = Integer.parseInt(portNum.getText());
			ip = ipAddress.getText();
			conn = createClient();
			try{
				conn.startConn();
			}
			catch (Exception i){
				System.out.println("Connection did not start.");
			}
		});
		v3.getChildren().addAll(connect);
		v3.setAlignment(Pos.CENTER);

		top.getChildren().addAll(v1,v2,v3);
		top.setSpacing(10);
		top.setAlignment(Pos.CENTER);




		challenge.setOnAction(e -> {
			try {
				conn.send((Serializable) "--");

				conn.send((Serializable) clientList.getValue());
				System.out.println("000000 ---- "+clientList.getValue());
			}
			catch (Exception i){
				//data not sending
			}
		});


		VBox vBoxInside = new VBox();
		Label ListTitle = new Label("Connected Client List. Select client to challenge.");
		vBoxInside.getChildren().addAll(ListTitle,clientList);
		vBoxInside.setAlignment(Pos.CENTER);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(challenge);
		vbox.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setTop(top);
		pane.setCenter(vBoxInside);
		pane.setBottom(vbox);

		return pane;
	}






	private Client createClient() {
		return new Client(ip, portNumber, data -> {
			Platform.runLater(()->{
				messages.appendText(data.toString() + "\n");
			});
		});
	}

}
