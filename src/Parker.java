
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by hdjones on 5/2/17.
 */
public abstract class Parker {
    public Parker() {}
    public abstract boolean idMatches(String id); //Checks whether personal id matches one supplied
    public abstract String signoutAndStatus(LocalDateTime arrivalTime);
}
