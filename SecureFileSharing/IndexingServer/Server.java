//package IndexingServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Main Server class where connection is done
 * @author yogi
 *
 */

public class Server implements Runnable {
private ServerSocket serversocket=null; 
private Socket clientsocket = null;
public static void main(String[] args) {
	new Server(args[0]);
}
public Server(String portNumber){
	
	try {
		serversocket = new ServerSocket(Integer.parseInt(portNumber));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("server listing on port  " +serversocket.getLocalPort());
	new Thread(this).start();	
	
}

public void run(){
	System.out.println("Waiting for connection");
	while(true){
	try {
		clientsocket = serversocket.accept();
		System.out.println("Accepted connection from  "+clientsocket.getInetAddress()+"  "+clientsocket.getPort());
		new Connect(clientsocket);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
}
	
	
}
