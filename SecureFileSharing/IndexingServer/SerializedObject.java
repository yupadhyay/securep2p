//package Peer1;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Server side implementation of serialized object
 * Serialized object will contain property of that object
 * @author yogi
 *
 */
public class SerializedObject implements Serializable{
private int index;
private ArrayList<String> fileName = new ArrayList<String>();
private int port_Number;
@SuppressWarnings("unchecked")
public void setFileList(ArrayList<String> fileName){
	this.fileName=(ArrayList<String>)fileName.clone();
}
public ArrayList<String> getFileList(){
	return this.fileName;
}
public void setIndex(int index){
	this.index=index;
}
public int getIndex(){
return this.index;
}
public void setPort(int portNumber){
	this.port_Number=portNumber;
}
public int getPort(){
return this.port_Number;
}
}
