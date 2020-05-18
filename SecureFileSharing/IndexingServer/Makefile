JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
	Peer.java \
       	PeerPool.java \
        SerializedObject.java \
        Connect.java \
	Server.java \
	
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class


