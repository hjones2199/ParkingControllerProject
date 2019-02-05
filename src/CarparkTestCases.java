
import java.time.LocalDateTime;

/**
 * Test cases for the various classes in the carpark project
 */
public class CarparkTestCases {
    public static void runTests() {
	generateTestDB("ParkerRegistry.parkdb");
        testUnRegisteredParker();
        testRegisteredParker();
        testDB();
        testSim();
        testCarparkDriver();
    }
    public static void testCarparkDriver() {
        /**
         * Tests the completed CarparkDriver API with various registered and unregistered users.
         */
        CarparkDriver d1 = new CarparkDriver(1,2);
        //Tests parking a registered User and unregistered user, and unparking them, and attempts to add to full lot
        if(d1.isValidPermCard("0001"))
            d1.genFromDB("0001");
        System.out.println(d1.parkCurrent());
        d1.genFromID("1234567812345678");
        System.out.println(d1.parkCurrent());
        System.out.println(d1.unparkAndGetReceipt("0001"));
        System.out.println(d1.unparkAndGetReceipt("1234567812345678"));

        //Tests parking a user when lot is full
        d1.genFromID("5555444433332222");
        System.out.println(d1.parkCurrent());
        d1.genFromID("5555444433332221");
        System.out.println(d1.parkCurrent());
        d1.genFromID("5555444433332220");
        System.out.println(d1.parkCurrent());
    }
    public static void testUnRegisteredParker() {
        /**
         *  Tests the unregistered parker class in a fail/success format
         *  */
        System.out.printf("\n\nBlackbox test of UnregisteredParker: ");
        Parker p1 = new UnregisteredParker("1234567812345678");
        if(p1.idMatches("1234567812345678"))
            System.out.printf("\nMatching ID: success, ");
        else
            System.out.printf("\nMatching ID: failed, ");
        if(p1.idMatches("1234123412341234"))
            System.out.printf("\nNonmatching ID: failed, ");
        else
            System.out.printf("\nNonmatching ID: success, ");
        String testPricing = p1.signoutAndStatus(LocalDateTime.now().minusDays(3).minusHours(5).minusMinutes(30));
        if(testPricing.equals("Success: You have been parked for: 3 days 5 hours 30 minutes\nCard ending in 5678 has been charged $38.32"))
            System.out.printf("\nSignout and receipt: success");
        else
            System.out.printf("\nSignout and receipt: failed");
    }
    public static void testRegisteredParker() {
        /**
         * Tests the RegisteredParker class in a pass/fail format
         */
        System.out.printf("\n\nBlackbox test of RegisteredParker: ");
        RegisteredParker p1 = new RegisteredParker("Hunter Jones","0001",true);
        if(p1.getName() == "Hunter Jones")
            System.out.printf("\nUsername: success,");
        else
            System.out.printf("\nUsername: failed,");
        if(p1.getCardNumber() == "0001")
            System.out.printf("\nID Number: success,");
        else
            System.out.printf("\nID Number: failed,");
        if(p1.signoutAndStatus(LocalDateTime.now().minusDays(3).minusHours(5).minusMinutes(30)).equals("Success: Have a nice day Hunter Jones"))
            System.out.printf("\nSignout: success");
        else
            System.out.printf("\nSignout: failed");
    }
    public static void testDB() {
        System.out.printf("\n\nBlackbox test of database: ");
        RegParkerDB db1 = new RegParkerDB();
        db1.addParker(new RegisteredParker("Hunter Jones","0001", true));
        db1.addParker(new RegisteredParker("John Adams", "0002", true));
        if(!db1.bulkSerialize("test1.db"))
            System.out.printf("Serialization: failed");
        RegParkerDB db2 = new RegParkerDB();
        if(!db2.bulkDeserialize("test1.db"))
            System.out.printf("Deserialization: failed");
        if(!db2.getParkerByCard("0001").getName().equals("Hunter Jones"))
            System.out.printf("Deserialization: failed");
    }
    public static void testSim() {
        System.out.printf("\n\nBlackbox test of carpark simulator: ");
        CarparkSimulator sim = new CarparkSimulator(100,100);
        sim.parkAtBestSpot(new UnregisteredParker("1234567812345678"));
        if(!(sim.numEmptySpots() == 9999))
            System.out.printf("\nCount number of empty spots: failed");
        sim.unparkByID("1234567812345678");
        if(!(sim.numEmptySpots() == 10000))
            System.out.printf("Clear a parking spot: failed");
    }
    public static void generateTestDB(String filename) {
        /**
         * Generates a usable persistent database of 12 Registered Parkers for testing
         */
        RegParkerDB testDB = new RegParkerDB();
        testDB.addParker(new RegisteredParker("Hunter Jones", "0001", true));
        testDB.addParker(new RegisteredParker("Jebediah Kerman", "0002", true));
        testDB.addParker(new RegisteredParker("John Mccready", "0003", true));
        testDB.addParker(new RegisteredParker("Scarlett Valentine", "0004", true));
        testDB.addParker(new RegisteredParker("Jerry Garcia", "0005", true));
        testDB.addParker(new RegisteredParker("Rick Grimes", "0006", true));
        testDB.addParker(new RegisteredParker("Scott Manly", "0007", true));
        testDB.addParker(new RegisteredParker("Satya Vaswani", "0008", true));
        testDB.addParker(new RegisteredParker("Walter White", "0009", true));
        testDB.addParker(new RegisteredParker("Augustus Caesar", "0010", true));
        testDB.addParker(new RegisteredParker("Attia Julii", "0011", true));
        testDB.addParker(new RegisteredParker("Richard Lionheart", "0012", true));
        testDB.bulkSerialize(filename);
    }

}
