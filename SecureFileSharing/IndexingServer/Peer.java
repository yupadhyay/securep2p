//package IndexingServer;


import java.util.ArrayList;
/**
 * Each peer will have seperate object
 * This object will contain property of that peer
 * @author yogi
 *
 */
public class Peer {
private int PeerName;
private ArrayList<String> fileList = new ArrayList<String>();
public Peer(){
	
}
public Peer(int peerName){
	this.PeerName = peerName;
}
public void setFileList(ArrayList<String> fileList){
	this.fileList = fileList;
}
public ArrayList<String> getFileList(){
	return fileList;
}
public int getPeerName(){
	return PeerName;
}
}
