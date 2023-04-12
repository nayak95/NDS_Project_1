import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HashTable {
    static int entryCount = 0;

    // Function to generate Multi-Hashing Table
    public static Entry[] multiHash(int numberOfEntries, int numberOfFlows, int numberOfHashes) throws NoSuchAlgorithmException {
        Entry[] flows = new Entry[numberOfFlows];
        Entry[] table = new Entry[numberOfEntries];
        int[] s = new int[numberOfFlows];
        generateRandomEntries(flows, numberOfFlows, s);

        //Multi HashTable Implementation
        for (Entry flow : flows) {
            for (int j = 0; j < numberOfHashes; j++) {
                int index = Math.floorMod(hashFunction(flow.getFlowID(), s[j]), numberOfEntries);
                if (table[index] == null) {
                    table[index] = new Entry(flow);
                    entryCount++;
                    break;
                }
            }
        }
        return table;
    }
    //    Function to generate cuckoo hashtable
   public static Entry[] cuckooHash (int numberOfEntries, int numberOfFlows, int numberOfHashes, int cuckooSteps) throws NoSuchAlgorithmException {
        Entry[] flows = new Entry[numberOfFlows];
        Entry[] table = new Entry[numberOfEntries];
        int[] s = new int[numberOfFlows];
        generateRandomEntries(flows, numberOfFlows, s);
        // Cuckoo HashTable Implementation
        for (Entry flow : flows) {
            cuckooInsert (numberOfHashes, numberOfEntries, cuckooSteps, table, flow, s);
        }
        return table;
    }
    //     function to support implementation of cuckoo
    private  static boolean cuckooInsert (int numberOfHashes, int numberOfEntries, int cuckooSteps, Entry[] table, Entry flow, int[] s) throws NoSuchAlgorithmException {
        for (int j = 0; j < numberOfHashes; j++) {
            int index = Math.floorMod(hashFunction(flow.getFlowID(), s[j]), numberOfEntries);
            if (table[index] == null) {
                table[index] = new Entry(flow);
                entryCount++;
                return true;
            }
        }
        for (int j = 0; j < numberOfHashes; j++) {
            int index = Math.floorMod(hashFunction(flow.getFlowID(), s[j]), numberOfEntries);
            if(checkMove(table, index, cuckooSteps, numberOfHashes, s, numberOfEntries)){
                table[index] = new Entry(flow);
                entryCount++;
                return true;
            }
        }
        return false;
    }
    //     function to support implementation of cuckoo
    private static boolean checkMove(Entry[] table, int idx, int s, int k, int[] seedArray, int numberOfEntries) throws NoSuchAlgorithmException {
        int fID = table[idx].getFlowID();
        if (s <= 0)
            return false;
        for (int i = 0; i < k; i++) {
            int index = Math.floorMod(hashFunction(fID, seedArray[i]), numberOfEntries);
            if ( index != idx && table[index] == null){
                table[index] = new Entry(table[idx]);
                return true;
            }
        }
        for (int i = 0; i < k; i++) {
            int index = Math.floorMod(hashFunction(fID, seedArray[i]), numberOfEntries);
            if ( index != idx && checkMove(table, idx,s-1, k, seedArray, numberOfEntries)){
                table[index] = new Entry(table[idx]);
                return true;
            }
        }
        return false;
    }

    // Starting d-left Implementation
    public static Entry[] dLeftHash (int numberOfEntries, int numberOfFlows, int numberOfSegments) throws NoSuchAlgorithmException {
        Entry[] flows = new Entry[numberOfFlows];
        Entry[] table = new Entry[numberOfEntries];
        int[] s = new int[numberOfFlows];
        generateRandomEntries(flows, numberOfFlows, s);

        // dLeft HashTable Implementation logic
        for (Entry flow: flows) {
            for (int j = 0; j < numberOfSegments; j++) {
                int segmentSize = numberOfEntries/numberOfSegments;
                int index = Math.floorMod(hashFunction(flow.getFlowID(), s[j]), segmentSize) + segmentSize*j;
                if (table[index] == null) {
                    table[index] = new Entry(flow);
                    entryCount++;
                    break;
                }
            }

        }
        return table;
    }

    // function to generate random entries
    public static void generateRandomEntries(Entry[] flows, int numberOfFlows, int s[]){
        Random rand = new Random();
        Set<Integer> distinct = new HashSet<>();

        for (int i = 0; i < flows.length;){
            int distinctFlowID = rand.nextInt(numberOfFlows*10);
            if (!distinct.contains(distinctFlowID)) {
                distinct.add(distinctFlowID);
                flows[i] = new Entry(distinctFlowID);
                i++;
            }
        }

        for (int i = 0; i < s.length; i++)
            s[i] = rand.nextInt();

        entryCount = 0;
    }

    // function to generate hash
    public static int hashFunction(int flowID, int s) throws NoSuchAlgorithmException {
        int hashInput =  flowID ^ s;
        byte[] bytearray= ByteBuffer.allocate(4).putInt(hashInput).array();
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(bytearray);
        return  ByteBuffer.wrap(messageDigest).getInt();
    }


}
