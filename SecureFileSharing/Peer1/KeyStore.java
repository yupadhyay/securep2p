//package Peer1;

import java.io.Serializable;
import java.security.SignedObject;
/**
 * Serialized Object key Store
 * It will transport signed object
 * and encrypted data to the other end
 * @author yogi
 *
 */
public class KeyStore implements Serializable {
	private byte[] encryptedData;
	private SignedObject sign;
	private int index;

public void setEncryptedData(byte[] encryptedData){
	this.encryptedData=encryptedData;
}
public byte[] getEncryptedData(){
	return this.encryptedData;
}
public void setSign(SignedObject sign){
	this.sign=sign;
}
public SignedObject getSignedObject(){
	return this.sign;
}
public void setIndex(int index){
	this.index=index;
}
public int getIndex(){
	return this.index;
}
}
