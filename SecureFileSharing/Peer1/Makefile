JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
	Encrypt.java \
       	Decrypt.java \
        PublicKeyReader.java \
        RawRSAKey.java \
	SerializedObject.java \
	VerifySignature.java \
	FileTransfer.java \
	KeyStore.java \
	ConnectToAnotherPeer.java \
	ConnectToServer.java \
	Peer1.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

