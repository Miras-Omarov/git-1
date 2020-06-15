package com.github.mo;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CRUD1 {
    private static String mode,path;
    private static boolean isFileFound = true,isReady = false;
    private static final Scanner scanner = new Scanner(System.in);
    private static BufferedReader bufferedReader = null;
    private static BufferedWriter bufferedWriter = null;

    public static void main(String[] args) throws IOException {
        start();
        if(isFileFound) {
            switch(mode.toLowerCase()) {
                case "create":
                    int id = read();
                    create(id);
                    break;
                case "update":
                    update();
                    break;
                case "delete":
                    delete();
                    break;
                default:
                    System.out.println("Something went wrong..");
            }
            bufferedWriter.close();
            bufferedReader.close();
            scanner.close();
        }
    }

    public static void start() {
        System.out.println("Input a file path...");
        path = scanner.nextLine();
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            bufferedWriter = new BufferedWriter(new FileWriter(path,true));
        } catch(FileNotFoundException e) {
            System.out.println("File was not found.");
            isFileFound = false;
            return;
        } catch(IOException e) {
            System.out.println(e.getMessage());
            isFileFound = false;
            return;
        }
        System.out.println("What would you like to do?\nCreate\nUpdate\nDelete");
        mode = scanner.nextLine();
    }

    private static int read() throws IOException {
        int maxId = 0,id;
        while(bufferedReader.ready()) { // Finds the max ID in the file
            isReady = true;
            String line = bufferedReader.readLine();
            id = Integer.parseInt(line.substring(0,8).trim());
            maxId = Math.max(maxId, id);
        }
        return maxId + 1;
    }

    private static void create(int id) throws IOException {
        System.out.println("productName:price:quantity");
        System.out.println("NOTE:Product name cannot be more than 30 letters,whereas price cannot be more than 8 digits.\nInput the product name, price, and quantity as shown.");

        String productString = scanner.nextLine();
        String[] product = productString.split(":");
        String productId = String.valueOf(id);
        String productName = product[0];
        String productPrice = product[1];
        String productQuantity = product[2];

        while(productId.length() < 8) {
            productId += " ";
        }
        while(productName.length() < 30) {
            productName += " ";
        }
        while(productPrice.length() < 8) {
            productPrice += " ";
        }

        String line = productId + productName + productPrice + productQuantity;
        if(isReady) {
            bufferedWriter.write("\n");
        }
        bufferedWriter.write(line);
        System.out.println("The product has been successfully created.");
    }

    public static void update() throws IOException {
        System.out.println("Enter ID");
        String productID = scanner.nextLine();
        System.out.println("Enter the new product(productName:price:quantity)");
        String[] newProduct = scanner.nextLine().split(":");
        String productName = newProduct[0];
        String productPrice = newProduct[1];
        String productQuantity = newProduct[2];
        while(productName.length() < 30){
            productName += " ";
        }
        while(productPrice.length() < 8){
            productPrice += " ";
        }
        ArrayList<String> list = new ArrayList<>();

        while(bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            String id = line.substring(0,8);
            if(id.trim().equals(productID)) {
                line = "";
                line += id + productName + productPrice + productQuantity;
            }
            list.add(line);
        }
        bufferedReader.close();
        BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(path));
        bufferedWriter2.write("");
        bufferedWriter2.close();
        int alert = 0;
        for(String line : list) {
            bufferedWriter.write(line);
            if(alert == list.size() - 1){}
            else
                bufferedWriter.write("\n");
            alert++;
        }
        bufferedWriter.close();
        scanner.close();
        System.out.println("The product has been successfully updated.");
    }

    public static void delete() throws IOException {
        System.out.println("Enter the product ID..");
        String productID = scanner.nextLine();
        ArrayList<String> list = new ArrayList<>();
        while(bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            String id = line.substring(0,8);
            if(id.trim().equals(String.valueOf(productID))) {
            }
            else list.add(line);
        }
        bufferedReader.close();
        BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(path));
        bufferedWriter2.write("");
        bufferedWriter2.close();
        int alert = 0;
        for(String line : list) {
            bufferedWriter.write(line);
            if(alert == list.size() - 1){}
            else
                bufferedWriter.write("\n");
            alert++;
        }
        bufferedWriter.close();
        scanner.close();
        System.out.println("The product has been successfully deleted.");
    }
}
