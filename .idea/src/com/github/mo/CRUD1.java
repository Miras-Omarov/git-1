package com.github.mo;

import java.io.*;
import java.util.Scanner;

public class CRUD1 {
    private static String mode;
    private static boolean isFileFound = true;
    private static boolean isReady = false;
    private static final Scanner scanner = new Scanner(System.in);
    private static BufferedReader bufferedReader = null;
    private static BufferedWriter bufferedWriter = null;

    public static void main(String[] args) throws IOException {
        start();
        if(isFileFound) {
            switch(mode.toLowerCase()) {
                case "create": int id = read();
                    create(id);
                    break;
                default:
                    System.out.println("Not supported in this version.(v1.0)");
            }
        }
        bufferedWriter.close();
        bufferedReader.close();
        scanner.close();
    }

    public static void start() {
        System.out.println("Input a file path...");
        String path = scanner.nextLine();
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            bufferedWriter = new BufferedWriter(new FileWriter(path,true));
        } catch(FileNotFoundException e) {
            System.out.println("File was not found.");
            isFileFound = false;
        } catch(IOException e) {
            System.out.println(e.getMessage());
            isFileFound = false;
        }
        System.out.println("!!!AT THE MOMENT ONLY CREATE IS AVAILABLE!!!\nWhat would you like to do?\nCreate\nUpdate\nDelete");
        mode = scanner.nextLine();
    }

    private static int read() throws IOException {
        int maxId = 0,id;
        while(bufferedReader.ready()) {
            isReady = true;
            String line = bufferedReader.readLine();
            id = Integer.parseInt(line.substring(0,8).trim());
            maxId = Math.max(maxId, id);
        }
        return maxId + 1;
    }

    private static void create(int id) throws IOException {
        System.out.println("productName:quantity:price");
        System.out.println("NOTE:Product name cannot be more than 30 letters,whereas quantity cannot be more than 8 digits.\nInput the product name, quantity, and price as shown.");

        String productString = scanner.nextLine();
        String[] product = productString.split(":");
        String productId = String.valueOf(id);
        String productName = product[0];
        String productQuantity = product[1];
        String productPrice = product[2];

        while(productId.length() < 8) {
            productId += " ";
        }
        while(productName.length() < 30) {
            productName += " ";
        }
        while(productQuantity.length() < 8) {
            productQuantity += " ";
        }

        String line = productId + productName + productQuantity + productPrice;
        if(isReady) {
            bufferedWriter.write("\n");
        }
        bufferedWriter.write(line);
    }
}
