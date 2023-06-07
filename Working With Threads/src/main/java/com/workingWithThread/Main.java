package com.workingWithThread;

import com.workingWithThread.service.ComputationalServiceImpl;
import com.workingWithThread.service.ComputationalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static Logger LOG = LogManager.getLogger(Main.class);
    public static ComputationalService cs = new ComputationalServiceImpl();

    // Program Args :
    public static void main(String[] args) {
        LOG.info("Starting Application.");
        int argsLength = 5;
        try {
            if(args.length == argsLength) {
                String computationType = args[0];
                String strategy = args[1];
                int size = Integer.valueOf(args[2]);
                int threadCount = Integer.valueOf(args[3]);
                boolean printEnable = Boolean.parseBoolean(args[4]);
                LOG.info(String.format("Program Input : %s, Strategy : %s, Size : %d, Thread count : %d, PrintEnabled : %s", computationType, strategy, size, threadCount, printEnable));

                if(computationType.equalsIgnoreCase("matrixMultiplication")) {
                    // Matrix Multiplication Call
                    cs.matrixMultiplication(strategy, size, threadCount, printEnable);
                } else if(computationType.equalsIgnoreCase("arraySum")) {
                    // Array Sum computation call
                    cs.arraySum(strategy, size, threadCount, printEnable);
                }
            } else {
                LOG.info(String.format("Invalid Program Arguments. Required %d args, provided %d", argsLength,args.length));
            }
        } catch (Exception e) {
            LOG.error("Exception in com.workingWithThread.Main : ", e);
        }
        LOG.info("Exiting Application.");
    }
}
