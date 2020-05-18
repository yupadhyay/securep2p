//package Peer1;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.InvalidAlgorithmParameterException;

/**
 * A simple class to generate a set of RSA (public and private) keys and
 * write those keys to files for exchange with other environments.  Four files will
 * be generated containing the public and private keys in byte and text(hex) formats.
 */
public class GenerateRSAKeys {
	
	private RSAPrivateKey secretKey=null;
	private RSAPublicKey publicKey=null;
	private int index=0;
	private String fileName="../KeyStore/peer_"+(index+1);
   public GenerateRSAKeys () { 
   
	    
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
         /* The 512 is the key size.  For better encryption increase to 2048 */
			keyGen.initialize(new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4));
		}
		catch (NoSuchAlgorithmException noAlgorithm) {
			System.out.println("No RSA provider available!");
			return;
		}
		catch (InvalidAlgorithmParameterException invalidAlgorithm) {
			System.out.println("Invalid algorithm for RSA!");
			return;
		}
		
		KeyPair keyPair = keyGen.generateKeyPair();
		
		secretKey = (RSAPrivateKey)keyPair.getPrivate();
		publicKey = (RSAPublicKey)keyPair.getPublic();
		
		// Write out the ASN.1 and raw key files
		FileOutputStream fOut=null;
		try {
			fOut = new FileOutputStream(fileName+".key");
			fOut.write(publicKey.getEncoded());
			fOut.close();
			
			FileWriter fw = null;
					
			fw = new FileWriter(fileName+".txt");
			fw.write(publicKey.getModulus().toString(16).toUpperCase());
			fw.write("\n");
			fw.write(publicKey.getPublicExponent().toString(16).toUpperCase());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
   }
  public RSAPrivateKey getPrivateKey(){
	  return this.secretKey;
  }
  public RSAPublicKey getPublicKey(){
	  return this.publicKey;
  }
	
}
