import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Runner {
    static int numberOfEntries = 0;
    static int numberOfFlows = 0;
    static int numberOfHashes = 0;
    static int cuckooSteps = 0;
    public static void main(String[] args) {
        try {
            System.out.print("Which hash table do you want to implement:\n" +
                    "1. Multi-Hashing Table (Enter 1)\n" +
                    "2. Cuckoo Hash Table (Enter 2)\n" +
                    "3. d-left Hash Table (Enter 3)\n" +
                    "You choice: ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            Entry[] entriesInTable;
            switch (choice){
                case 1:         //Multi-hashing Table
                    stdInput();
                    entriesInTable = HashTable.multiHash(numberOfEntries, numberOfFlows, numberOfHashes);
                    writeNewFile();
                    System.out.println(HashTable.entryCount);
                    for (Entry e : entriesInTable) {
                        if (e != null) {
                            System.out.println(e.getFlowID());
                        }
                        else
                            System.out.println(0);
                    }

                    break;
                case 2:         //Cuckoo Hash Table
                    stdInput();
                    System.out.println("Enter the Cuckoo Steps: ");
                    cuckooSteps = sc.nextInt();
                    entriesInTable = HashTable.cuckooHash(numberOfEntries, numberOfFlows, numberOfHashes, cuckooSteps);
                    writeNewFile();
                    System.out.println(HashTable.entryCount);
                    for (Entry e : entriesInTable) {
                        if (e != null) {
                            System.out.println(e.getFlowID());
                        }
                        else
                            System.out.println(0);
                    }

                    break;
                case 3:         // d-left Hash Table
                    stdInput();

                    entriesInTable = HashTable.dLeftHash(numberOfEntries, numberOfFlows, numberOfHashes);
                    writeNewFile();
                    System.out.println(HashTable.entryCount);
                    for (Entry e : entriesInTable) {
                        if (e != null) {
                            System.out.println(e.getFlowID());
                        }
                        else
                            System.out.println(0);
                    }
                    break;
                default:
                    System.out.println("Enter a valid choice (i.e. between 1 and 3)");
            }
        }
        catch (InputMismatchException | FileNotFoundException | NoSuchAlgorithmException e){
            System.out.println("Bad numbers!! Please re-run the program and input valid numbers");
        }
    }
    public static void stdInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of table entries: ");
        numberOfEntries = sc.nextInt();
        System.out.println("Enter the number of flows: ");
        numberOfFlows = sc.nextInt();
        System.out.println("Enter the number of hashes/segments (for d-left): ");
        numberOfHashes = sc.nextInt();
    }
    public static void writeNewFile() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name for output file[format – output_typeOfHashTable]:");
        String fileName = sc.nextLine();
        PrintStream outputFile = new PrintStream(new File(fileName));
        PrintStream console = System.out;
        System.setOut(outputFile);
    }
}
