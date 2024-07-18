package org.example;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Blockchain {

    private boolean addBlockLock = false;

    private static final int DEFAULT_DIFFICULTY = 5;

    private List<Block> blockchain = new ArrayList<>();
    private List<Block> open = new ArrayList<>();

    public Blockchain() {
        this.createGenesisBlock();
    }

    public Block getLastBlock() {
        return this.blockchain.getLast();
    }

    public List<Block> blockchain() {
        return this.blockchain;
    }

    public Block getOpen() {
        return this.open.get(Math.abs(new Random().nextInt() % this.open.size()));
    }

    public void addTransactions(String[] transactions) {
        this.open.add(new Block(this.getLastBlock().generateHash(), transactions, this.DEFAULT_DIFFICULTY));
    }

    public void createGenesisBlock() {
        this.blockchain.add(new Block("0", new String[]{"Genesis"}, 0));
    }

    public void renewOpenPrevHashes() {
        String hash = this.getLastBlock().generateHash();
        this.open = this.open.stream().map(e -> new Block(hash, e.getTransaction(), e.getDifficulty())).collect(Collectors.toList());
    }

    public boolean addBlock(Block block) {
        this.addBlockLock = true;
        block.generateHash();
        if (!checkHash(block)) {
            System.out.println("Add failed because " +
                    ((block.getPreviousBlockHash()).equals(this.getLastBlock().generateHash()) ? "hashes don't match" : "no pow"));
            this.addBlockLock = false;
            return false;
        }
        this.open.remove(this.open.stream().filter(e -> e.getId().equals(block.getId())).collect(Collectors.toList()).get(0));
        this.blockchain.add(block);
        this.renewOpenPrevHashes();
        System.out.println("MINED BLOCK SUCCESS " + block);
        this.addBlockLock = false;
        return true;
    }

    public boolean hasBlocks() {
        return this.open.size() > 0;
    }

    public boolean getAddBlockLock() {
        return this.addBlockLock;
    }

    public List<Block> getBlockList() {
        return this.open;
    }

    public boolean checkHash(Block b) {
        return b.getPreviousBlockHash().equals(this.getLastBlock().generateHash())
        && b.generateHash().substring(b.generateHash().length() - b.getDifficulty()).equals("0".repeat(b.getDifficulty()));
    }

    public int length() {
        return this.blockchain.size();
    }

    public List<String[]> transactions() {
        return this.blockchain.stream().map(e -> e.getTransaction()).collect(Collectors.toList());
    }
}
