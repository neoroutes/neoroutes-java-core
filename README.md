# neoroutes-java-core

This repository contains java classes, helpers and utils for neoroutes to be used in different implementations.

### Diffie Hellman

Diffie-Hellman is a way of generating a shared secret between two people in such a way that the secret can't be seen by observing the communication. That's an important distinction: You're not sharing information during the key exchange, you're creating a key together.

NeoRoutes uses Diffie-Hellman to perform an end-to-end key exchange. This (temporary) secret key is used to encrypt p2p communications with an AES-256 cipher. 

- *Tell me more! What it really is?* [read](https://security.stackexchange.com/questions/45963/diffie-hellman-key-exchange-in-plain-english)
- *How?* [look](https://github.com/neoroutes/neoroutes-java-core/tree/master/src/main/java/project/neoroutes/diffieHellman)
- *Show me a simple test?* [look](https://github.com/neoroutes/neoroutes-java-core/blob/master/src/test/java/project/neoroutes/diffieHellman/EncryptedSessionTest.java)

### Encrypted Sessions

Encrypted Session class attaches to a session and uses diffie hellman key agreement to encrypt data.

### AES File Encryption

In other to back-up the neoroutes data and reuse it in other devices, user first needs to encrypt the data. AES File Encryption helps user in this process.

### Generators

Generator is an interface to generate different kind of data, mostly for initializing neo-routes in the device.

For example, `KeyStoreGenerator` generates initial keystore which holds user public and private keys.
