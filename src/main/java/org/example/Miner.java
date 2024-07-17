package org.example;

public class Miner implements Runnable {


    private Block b;

    public Miner() {
        this.b = BlockchainHolder.getBlockchain().getOpen();
    }


    @Override
    public void run() {
        while (!b.mine()) {
            if (Thread.currentThread().isInterrupted()) return;
        };
        BlockchainHolder.getBlockchain().addBlock(b);
    }
}
