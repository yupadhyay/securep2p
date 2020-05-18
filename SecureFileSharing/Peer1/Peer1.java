//package Peer1;
/**
 * Main Class
 * @author Yogesh Upadhyay
 *
 */


public class Peer1{
	public static void main(String[] args) {
		new Peer1(args[0]);
	}	
	public Peer1(String portNumber){		
		
		while(true){
		ConnectToServer connecttoserver = new ConnectToServer();
		connecttoserver.connect("127.0.0.1", Integer.parseInt(portNumber));	
		connecttoserver.sendIntialObject();
		connecttoserver.readUserCommand();	
		connecttoserver.communicateWithServer();
		}
				
	}
	
	}


