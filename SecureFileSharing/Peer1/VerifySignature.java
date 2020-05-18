//package Peer1;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.SignedObject;
import java.security.interfaces.RSAPublicKey;

class VerifySignature {
	private SignedObject signedobject;
	private RSAPublicKey rsapublickey;
	private boolean verify;
	
public VerifySignature(SignedObject signedobject,RSAPublicKey rsapublickey) {
	this.signedobject=signedobject;
	this.rsapublickey=rsapublickey;
}
public boolean verify(){
try {
		java.security.Signature signature = java.security.Signature.getInstance("SHA1withRSA");
		try {
			verify = this.signedobject.verify(this.rsapublickey, signature);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
return verify;
}
}
