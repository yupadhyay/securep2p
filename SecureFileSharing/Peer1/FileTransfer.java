//package Peer1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

class FileTransfer implements Runnable{
private ServerSocket serversocket=null;
private Socket clientsocket=null;
private ConnectToServer conn;
private StringBuffer sb = new StringBuffer();
private Date date = new Date();
private String time = "\n"+date.toString()+this.getClass().getName()+"   ";
public FileTransfer(int portNumber,ConnectToServer conn){
	this.conn=conn;
	sb.append(time+"Local Port is "+portNumber);
	try {
		
		serversocket = new ServerSocket(portNumber);
				
	} catch (IOException e) {
		System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		return;
	}
	sb.append(time+"starting new thread to handle peer request");
	new Thread(this).start();
	
}
public void run(){
while(true){
try {
	clientsocket=serversocket.accept();
	System.out.println("Accepted Connection From  "+clientsocket.getPort());
	new ConnectToAnotherPeer(clientsocket,this.conn);
} catch (IOException e) {
	// TODO Auto-generated catch block
	System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
}
}

	
}

}
