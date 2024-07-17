package org.example;

public class BlockchainHolder {

    static Blockchain instance = new Blockchain();

    public static Blockchain getBlockchain() {
        return instance;
    }
}
