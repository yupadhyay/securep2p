//package Peer2;


public class Peer2{
	public static void main(String[] args) {
		new Peer2(args[0]);
	}	
	public Peer2(String PortNumber){		
		
	
		while(true){
		ConnectToServer connecttoserver = new ConnectToServer();
		connecttoserver.connect("127.0.0.1",Integer.parseInt(PortNumber));	
		connecttoserver.sendIntialObject();
		connecttoserver.readUserCommand();	
		connecttoserver.communicateWithServer();
		}
		
		
	}
	
	}

