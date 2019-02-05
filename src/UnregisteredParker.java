
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by hdjones on 5/2/17.
 */
public class UnregisteredParker extends Parker {
    //Data
    private String ccinfo; //stores credit card info

    //Constructors
    public UnregisteredParker(String ccinfo) {
        this.ccinfo = new String(ccinfo);
    }

    //Methods
    public boolean idMatches(String id) {
        //Comparator of ccinfo to supplied id
        if(id.equals(ccinfo))
            return true;
        else
            return false;
    }

    public String signoutAndStatus(LocalDateTime arrivalTime) {
        LocalDateTime rightnow = LocalDateTime.now(); //current time as of card swipe
        long monthdelta = arrivalTime.until(rightnow, ChronoUnit.MONTHS); //months since arrival
        if(monthdelta > 0)
            return "Error: Your car has been impounded for being parked over 2 months";
        long daydelta = arrivalTime.until(rightnow, ChronoUnit.DAYS); //days since arrival
        long hourdelta = arrivalTime.until(rightnow, ChronoUnit.HOURS) - (daydelta*24); //hours since last day turnover
        long minutedelta = arrivalTime.until(rightnow, ChronoUnit.MINUTES) - ((daydelta * 24 * 60) + hourdelta * 60); //minutes since last hours turnover
        if(daydelta == 0 && hourdelta == 0 && minutedelta < 30) //charge nothing if < 30 minutes have been spent parked
            return "Success: Card has not been charged as only " + minutedelta + " minutes have passed since sign in.";
        if(minutedelta < 30) //only charge in 30 minute intervals
            minutedelta = 0;
        else
            minutedelta = 30;
        double price = (daydelta * 10) + (hourdelta * 0.416) + (minutedelta * .208); //calculates the price to charge
        return "Success: You have been parked for: " + daydelta + " days " + hourdelta + " hours " +
                minutedelta + " minutes\n" + chargeCC(price);
    }

    public String chargeCC(double price) {
        //In real world would interface with a credit card scanner
        return "Card ending in " + ccinfo.substring(12,16) + " has been charged $" + price;
    }

}
