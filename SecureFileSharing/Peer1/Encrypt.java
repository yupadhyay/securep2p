//package Peer1;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Class for encrypting data
 * @author Yogesh Upadhyay
 *
 */

class Encrypt {
	private byte[] dataTobeEncrypted;
	private Cipher ecifer = null;
	private RSAPublicKey rsapublickey;
	private byte[] cipherText;
	public Encrypt(RSAPublicKey rsapublickey,byte[] dataTobeEncrypted){
	this.rsapublickey = rsapublickey;
	this.dataTobeEncrypted=dataTobeEncrypted;
	}
	public byte[] encrypt(){
	try {
		ecifer=Cipher.getInstance("RSA/ECB/NoPadding");
		try {
			ecifer.init(Cipher.ENCRYPT_MODE, rsapublickey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
		}
		try {
			
			cipherText = ecifer.doFinal(this.dataTobeEncrypted);
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

	return cipherText;	
	}
	}
