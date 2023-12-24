import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
public class Babysitters {
    public static void main(String[] args) throws Exception {
        Map<Integer, Sitter> user = readData();
        readOtherData(user);
        printFees(user);
    }

    // Read in the following data from the  personel file
    public static Map<Integer, Sitter> readData() throws Exception {
        Map<Integer, Sitter> iSitter = new HashMap<>();
        Scanner input = new Scanner(new File("personelData.text"));
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            int numEmployee = Integer.parseInt(line);
            String name = input.nextLine().trim();
            String address = input.nextLine().trim();
            String location = input.nextLine().trim();
            String [] lines =input.nextLine().trim().split("\\s+");
            double before9 = Double.parseDouble(lines[0]);
            double between9after = Double.parseDouble(lines[1]);
            double afterMidnight = Double.parseDouble(lines[2]);
            iSitter.put(numEmployee, new Sitter(numEmployee, name, address, location, before9, between9after, afterMidnight));
        }
        return iSitter;
    }

    // Read in the following data from the payroll file
    public static void readOtherData(Map<Integer, Sitter> iSitter) throws Exception {
        Scanner input = new Scanner(new File("payrollData.text"));
        while (input.hasNextLine()) {
            String line=input.nextLine();

            // Check if the line is empty
            if(!line.equals("")){
                int employeeNum = Integer.parseInt(line.trim());
                int numDays = Integer.parseInt(input.nextLine().trim());
                Sitter iSit = iSitter.get(employeeNum);
                if (iSit != null) {
                    for (int i = 0; i < numDays; i++) {
                        String[] time = input.nextLine().trim().split(" ");
                        LocalTime start;
                        LocalTime end;
                        if(time[0].length()==4){
                            start = LocalTime.parse("0"+time[0]);
                        }else{
                            start = LocalTime.parse(time[0]);
                        }
                        if(time[1].length()==4){
                            end = LocalTime.parse("0"+time[1]);
                        }else{
                            end = LocalTime.parse(time[1]);
                        }

                        iSit.addDay(start, end);
                    }
                }
            }

        }
    }

    // Print the baby sitter and their fee in alphabetical order
    public static void printFees(Map<Integer, Sitter> iSitter) throws Exception {
        List<Sitter> alphabetize = new ArrayList<>(iSitter.values());
        Collections.sort(alphabetize, Comparator.comparing(Sitter::getName));
        PrintWriter output = new PrintWriter("output.text");
        for (Sitter iSit : alphabetize) {
            double feeAmount = iSit.calculate();
            output.println(iSit.getName() + ": $" + feeAmount + "\n");
        }
        output.close();
    }
}


class Sitter {
    private int employeeNum;
    private String name;
    private String address;
    private String location;
    private double before9;
    private double between9after;
    private double afterMidnight;
    private Map<LocalTime, LocalTime> dayWork;

    public Sitter(int employeeNum, String name, String address, String location, double before9, double between9after, double afterMidnight) {
        this.employeeNum = employeeNum;
        this.name = name;
        this.address = address;
        this.location = location;
        this.before9 = before9;
        this.between9after = between9after;
        this.afterMidnight = afterMidnight;
        this.dayWork = new HashMap<>();
    }
    public void addDay(LocalTime begin, LocalTime end) {
        dayWork.put(begin, end);
    }
    public double calculate() {
        double total = 0.0;
        for (Map.Entry<LocalTime, LocalTime> track : dayWork.entrySet()) {
            LocalTime begin = track.getKey();
            LocalTime end = track.getValue();
            if (begin.isAfter(end)) {
                end = end.plusHours(24);
            }

            int first = begin.getHour();
            int last = end.getHour();

            if (first >= 6 && last <= 21) {
                total += Math.abs(last - first) * this.before9;
            } else if (first >= 21 && last <= 24) {
                total += Math.abs(last - first) * this.between9after;
            } else if (first >= 0 && last <= 6) {
                total += Math.abs(last - first) * this.afterMidnight;
            }
        }
        return total;
    }
    public String getName(){
        return name;
    }
}