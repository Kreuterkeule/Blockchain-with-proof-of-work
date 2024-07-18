package org.example;


import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void threadPoolMine(int threads) throws InterruptedException {

        while (BlockchainHolder.getBlockchain().hasBlocks()) {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
            int oldBlockchainLength = BlockchainHolder.getBlockchain().length();
            int seconds = 0;
            while (BlockchainHolder.getBlockchain().length() == oldBlockchainLength && seconds < 1000) {
                if (executor.getPoolSize() < executor.getMaximumPoolSize()) {
                    executor.submit(new Miner());
                }
                Thread.sleep(10);
                seconds++;
            }
            executor.shutdownNow();
        }
    }
    public static void chronologicalMine() {
        while (BlockchainHolder.getBlockchain().hasBlocks()) {
            Miner m = new Miner();
            m.run();
        }
    }
    public static void main(String[] args) throws InterruptedException {

        String[][] transactionsList = {
                {"Victor sent Moritz 100 digital CNY"},
                {"Moritz sent Lenovo 300.000 digital CNY", "Moritz sent DHL 100 digital CNY"},
                {"Victor sent Moritz 40 digital CNY"},
                {"Sonja sent Victor -30 digital CNY (sneaky)"},
                {"Victor sent Apple Inc. 100.000.000.000.000 digital CNY (Apple computers are expensive)"},
                {"Moritz sent Victor 10 digital CNY"},
                {"Victor sent everyone 100 CNY", "everyone sent Victor 100 CNY"},
                {"Somebody sent BTC, whoops wrong Blockchain"},
        };

        Arrays.stream(transactionsList).toList().forEach(e -> BlockchainHolder.getBlockchain().addTransactions(e));

        // CHRONOLOGICAL
        chronologicalMine();


//         MULTITHREADING, NOT FULLY WORKING
//        threadPoolMine(4);
        System.out.println("\n####################################################");
        System.out.println("################## FULL BLOCKCHAIN #################");
        System.out.println("####################################################");
        System.out.println(BlockchainHolder.getBlockchain().blockchain().toString());
        System.out.println(BlockchainHolder.getBlockchain().length());
        System.out.println("### TRANSACTIONS ###");
        BlockchainHolder.getBlockchain().transactions().forEach(e -> System.out.println(Arrays.toString(e)));

    }
}