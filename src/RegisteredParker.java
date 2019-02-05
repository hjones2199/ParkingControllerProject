
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by hdjones on 5/2/17.
 */
public class RegisteredParker extends Parker implements Serializable{
    private String name;
    private String cardNumber;
    boolean validcard;
    public RegisteredParker(String name, String cardNumber, boolean validcard) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.validcard = validcard;
    }
    public boolean idMatches(String id) {
        if(cardNumber.equals(id))
            return true;
        else
            return false;
    }
    public String signoutAndStatus(LocalDateTime arrivalTime) {
        return "Success: Have a nice day " + name;
    }
    public String getName() {
        return this.name;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public boolean cardIsValid() {
        return validcard;
    }
}
