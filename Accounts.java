import java.io.*;
import java.util.*;

class Accounts {
    public static void main(String args[]) throws Exception {
        File f1 = new File ("master.txt");
        File f2 = new File("transaction.txt");
        Scanner input = new Scanner(f1);

        // Read the master file
        while (input.hasNext()) {
            String lineRead = input.nextLine();
            String [] parts = lineRead.split(" ");
            int customerNumber = Integer.parseInt(parts[0]);
            String customerName = parts[1];
            double balanceDue = Double.parseDouble(parts[2]);

            // Print the records for the current customer
            printRecords(customerName, customerNumber, balanceDue);

            // View the transaction records for the master record and get balance due
            balanceDue = transaction(customerNumber, customerName, balanceDue, f2);

            System.out.printf("%30s Balance Due           $%.2f\n", "", balanceDue);

        }
    }

    // Handle the transactions of the customer account from the transaction file
    public static double transaction(int customerNumber, String customerName, double balanceDue, File f2) throws Exception{
        Scanner input = new Scanner(f2);
        int transNb = 1;
        while (input.hasNext()) {
            String lineRead = input.nextLine();
            String [] parts = lineRead.split(" ");
            char records = parts[0].charAt(0);
            int number = Integer.parseInt(parts[1]);

            // Check if the invididual placed an order
            if (records == 'O') {
                int quantity = Integer.parseInt(parts[2]);
                String itemName = parts[3];
                double cost = Double.parseDouble(parts[4]);

                // Check if the record has a discount
                if (parts.length > 5) {
                    int discount = Integer.parseInt(parts[5]);
                    cost -= (cost * discount) / 100.0;
                }
                double amounts = quantity * cost;
                balanceDue += amounts;

                System.out.printf("%-30d %-20s %f\n", transNb, itemName, amounts);

            }
            // Check if the individual placed an payment
            else if (records == 'P') {
                double payments = Double.parseDouble(parts[2]);

                // Check if the record has a discount
                if (parts.length > 3) {
                    int discount = Integer.parseInt(parts[3]);
                    payments -= (payments * discount) / 100.0;
                }
                balanceDue -= payments;

                System.out.printf("%-30d %-20s %f\n", transNb, "payment", payments);
            }
            transNb++;
        }
        return balanceDue;
    }

    // Print the customer records
    public static void printRecords(String customerName, int customerNumber, double balanceDue) {
        System.out.printf("%-30s %d\n", customerName, customerNumber);
        System.out.printf("%30s Previous balance      $%.2f\n", "", balanceDue);
        System.out.println();

    }
}