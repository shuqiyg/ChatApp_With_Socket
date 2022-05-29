/********************************************** 
Workshop # 11
Course: JAC433
Last Name:Yang
First Name:Shuqi
ID:132162207
Section:NBB 
This assignment represents my own work in accordance with Seneca Academic Policy. 
Signature 
Date:2022-04-18
**********************************************/ 
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatBox extends Application{

	private boolean isServer = false;
	private TextArea group_msgs = new TextArea();
	private Client client;
	private Server server;
	private String msgToSend, clientName;
	//Parent is a base class for all nodes that handles all hierarchical scene graph operations
	private Parent createContent() throws IOException {
		group_msgs.setPrefHeight(550);
		TextField input = new TextField();
		input.setMinHeight(50);
		input.setPromptText("Please Enter Your Name here");
		input.clear();
		createConnection(isServer); 
		if(isServer) {
			server.startServer(group_msgs);
		}else {
			client.listenForMessage(group_msgs);
		}
		if(msgToSend == null) {
			group_msgs.appendText("Your Name: ");
		}
		input.setOnAction(e->{
			input.setPromptText("");
			if(!isServer) {			
				msgToSend = (input.getText());
				if(clientName == null) {
					clientName = msgToSend;
					group_msgs.appendText(msgToSend + "\n");
				}
				//The very first message to send is the User Name/Client Name
				client.sendMessage(group_msgs, msgToSend);
				input.clear();
			}
		});
				
		VBox root = new VBox(20, group_msgs, input);
		root.setPrefSize(400, 400);
		return root; 
	}
	
	public void createConnection(boolean isServer) throws IOException{
		if(isServer) {
			this.server = new Server(new ServerSocket(1234));
		}else {
			this.client = new Client(new Socket("localhost", 1234));		
		}
	}
	
	public static void main(String[] args) {	
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();
	}
}
