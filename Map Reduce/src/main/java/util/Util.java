package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class Util {
    private static Random random = new Random();
    public static void generateInput(int transactionCount) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("/Users/ashu/Desktop/MTech/ACSP/Assignments/Assignment2_MapReduce/src/main/resources/Input3.txt", false));
            for(int i = 1 ; i <= transactionCount ; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(i).append(":");
                int transactions = random.nextInt(50)+1;
                for(int j = 1 ; j <= transactions ; j++) {
                    int price = random.nextInt(100) + 100;
                    sb.append(price).append(" ");
                }
                out.write(sb.toString().trim() + "\n");
            }
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generateInput(Integer.parseInt(args[0]));
    }

}
