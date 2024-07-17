package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class Block {

    private String id;
    private String[] transactions;
    private String previousBlockHash;
    private String blockHash;
    private int nonce;
    private Timestamp created;
    private int difficulty = 7;
    public Block(String previousBlockHash, String[] transactions, int difficulty) {
        this.id = UUID.randomUUID().toString();
        this.difficulty = difficulty;
        this.previousBlockHash = previousBlockHash;
        this.transactions = transactions;

        this.nonce = 0;
        this.created = Timestamp.from(Instant.now());

        // this needs to be last
        this.generateHash();
    }
    public String getPreviousBlockHash() {
        return this.previousBlockHash;
    }

    public String[] getTransaction() {
        return this.transactions;
    }

    public Block mineFull() {
        while (!mine());
        System.out.println("block mined " + this.blockHash);
        return this;
    }

    public boolean mine() {
        this.nonce++;
        this.generateHash();
        return this.blockHash.substring(this.blockHash.length() - this.difficulty).equals("0".repeat(this.difficulty));
    }

    public String generateHash() {
        this.blockHash = this.hashString();
        return blockHash;
    }

    public String hashString() {
        return DigestUtils.sha256Hex(String.valueOf(Arrays.hashCode(new String[]{this.transactions.toString() + this.previousBlockHash + this.nonce})));
    }

    @Override
    public String toString() {
        return "Block{" +
                "transactions=" + Arrays.toString(this.transactions) +
                ", previousBlockHash=" + this.previousBlockHash +
                ", blockHash=" + this.blockHash +
                '}';
    }

    public String getId() {
        return this.id;
    }

    public int getDifficulty() {
        return this.difficulty;
    }
}
