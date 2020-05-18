//package Peer1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.SignedObject;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.io.File;
/**
 * Class that will handle communication between Two peers.
 * @author Yogesh Upadhyay
 *
 */
class ConnectToAnotherPeer implements Runnable {
	private Socket client = null;
	private ObjectOutputStream oout =null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private String inputLine = null;
	private int index=2;
	//private String root = "Peer"+(index+1)+"/";	
	private String root = "Public/";
	private String[] fileName;
	private FileInputStream fin=null;
	private ConnectToServer conn;
	private SignedObject sign=null;
	private StringBuffer sb = new StringBuffer();
	private Date date = new Date();
	private String time = "\n"+date.toString()+this.getClass().getName()+"   ";
	/**
	 * Constructor for the class
	 * @param client
	 * @param conn
	 */
	public ConnectToAnotherPeer(Socket client,ConnectToServer conn){
	//this.fileName=fileName;
	this.conn=conn;
	this.client=client;
	try {
		oout = new ObjectOutputStream(client.getOutputStream());
		new ObjectInputStream(client.getInputStream());
	} catch (IOException e1) {
		System.out.println("ERROR !!!!!!!!!!  "+e1.getMessage());
	}
	
	try {
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(),true);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
	}
	new Thread(this).start();
	//return;
}
@SuppressWarnings("deprecation")
public void run(){
try {
	if(inputLine==null){
	out.flush();
	out.println("Connected to remote server for file transfer "+this.client.getPort());
	}
	while((inputLine=in.readLine())!=null){
		try {
			sb.append(time+"reading ...... "+inputLine);
			//If give command is recieved from the another peer do the following
			if(inputLine.contains("give")){
			sb.append(time+"Give Command Recieved");
			this.fileName = inputLine.split(" ");
			sb.append(time+"someone requesting file  "+this.fileName[1]);
			fin = new FileInputStream(root+this.fileName[1]);
			byte [] inputDataFromTheFile = new byte[(int)(new File(root+this.fileName[1]).length())];
			fin.read(inputDataFromTheFile);
			try {
				int indexOfAnotherPeer;
				byte[] encryptedData;
				indexOfAnotherPeer=Integer.parseInt(fileName[2]); 
				//out.println("send public key"); //removed by yogesh 30 april
				//Read the public key from the key store.
				PublicKeyReader keyReader = new PublicKeyReader();
				RSAPublicKey rsaPublicKey = (RSAPublicKey)keyReader.getPublicKey(indexOfAnotherPeer);				
				
				//encryptedData = new Encrypt(rsaPublicKey,inputDataFromTheFile).encrypt();
				try {
					java.security.Signature signature = java.security.Signature.getInstance("SHA1withRSA");										
					try {
						//sign the data first
						//sign = new SignedObject(encryptedData,this.conn.getPrivateKey(),signature);
						sb.append(time+"signing data to be sent");
						sign = new SignedObject(inputDataFromTheFile,this.conn.getPrivateKey(),signature);
					} catch (InvalidKeyException e) {
						// TODO Auto-generated catch block
						System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
					} catch (SignatureException e) {
						// TODO Auto-generated catch block
						System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
				}
				//Get the encrypted data to be sent
				encryptedData = new Encrypt(rsaPublicKey,inputDataFromTheFile).encrypt();
				//Once data is signed and encrypted pack the data to be sent
				KeyStore packData = new KeyStore();
				packData.setEncryptedData(encryptedData);
				packData.setSign(sign);
				packData.setIndex(index);
				//Tell another peer to take the data
				out.println("take encrypted data");
				sb.append(time+"sending encrypted data to the another peer");
				oout.writeObject(packData);
				out.println("done");
				System.out.println(inputLine);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			}
			if(inputLine.compareTo("done")==0){
				sb.append(time+"Recived done command from another peer");
				out.flush();
				out.close();
				in.close();
				client.close();
				Thread.currentThread().stop();
				return;
			
			//out.flush();
			//out.close();
			//in.close();
			//client.close();
			//Thread.currentThread().stop();
			//return;
			}//end of if input line contain "give"
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		}
		
	}
} catch (IOException e) {
	// TODO Auto-generated catch block
	System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
}

}
public String toString(){
	return (new String(sb));
}
}
