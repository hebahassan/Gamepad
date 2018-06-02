package wifi_connection.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class CommunicationThread implements Runnable {

	  private Socket clientSocket;
	  private BufferedReader input;
	  public String read ;
	  
	  public CommunicationThread(Socket clientSocket) {
		  this.clientSocket = clientSocket;		  
		  try {
			  this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		  } catch (IOException e) { 
			  e.printStackTrace();
		  }
	  }
			  
	  public void run() {
		  while (!Thread.currentThread().isInterrupted()) {
			  try {
				   read = input.readLine();
			  } catch (IOException e) { 
				  e.printStackTrace();
			  }
		  }
	  }
	  
	  public String getRead(){
		  return this.read;
	  }
			  
}			  
