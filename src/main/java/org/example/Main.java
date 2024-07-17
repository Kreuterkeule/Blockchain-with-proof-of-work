package org.example;


import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        String[][] transactionsList = {
                {"Victor sent Moritz 100 digital CNY"},
                {"Moritz sent Lenovo 300.000 digital CNY", "Moritz sent DHL 100 digital CNY"},
                {"Victor sent Moritz 40 digital CNY"},
                {"Sonja sent Victor -30 digital CNY (sneaky)"},
                {"Victor sent Apple Inc. 100.000.000.000.000 digital CNY (Apple computers are expensive)"},
                {"Moritz sent Victor 10 digital CNY"},
                {"Victor sent everyone 100 CNY", "everyone sent Victor 100 CNY"}
        };

        Arrays.stream(transactionsList).toList().forEach(e -> BlockchainHolder.getBlockchain().addTransactions(e));

        while (BlockchainHolder.getBlockchain().hasBlocks()) {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
            int oldBlockchainLength = BlockchainHolder.getBlockchain().length();
            while (BlockchainHolder.getBlockchain().length() == oldBlockchainLength) {
                if (executor.getPoolSize() < executor.getMaximumPoolSize()) {
                    executor.submit(new Miner());
                }
            }
            System.out.println("Mined block" + BlockchainHolder.getBlockchain().getLastBlock());
            executor.shutdownNow();
        }

        System.out.println(BlockchainHolder.getBlockchain().blockchain().toString().replaceAll("Block\\{", "\n"));
        System.out.println(BlockchainHolder.getBlockchain().length());

    }
}