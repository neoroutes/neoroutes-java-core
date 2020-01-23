# neoroutes-java-core

This repository contains java classes, helpers and utils for neoroutes to be used in different implementations.

### Diffie Hellman

Diffie-Hellman is a way of generating a shared secret between two people in such a way that the secret can't be seen by observing the communication. That's an important distinction: You're not sharing information during the key exchange, you're creating a key together.

NeoRoutes uses Diffie-Hellman to perform an end-to-end key exchange. This (temporary) secret key is used to encrypt p2p communications with an AES-256 cipher. 

- *Tell me more! What it really is?* [read](https://security.stackexchange.com/questions/45963/diffie-hellman-key-exchange-in-plain-english)
- *How?* [look](https://github.com/neoroutes/neoroutes-java-core/tree/master/src/main/java/project/neoroutes/diffieHellman)
- *Show me a simple test?* [look](https://github.com/neoroutes/neoroutes-java-core/blob/master/src/test/java/project/neoroutes/diffieHellman/EncryptedSessionTest.java)

