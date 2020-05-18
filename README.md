# securep2p
This application is designed to facilitate secure peer to Peer file transfer between Sets of client
This application is designed to facilitate secure peer to Peer file transfer between Sets of client. In this application there is a indexing server and set of clients. Each client registers to the indexing server and register its file content in the global directory to the server. If other clients require that file they first query indexing server and then indexing server returns the set of clients having that file. Then requesting client select one of the client from the list and stabilize secure channel for the file transfer. I used RSA public key encryption for the file transfer. Each client has set of private and public key to confirm confidentiality and authentication. Digital signature is implemented to ensure authentication. All server and client are multithreaded to accept any number of requests at any time. Thread pooling is implemented to insure that request is handled in a proper way. Sharing of key is serialized with additional encryption to deny spoofing attack.

Enviornmetal Reqirenment for this project:

1) Java 6 (JDK)

 

Presentation
