//package Peer1;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
/**
 * Class used for decryption
 * @author Yogesh Upadhyay
 *
 */
class Decrypt {
	private byte[] dataTobeDecrypted;
	private byte[] decryptedData;
	private Cipher dcifer = null;
	private RSAPrivateKey rsaPrivateKey;
	private byte[] cipherText;
	private byte[] plainText;
	public Decrypt(RSAPrivateKey rsaprivatekey,byte[] dataTobeDecrypted){
	this.rsaPrivateKey = rsaprivatekey;
	this.dataTobeDecrypted=dataTobeDecrypted;
	}
	/**
	 * Method to decrypt
	 * @return decrypted data
	 */
	public byte[] decrypt(){
	try {
		dcifer=Cipher.getInstance("RSA/ECB/NoPadding");
		//System.out.println("Got private key as ... "+rsaPrivateKey.toString());
		try {
			dcifer.init(Cipher.DECRYPT_MODE, this.rsaPrivateKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cipherText = dataTobeDecrypted;
		try {
			plainText = dcifer.doFinal(this.dataTobeDecrypted);
			decryptedData=plainText;
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		}
		
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
	} catch (NoSuchPaddingException e) {
		// TODO Auto-generated catch block
		System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
	}

	return plainText;	
	}
}
