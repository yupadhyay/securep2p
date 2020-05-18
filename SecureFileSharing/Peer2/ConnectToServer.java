//package Peer1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Date;
/**
 * Class that will handle interaction between server and peer
 * @author yogi
 *
 */

class ConnectToServer implements Runnable{
	private Socket client=null;
	private ObjectOutputStream oout = null;
	private BufferedReader in =null;
	private PrintWriter out = null;
	private ArrayList<String> fileName = new ArrayList<String>();
	private int index = 1;
	//private String root = "Peer"+(index+1)+"/";
	private String root = "Public/";

	private File dir;
	private String[] files; 
	private String get_Command;
	private String[] get_FileName;
	private KeyStore keyStore;
	private RSAPrivateKey rsaPrivatekey = null;
	private RSAPublicKey rsaPublicKey = null;
	private StringBuffer sb = new StringBuffer();
	private Date date = new Date();
	private String time = "\n"+date.toString()+this.getClass().getName()+"   ";
	
	/**
	 * Used to generate Private and public key
	 */
	private void intializeKey(){		
		GenerateRSAKeys generateRSAKeys = new GenerateRSAKeys();
		rsaPrivatekey = generateRSAKeys.getPrivateKey();
		rsaPublicKey = generateRSAKeys.getPublicKey();
	}
	protected RSAPrivateKey getPrivateKey(){
		return this.rsaPrivatekey;
	}
	protected RSAPublicKey getPublicKey(){
		return this.rsaPublicKey;
	}
	/**
	 * Method to Initialise key store with the index
	 */
	private void initialiseKeyStore(){
		this.keyStore = new KeyStore();
		keyStore.setIndex(index);		
	}
	/**
	 * Method to connect to the host
	 * @param hostName
	 * @param portNumber
	 */
	public void connect(String hostName,int portNumber){
		try {
			client = new Socket(hostName,portNumber);
			new ObjectInputStream(client.getInputStream());
			oout = new ObjectOutputStream(client.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		}
		getAllFilesName();
		intializeKey();
		initialiseKeyStore();		
		new Thread(this).start();	
		
	}
	/**
	 * Method to get all the file information
	 */
	private void getAllFilesName(){
		dir= new File(root);
		files=dir.list();
		fileName.clear();
		for(String file : files){
			fileName.add(file);
		}
	 System.out.println("Files Registered to the server");
	}
	/**
	 * Method that will send initial configuration to the server
	 * This object is serialized 
	 */
	public void sendIntialObject(){
		try {
			sb.append(time+"Sending Initial Object to the server");
			SerializedObject serialized = new SerializedObject();
			serialized.setFileList(fileName);
			serialized.setIndex(index);
			serialized.setPort(client.getLocalPort());
			oout.writeObject(serialized);
			sb.append(time+"initial object is sent");
					
		} catch (UnknownHostException e) {
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		} catch (IOException e1) {
			System.out.println("ERROR !!!!!!!!!!  "+e1.getMessage());
		}
		
	}
/**
 * Method to read user command
 */
	public void readUserCommand(){
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		}		
	}
/**
 * Method that will handle communication with the server
 * Once the list is retrived it will connect to another peer
 * for the file transfer.
 */
public void communicateWithServer(){
		String fromServer;
		try {
			while((fromServer=in.readLine())!=null){
				System.out.println(fromServer);
				if(fromServer.contains("exit")){
					System.exit(1);
				}
				if(fromServer.compareTo("file not found")==0){
				//Print the server response	
				}
				if(fromServer.compareTo("port list")==0){
				ArrayList<Integer> portList = new ArrayList<Integer>();
				String listOfPort;
				while((listOfPort=in.readLine())!=null){
				if(listOfPort.compareTo("done")==0){
					break;
				}
				else
				portList.add(Integer.parseInt(listOfPort));
				}//end of while
				System.out.println("Following port lists are recived from server ...");
				for(int i=0;i<portList.size();i++){
					System.out.println(portList.get(i));
				}
				for(int i=0;i<portList.size();i++){
					if(portList.get(i)==client.getLocalPort()){
						System.out.println("you have this file already !!!!!!");
						portList.clear();
						break;
					}
				}
				//Soon after getting the list estabilise connection with another peer having that file
				if(portList.size()>0){
					System.out.println("connecting to  "+client.getInetAddress()+"  "+portList.get(0));
					Socket ephemeral_Socket = new Socket("127.0.0.1",portList.get(0));
					BufferedReader temp_in = new BufferedReader(new InputStreamReader(ephemeral_Socket.getInputStream()));
					PrintWriter temp_out = new PrintWriter(ephemeral_Socket.getOutputStream(),true);
					ObjectInputStream temp_oin = new ObjectInputStream(ephemeral_Socket.getInputStream());
					ObjectOutputStream temp_oout = new ObjectOutputStream(ephemeral_Socket.getOutputStream());
					sb.append(time+"Sending Command .... "+"give "+this.get_FileName[1]+"to another peer");
					byte[] encryptedDataFromServer;
					String fromEphemeralServer;	
					byte[] finalDecryptedData=null;
					while((fromEphemeralServer=temp_in.readLine())!=null){
						if(fromEphemeralServer.contains("Connected to remote")){
							//will send give command + filename + it's own index for public key extraction
							temp_out.println("give "+this.get_FileName[1]+" "+index);
							if(temp_in.readLine().contains("take encrypted data")){
								sb.append(time+"taking encrypted data");
								KeyStore keystore = new KeyStore();
								try {
									keystore = (KeyStore)temp_oin.readObject();
									sb.append(time+"Packed object reading done");
									encryptedDataFromServer = keystore.getEncryptedData();
								
									Decrypt decrypt = new Decrypt(this.rsaPrivatekey,encryptedDataFromServer);
									finalDecryptedData = decrypt.decrypt();
									PublicKeyReader keyReader = new PublicKeyReader();
									VerifySignature verify = new VerifySignature(keystore.getSignedObject(),(RSAPublicKey)keyReader.getPublicKey(keystore.getIndex()));
									boolean verified = verify.verify();
									if(verified){
										System.out.println("Signature varification Sucessfull");
									}
								else{
										System.out.println("Signature varification Failed");
								}
									sb.append(time+"Final decrypted data recieved as "+finalDecryptedData.toString());
									
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
								}
							}
						}
						else if(fromEphemeralServer.compareTo("done")==0){
								temp_out.println("done"); //Send to server that even I am done u close ur connection
								break;
						}
						else{
						if(!fromEphemeralServer.contains("Connected to remote")){	
						//encryptedDataFromServer+=fromEphemeralServer;
						//Decrypt decrypt = new Decrypt(this.rsaPrivatekey,encryptedDataFromServer);
						//dataTobeWrittenOnFile = decrypt.decrypt();
						//dataTobeWrittenOnFile+=fromEphemeralServer;
						}
						else{
							System.out.println("I recieved this from another peer "+fromEphemeralServer);
						}
						}
						
							System.out.println(fromEphemeralServer);
										
					}
					temp_in.close();
					temp_out.flush();
					temp_oout.flush();
					temp_out.close();
					temp_oout.close();
					writeDataToFile(this.get_FileName[1],finalDecryptedData);
					ephemeral_Socket.close();
					sb.append(time+"writing to file done ... port is also closed");
					//Now update server with the new file list
					refreshFilelistOnServer();
					//readFromUser();
				}
				}//end of if input is port list
				this.readFromUser();							
			}
		} catch (IOException e) {
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		}
	}
private void refreshFilelistOnServer(){
	getAllFilesName();
	out.println("update list");
	try {
		if(in.readLine().contains("please send")){
		sendIntialObject();
		sb.append(time+"Sending Command update list");
		sb.append(time+"Command send complete");
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
	}
}
private void readFromUser(){
	String fromUser;
	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	try {
		System.out.print("Peer "+(index+1)+"~~~ > ");
		if((fromUser=stdIn.readLine())!=null){
			if(fromUser.contains("get")){
				get_Command=fromUser;
				get_FileName = get_Command.split(" ");
			}
			else if(fromUser.contains("refresh")){
				refreshFilelistOnServer();
			}
			out.println(fromUser);
		}
	} catch (IOException e) {
		System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		
	}	
}
private void writeDataToFile(String fileName,byte[] dataToBeWritten){
	File file = new File(root+fileName);
	FileOutputStream fout=null;
		
	//PrintStream pout = null;
	try {
		fout = new FileOutputStream(file);
		try {
			fout.write(dataToBeWritten);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
	}
	finally{
		try {
			fout.close();
			//pout.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		}
		
	}
	
}
public void openEhemeralPort(){
	sb.append("local port is "+client.getLocalPort());
	new FileTransfer(client.getLocalPort(),this);
}
public void run(){	
	openEhemeralPort();	
}
public void closeConnection(){
	try {
		in.close();
		out.flush();
		out.close();
		client.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
	}
	
}
public String toString(){
	return (new String(sb));
}
}
