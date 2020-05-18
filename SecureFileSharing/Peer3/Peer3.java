//package Peer2;


public class Peer3{
	public static void main(String[] args) {
		new Peer3(args[0]);
	}	
	public Peer3(String PortNumber){		
		
	
		while(true){
		ConnectToServer connecttoserver = new ConnectToServer();
		connecttoserver.connect("127.0.0.1",Integer.parseInt(PortNumber));	
		connecttoserver.sendIntialObject();
		connecttoserver.readUserCommand();	
		connecttoserver.communicateWithServer();
		}
		
		
	}
	
	}


