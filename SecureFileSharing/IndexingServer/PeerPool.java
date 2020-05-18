//package IndexingServer;

import java.util.ArrayList;
/**
 * Singlton class that will contain pool of all pear
 * Note that by controlling peersize number of connection 
 * can be made to the server can be reduced
 * @author yogi
 *
 */
public class PeerPool {
private static final int peerSize=10;
private volatile static PeerPool peerPool=new PeerPool();
private volatile static Peer[] peerList= new Peer[peerSize];

private PeerPool(){

}
public void intializeArray(){
	for(int i=0;i<peerSize;i++){
		peerList[i]=null;
	}
}
public static PeerPool getPeerPoolInstance(){
		return peerPool;
}
public void addPeer(Peer peer,int index){
	peerList[index]=peer;
	
}
public Peer[] getPeerList(){
	return peerList.clone();
}
public void removePeer(int index){
	peerList[index]=null;
	
}
public int getPeerSize(){
return peerSize;
}

}


