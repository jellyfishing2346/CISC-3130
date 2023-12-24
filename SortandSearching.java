import java.io.*;
import java.util.*;

public class SortandSearching {

    // Counters to compare the numbers in the array from sorting
    static int compareCount;
    static int compareCount2;
    static int compareCount3;


    public static void main(String[] args) throws Exception {
        File inFile = new File("input.txt");
        Scanner input = new Scanner(inFile);
        PrintWriter output = new PrintWriter(new File("output.txt"));


        // Read the numbers on each line from the file
        while (input.hasNextLine()) {
            String numLines = input.nextLine();
            int[] values = fileNumbers(numLines);

            output.println("Original Array: " + Arrays.toString(values));

            int[] bubbleSorts = Arrays.copyOf(values, values.length);
            bubbleSort(bubbleSorts);
            output.println("Bubble Sort: ");
            output.println("Sorted Array: " + Arrays.toString(bubbleSorts));
            output.println("Comparisons: " + compareCount);

            int[] quickSorts = Arrays.copyOf(values, values.length);
            compareCount2 = 0;
            quickSort(quickSorts, 0, quickSorts.length - 1);
            output.println("Quicksort: ");
            output.println("Sorted Array: " + Arrays.toString(quickSorts));
            output.println("Comparisons: " + compareCount2);

            int[] insertSorts = Arrays.copyOf(values, values.length);
            compareCount3 = 0;
            insertionSort(insertSorts);
            output.println("Insertion Sort: ");
            output.println("Sorted Array: " + Arrays.toString(insertSorts));
            output.println("Comparisons: " + compareCount3);

            // Print the sorting methods compare count from least to greatest to see which is efficient
            leastToGreatest(compareCount, compareCount2, compareCount3, output);

            output.println();
        }

        input.close();
        output.close();
    }

    // Return the data after reading the numbers in the file
    public static int[] fileNumbers(String lineRead) throws Exception {
        String[] numString = lineRead.split(" ");
        int[] values = new int[numString.length];

        for (int k = 0; k < numString.length; k++) {
            values[k] = Integer.parseInt(numString[k]);
        }

        return values;
    }

    // Using the bubble sort algorithm
    public static void bubbleSort(int[] arr) {
        compareCount = 0;
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                compareCount++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                }
            }
        }
    }

    // Using the quick sort algorithm
    public static void quickSort(int[] a, int low, int high) {
        if (low < high) {
            int pivot = partition(a, low, high);
            quickSort(a, low, pivot - 1);
            quickSort(a, pivot + 1, high);
        }
    }

    // Place the pivot position in the right place
    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            compareCount2++;
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // Using the insertion sort algorithm
    public static void insertionSort(int arr[]) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            compareCount3++;
            while (j >= 0 && arr[j] > key) {
                compareCount3++;
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    // Determine which sorting method has the least number of compares to show least from greatest
    public static void leastToGreatest(int bubbles, int quicks, int inserts, PrintWriter output) {
        if (bubbles < quicks && bubbles < inserts) {
            output.print("Bubble Sort: " + bubbles + " -> ");
            if (quicks < inserts) {
                output.print("Quick Sort: " + quicks + " -> " + "Insertion Sort: " + inserts + "\n");
            } else {
                output.print("Insertion Sort: " + inserts + " -> " + "Quick Sort: " + quicks + "\n");
            }
        }
        if (quicks < bubbles && quicks < inserts) {
            output.print("Quick Sort: " + quicks + " -> ");
            if (bubbles < inserts) {
                output.print("Bubble Sort: " + bubbles + " -> " + "Insertion Sort: " + inserts + "\n");
            } else {
                output.print("Insertion Sort: " + inserts + " -> " + "Bubble Sort: " + bubbles + "\n");
            }
        }
        if (inserts < quicks && inserts < bubbles) {
            output.print("Insertion Sort: " + inserts + " -> ");
            if (quicks < bubbles) {
                output.print("Quick Sort: " + quicks + " -> " + "Bubble Sort: " + bubbles + "\n");
            } else {
                output.print("Bubble Sort: " + bubbles + " -> " + "Quick Sort: " + quicks + "\n");
            }
        }
    }
}
