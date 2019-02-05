
/**
 * Created by hdjones on 5/2/17.
 */
public class CarparkDriver {
    /**
     * CarparkDriver class is the API wrapper that combines the carpark simulator
     * with the database of registered parkers in an easily usable programming
     * interface. It allows for logins, querying the database for a particular
     * cardID, and is ment to be used directly by user interface software layers.
     */
    //Private data
    private RegParkerDB registry;
    private CarparkSimulator parksim;
    private Parker currentParker;

    //Constructors
    public CarparkDriver(int floors, int spotsPerFloor) {
        parksim = new CarparkSimulator(floors,  spotsPerFloor);
        loadRegistry();
    }

    //Methods
    public void loadRegistry() {
        //Loads database from file
        registry = new RegParkerDB("ParkerRegistry.parkdb");
    }

    public void saveRegistry() {
        //Saves database to file
        registry.bulkSerialize("ParkerRegistry.parkdb");
    }

    public String parkCurrent() {
        if(parksim.numEmptySpots() < 1)
            return "No parking spots available";
        IntPair tmp = parksim.parkAtBestSpot(currentParker);
        openGate(); //Does nothing, but in real world would open the gate
        return "Your parking location is on floor " + Integer.toString(tmp.first + 1) +
                " at spot " + Integer.toString(tmp.second + 1);
    }

    public void genFromID(String id) {
        currentParker = new UnregisteredParker(id);
    }
    public boolean genFromDB(String id) {
        if(isValidPermCard(id)) {
            currentParker = registry.getParkerByCard(id);
            return true;
        }
        else
            return false;
    }

    public boolean isValidPermCard(String id) {
        if(registry.cardIsRegistered(id))
            return true;
        else
            return false;
    }

    public boolean isParked(String id) {
        if(parksim.isParked(id))
            return true;
        else
            return false;
    }

    public String unparkAndGetReceipt(String id) {
        return parksim.unparkByID(id);
    }

    private void openGate() {
        //Would interface with actual gate control mechanism in real world
        //here, it does nothing.
    }

}
