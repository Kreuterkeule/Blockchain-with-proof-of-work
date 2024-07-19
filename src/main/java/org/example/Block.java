package org.example;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class Block {

    private String id;
    private String[] transactions;
    private String previousBlockHash;
    private String blockHash;
    private int nonce;
    private Timestamp created;
    private int difficulty = 10;
    private int maxDifficulty = 10;

    public Block(Block b) {
        this.id = b.id;
        this.transactions = b.transactions;
        this.previousBlockHash = b.previousBlockHash;
        this.blockHash = b.blockHash;
        this.nonce = b.nonce;
        this.created = b.created;
        this.difficulty = b.difficulty;
        this.maxDifficulty = b.maxDifficulty;
    }

    public Block(String previousBlockHash, String[] transactions, int difficulty) {
        this.id = UUID.randomUUID().toString();
        this.difficulty = difficulty;
        this.maxDifficulty = difficulty;
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

    public Block mineFull() { // this won't stop on changes to the blockchain
        while (!mine());
        System.out.println("block mined " + this.blockHash);
        return this;
    }

    public boolean mine() {
        this.nonce++;
        this.generateHash();
        this.renewDifficulty();
        return this.blockHash.substring(this.blockHash.length() - this.difficulty).equals("0".repeat(this.difficulty));
    }
    public void renewDifficulty() { // 1000 * 10 every five seconds, this is not realistic
        this.difficulty = this.maxDifficulty - Math.toIntExact((Timestamp.from(Instant.now()).getTime() - this.created.getTime()) / (1000 * 10));
    }

    public String generateHash() {
        this.blockHash = this.hashString();
        return blockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public String getId() {
        return this.id;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public Timestamp getCreated() {
        return created;
    }

    public String hashString() {
        return DigestUtils.sha256Hex(String.valueOf(Arrays.hashCode(new String[]{this.transactions.toString() + this.previousBlockHash + this.nonce})));
    }

    @Override
    public String toString() {
        return "Block{" +
                "\n\ttransactions=" + Arrays.toString(this.transactions) +
                ", \n\tpreviousBlockHash=" + this.previousBlockHash +
                ", \n\tblockHash=" + this.blockHash +
                "\n}";
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
