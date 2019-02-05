
import java.io.*;
import java.util.TreeMap;

/**
 * Created by hdjones on 5/2/17.
 */
public class RegParkerDB {
    //private data
    private java.util.TreeMap<String,RegisteredParker> data;

    //Constructors
    public RegParkerDB() {
        data = new TreeMap<String,RegisteredParker>();
    }
    public RegParkerDB(String filename) {
        data = null;
        bulkDeserialize(filename);
    }

    //methods
    public void addParker(RegisteredParker p1) {
        data.put(p1.getCardNumber(), p1);
    }
    public RegisteredParker getParkerByCard(String cardNum) {
        return data.get(cardNum); //call the boolean one first
    }
    public boolean cardIsRegistered(String cardNum) {
        return data.containsKey(cardNum); //could be bugged
    }
    public boolean bulkSerialize(String filename) {
        //Serializes the open DB to specified file
        try {
            FileOutputStream f1 = new FileOutputStream(filename);
            ObjectOutputStream o1 = new ObjectOutputStream(f1);
            o1.writeObject(data);
            f1.close();
            return true;
        } catch(IOException ioe) {
            return false;
        }
    }
    public boolean bulkDeserialize(String filename) {
        //loads serialized DB into memory
        try {
            FileInputStream f1 = new FileInputStream(filename);
            ObjectInputStream ins = new ObjectInputStream(f1);
            data = (TreeMap<String, RegisteredParker>) ins.readObject();
            f1.close();
            return true;
        } catch(IOException ioe) {
            return false;
        } catch(ClassNotFoundException ioe) {
            return false;
        }
    }
}
