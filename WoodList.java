import java.util.*;
import java.io.*;

class Wood {
    LinkedList<String[]> records;
    HashMap<String, Integer> woodList;
    int discount;
    PrintWriter print;

    public Wood() throws Exception{
        records = new LinkedList<>();
        woodList = new HashMap<>();
        discount = 0;
        print = new PrintWriter("result.txt");
    }

    // List the pieces of wood by the cost
    public void addRecord(String wood, int q, double p) {
        String [] record = {"R", wood, String.valueOf(q), String.valueOf(p)};
        records.add(record);
        print.println("Received " + q + " pieces of " + wood + " wood at $" + p + " each.");
    }

    // Keeping track which customer will get a discount
    public void addDiscount(int reduce) {
        discount = reduce;
        print.println("Next customer will receive a " + discount + "% discount");
    }

    // Keep track of how much Oak/Cherry wood "stock" in a sale and its total until there is none left
    public void sell(String t, int d) {
        Iterator<String[]> read = records.iterator();
        double total = 0;
        while (read.hasNext()) {
            String [] readRecord = read.next();
            if (readRecord[1].equals(t)) {
                int remaining = Integer.parseInt(readRecord[2]);
                double cost = Double.parseDouble(readRecord[3]);
                int sold = Math.min(remaining,d);
                remaining -= sold;
                d -= sold;
                double sales = cost * sold * 1.4 * (1 - (double) discount / 100);
                total += sales;
                print.println("Sold " + sold + " pieces of " + t + " at $" + String.format("%.2f", (cost * 1.4)) + " each. Sales: $" + String.format("%.2f", sales));
                if (remaining > 0) {
                    readRecord[2] = String.valueOf(remaining);
                } else {
                    read.remove();
                }
                if (d == 0) {
                    break;
                }
            }
        }
        if (d > 0) {
            print.println("Remainder of " + d + " pieces of " + t + " wood is not available");
        }
        print.println("Total Sale: $" + String.format("%.2f", total));
    }


    // Print the amount of wood and its original price
    public void print() {
        int numOak = this.woodList.getOrDefault("O", 0);
        int numCherry = this.woodList.getOrDefault("C", 0);
        Double oakPrice = null;
        Double cherryPrice = null;

        for (String[] record: this.records) {
            String type = record[1];
            int amount = Integer.parseInt(record[2]);
            double cost = Double.parseDouble(record[3]);
            this.woodList.put(type, this.woodList.getOrDefault(type, 0) + amount);
            if (type.equals("O") && oakPrice == null) {
                oakPrice = cost;
            } else if(type.equals("C") && cherryPrice == null) {
                cherryPrice = cost;
            }
        }

        print.println("Oak Wood: " + numOak + " pieces, Original Price: $" + oakPrice);
        print.println("Cherry Maple Wood: " + numCherry + " pieces, Original Price: $" + cherryPrice);
    }
    // Keep track of the remaining wood
    public void remainingWood() {
        print.println("Remaining Wood pieces: ");

        for (String [] leftOver : records) {
            String type = leftOver[1];
            int remainAmount = Integer.parseInt(leftOver[2]);
            print.println(remainAmount + " pieces of " + type + " wood");
        }
        print.close();
    }

}

class WoodList {
    public static void main(String [] args) throws Exception {
        Wood records = new Wood();

        // Read the input file
        File input = new File("input.txt");
        Scanner read = new Scanner(input);
        while (read.hasNextLine()) {
            String lineRead = read.nextLine();
            String [] info = lineRead.split(" ");
            if (info[0].equals("R")) {
                records.addRecord(info[1], Integer.parseInt(info[2]), Double.parseDouble(info[3]));
            } else if (info[0].equals("P")) {
                records.addDiscount(Integer.parseInt(info[1]));
            } else if (info[0].equals("S")) {
                records.sell(info[1], Integer.parseInt(info[2]));
            }
        }
        read.close();
        records.print();
        records.remainingWood();
        }
    }