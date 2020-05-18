//package Peer1;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.interfaces.RSAPublicKey;

public class PublicKeyReader {

private String fileName = "../KeyStore/peer_";
private KeyFactory keyFactory = null;
private RSAPublicKey publicKey = null;
private RSAPublicKeySpec publicKeySpec = null;
	
/**
 * This method will return public key of another client
 * @return
 */	
PublicKey getPublicKey(int indexOfAnotherPeer){

RawRSAKey rawKey;
try {
	rawKey = RawRSAKey.getInstance(fileName+(indexOfAnotherPeer+1)+".txt");
	publicKeySpec = new RSAPublicKeySpec(rawKey.getModulus(), rawKey.getExponent());	
} catch (IOException e) {
	// TODO Auto-generated catch block
	System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
}
try {
	keyFactory = KeyFactory.getInstance("RSA");
	try {
		publicKey = (RSAPublicKey)keyFactory.generatePublic(publicKeySpec);
	} catch (InvalidKeySpecException e) {
		// TODO Auto-generated catch block
		System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
	}
} catch (NoSuchAlgorithmException e) {
	// TODO Auto-generated catch block
	System.out.println("ERROR !!!!!!!!!!  "+e.getMessage());
}
return publicKey;
}
}
