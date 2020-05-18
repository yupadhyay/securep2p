//package IndexingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
/**
 * Class that will connect to the another client
 * This class is multi threaded to handle request from each client
 * @author yogi
 *
 */

public class Connect implements Runnable {
	private Socket client = null;
	private ObjectOutputStream oout =null;
	private ObjectInputStream oin= null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String inputLine = null;
	private PeerPool peerPool = null;
	private int index;
	private StringBuffer sb = new StringBuffer();
public Connect(Socket client){	
	this.client = client;
	peerPool = PeerPool.getPeerPoolInstance();
	sb.append("\nI am trying to initilise array");
	//peerPool.intializeArray();
	try {
		in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(),true);
		try {
		oout = new ObjectOutputStream(client.getOutputStream());
		oin = new ObjectInputStream(client.getInputStream());
	} catch (IOException e) {
		try {
			this.client.close();
		} catch (IOException e1) {
			System.out.println("ERROR!!!!!!!!! "+e1.getMessage());
		}
		return;
	}
		
	} catch (IOException e2) {
		System.out.println("ERROR!!!!!!!!!! "+e2.getMessage());	}
	
	
	new Thread(this).start();
}
@SuppressWarnings({ "unchecked", "deprecation" })
public void run(){
	
	if(inputLine==null){
	readSerializedObject();
	out.println("Connected to server");
	}
	
	try {
		while((inputLine=in.readLine())!=null){
			if(inputLine.compareToIgnoreCase("hi")==0)
			out.println("Hello");
			else if(inputLine.contains("get")){
				String[] getFileCommand = inputLine.split(" ");
				ArrayList<Integer> setOfPortNumber = new ArrayList<Integer>();
				sb.append("\nAsked for file name  "+getFileCommand[1]);
				setOfPortNumber = getPort(getFileCommand[1]);
				sb.append("Size is "+setOfPortNumber.size());
				if(setOfPortNumber.size()==0){
					out.println("file not found");
				}
				else{
				out.println("port list");
				for(int i=0;i<setOfPortNumber.size();i++){
					out.println(setOfPortNumber.get(i));
				}
				out.println("done");
				}
				setOfPortNumber.clear();
			}
			else if(inputLine.contains("update list")){
				sb.append("update command recieved");
				out.println("please send");
				readSerializedObject();
				//out.println("Updation complete");
			}
			else if(inputLine.contains("refresh")){
				sb.append("I am here got refresh commnd");
				out.println("File list updated on the server !!!");				
			}
			else if(inputLine.contains("exit")){
				System.out.println("Peer "+index+" is now disconnected");
				peerPool.removePeer(index);
				out.println("exited from server ");
				Thread.currentThread().stop();
			}
			else{
				out.println("Unknown Command");
			}
			System.out.println(inputLine);
			
			
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
private void readSerializedObject(){
	try {
		sb.append("\nI m inside run");
		//out.println("Connected to the Indexing Server");
			/*try {
				oout = new ObjectOutputStream(client.getOutputStream());
				oin = new ObjectInputStream(client.getInputStream());
			} catch (IOException e) {
				try {
					this.client.close();
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
				}
				return;
			}*/
			Peer peer = new Peer(client.getPort());			
			ArrayList<String> fileList = new ArrayList<String>();
			fileList.clear();
			try {
				//SerializedObject serialized =  new SerializedObject();
				SerializedObject serialized;
				serialized = (SerializedObject)oin.readObject();
				fileList = serialized.getFileList();
				for(int i=0;i<fileList.size();i++){
					System.out.println("File recieved "+fileList.get(i));
				}
				index=serialized.getIndex();
				peer.setFileList(fileList);
				peerPool.addPeer(peer,index);
		
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//oout.writeObject(new Date());
			oout.flush();
			//out.flush();
			//out.println("Connected to Server");
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}	
}
@SuppressWarnings("unchecked")
private ArrayList<Integer> getPort(String FileName){
ArrayList<Integer> setOfPortNumber = new ArrayList<Integer>();
Peer peer;
Peer[] peerList = peerPool.getPeerList();
ArrayList<String> tempFielList;
sb.append("\npeer List Size is "+peerPool.getPeerSize());

for(int i=0;i<peerPool.getPeerSize();i++){	
	peer = peerList[i];
	if(peer!=null){
	tempFielList = peer.getFileList();
	sb.append("\nfor Peer Name  "+peer.getPeerName());	
	for(int j=0;j<tempFielList.size();j++){
		//Get the list of peer port number for a file name asked for
		if(tempFielList.get(j).equals(FileName)){
			sb.append("\nFound the file adding "+peer.getPeerName() + "in the list");
		setOfPortNumber.add(peer.getPeerName());	
		}
	}
	}//end of if peer != null

	
}
//peerList.clear();
return setOfPortNumber;
}
}
