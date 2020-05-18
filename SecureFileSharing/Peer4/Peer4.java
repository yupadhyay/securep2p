//package Peer2;


public class Peer4{
	public static void main(String[] args) {
		new Peer4(args[0]);
	}	
	public Peer4(String PortNumber){		
		
	
		while(true){
		ConnectToServer connecttoserver = new ConnectToServer();
		connecttoserver.connect("127.0.0.1",Integer.parseInt(PortNumber));	
		connecttoserver.sendIntialObject();
		connecttoserver.readUserCommand();	
		connecttoserver.communicateWithServer();
		}
		
		
	}
	
	}


