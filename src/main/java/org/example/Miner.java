package org.example;


import java.util.UUID;

public class Miner implements Runnable {

    private Block b;
    private String id;

    public Miner() {
        this.b = BlockchainHolder.getBlockchain().getOpen();
        this.id = UUID.randomUUID().toString();
    }


    @Override
    public void run() {
        while (!b.mine()) {
            if (Thread.currentThread().isInterrupted()) return;
        }
        while (BlockchainHolder.getBlockchain().getAddBlockLock()) {
            if (Thread.currentThread().isInterrupted()) return;
        }
        BlockchainHolder.getBlockchain().addBlock(b);
    }

    private void log(String msg) {
        System.out.println("Miner["+this.id +"] | >>> " + msg);
    }
}
