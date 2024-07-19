package org.example;


import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

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
                {"Moritz sent IKEA 2000 digital CNY (furniture shopping spree)"},
                {"Victor sent Moritz 25 digital CNY (loan repayment)"},
                {"Sonja sent Moritz 500 digital CNY (thanks for the help)"},
                {"Lenovo sent DHL 50 digital CNY (shipping fees)"},
                {"Victor sent Moritz 75 digital CNY (birthday gift)"},
                {"Moritz sent Sonja 300 digital CNY (shared dinner costs)"},
                {"Victor sent Tesla 500,000 digital CNY (new electric car)"},
                {"Sonja sent Victor 150 digital CNY (concert tickets)"},
                {"Moritz sent Victor 5 digital CNY (just for fun)"},
                {"Victor sent Moritz 80 digital CNY (monthly allowance)"},
                {"DHL sent Victor 30 digital CNY (delivery refund)"},
                {"Victor sent Moritz 15 digital CNY (emergency cash)"},
                {"Sonja sent Victor 200 digital CNY (book purchases)"},
                {"Moritz sent Sonja 1000 digital CNY (vacation expenses)"},
                {"Victor sent Apple Inc. 1,000,000,000 digital CNY (upgrading office computers)"},
                {"Moritz sent DHL 20 digital CNY (small parcel shipping)"},
                {"Victor sent Sonja 60 digital CNY (dinner contribution)"},
                {"Sonja sent Moritz 75 digital CNY (movie tickets)"},
                {"Victor sent Moritz 120 digital CNY (game console payment)"},
                {"Somebody sent Ether, wrong blockchain again!"},
                {"Victor sent Sonja 40 digital CNY (thanks for the coffee)"},
                {"Moritz sent Victor 70 digital CNY (split bill for lunch)"},
                {"Sonja sent DHL 45 digital CNY (express delivery fee)"},
                {"Victor sent Lenovo 5000 digital CNY (new laptop purchase)"},
                {"Moritz sent Victor 85 digital CNY (gift card)"},
                {"Victor sent DHL 15 digital CNY (shipping costs)"},
                {"Sonja sent Victor 300 digital CNY (travel expenses)"},
                {"Victor sent Sonja 50 digital CNY (lunch payment)"},
                {"Moritz sent Sonja 25 digital CNY (shared expenses)"},
                {"Victor sent DHL 100 digital CNY (courier service)"},
                {"Victor sent Moritz 200 digital CNY (weekend trip)"},
                {"Sonja sent Victor 35 digital CNY (coffee money)"},
                {"Victor sent Lenovo 10,000 digital CNY (tech gadgets)"},
                {"Moritz sent Sonja 500 digital CNY (birthday gift)"},
                {"Victor sent DHL 20 digital CNY (shipping charges)"},
        };
//        ADD BEFORE
        Arrays.stream(transactionsList).toList().forEach(e -> BlockchainHolder.getBlockchain().addTransactions(e));

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (String[] transactions : transactionsList) {
//                    BlockchainHolder.getBlockchain().addTransactions(transactions);
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }).start();

        Thread.sleep(5000);

        // CHRONOLOGICAL
//        chronologicalMine();


//         MULTITHREADING, NOT FULLY WORKING
        threadPoolMine(16);
        logger.log(Level.INFO, "\n####################################################");
        logger.log(Level.INFO, "################## FULL BLOCKCHAIN #################");
        logger.log(Level.INFO, "####################################################");
        logger.log(Level.INFO, BlockchainHolder.getBlockchain().blockchain().toString());
        logger.log(Level.INFO, BlockchainHolder.getBlockchain().length());
        logger.log(Level.INFO, "### TRANSACTIONS ###");
        BlockchainHolder.getBlockchain().transactions().forEach(e -> System.out.println(Arrays.toString(e)));

    }
}