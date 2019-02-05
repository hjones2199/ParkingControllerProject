
import java.time.LocalDateTime;

/**
 * Created by hdjones on 5/2/17.
 */
public class CarparkSimulator {
    //private data
    private ParkingSpot[][] park;

    //Constructors
    public CarparkSimulator(int floorcount, int spotsPerFloor) {
        park = new ParkingSpot[floorcount][spotsPerFloor];
    }

    //Methods
    public Parker getParkerByID(String id) {
        //Attempts to find the specified parker by id in the lot and return it
        try {
            return getSpotByID(id).p;
        } catch(ArrayIndexOutOfBoundsException e) {
            throw e;
        }
    }
    public IntPair parkAtBestSpot(Parker pk) {
        IntPair bestspot = bestEmptySpot();
        parkAt(bestspot.first,bestspot.second,pk);
        return bestspot;
    }
    public IntPair bestEmptySpot() {
        for(int i = 0; i < park.length; i++) {
            for(int j = 0; j < park[i].length; j++) {
                if(park[i][j] == null || park[i][j].isEmpty()) {
                    IntPair tmp = new IntPair();
                    tmp.first = i;
                    tmp.second = j;
                    return tmp;
                }
            }
        }
        throw new ArrayIndexOutOfBoundsException("No empty spots in the lot.");
    }
    public void parkAt(int i, int j, Parker pkr) {
        park[i][j] = new ParkingSpot();
        park[i][j].p = pkr;
        park[i][j].t = LocalDateTime.now();
    }
    public String unparkByID(String id) {
        ParkingSpot tmp = getSpotByID(id);
        String status = tmp.p.signoutAndStatus(tmp.t);
        tmp.p = null;
        tmp.t = null;
        return status;
    }
    public int numEmptySpots() {
        //Calculates the number of empty spots in the lot
        int total = 0;
        for(int i = 0; i < park.length; i++)
            for(int j = 0; j < park[i].length; j++)
                if(park[i][j] == null || park[i][j].isEmpty())
                    ++total;
        return total;
    }
    public boolean isParked(String id) {
        try {
            getSpotByID(id);
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
    private ParkingSpot getSpotByID(String id) {
        for(int i = 0; i < park.length; i++) { //for every floor
            for(int j = 0; j < park[i].length; j++) { //in every spot
                if(((park[i][j] != null) && !park[i][j].isEmpty()) && park[i][j].p.idMatches(id)) //if the id matches
                    return park[i][j]; //return the Parking spot
            }
        }
        throw new ArrayIndexOutOfBoundsException("Parker ID has no match in the lot.");
    }
}

class ParkingSpot {
    public Parker p = null;
    public LocalDateTime t = null;
    public boolean isEmpty() {
        if(p == null && t == null)
            return true;
        else
            return false;
    }
}