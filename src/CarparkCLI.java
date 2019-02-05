
import java.io.File;
import java.util.Scanner;

/**
 * Created by hdjones on 5/4/17.
 */
public class CarparkCLI {
    private Scanner userIn;
    private CarparkDriver cpd;
    public CarparkCLI() {
        userIn = new Scanner(System.in);
        cpd = null;
    }
    public void startCLI() {
        System.out.printf("Would you like to run the tests or boot the command line carpark management system? (test/boot): ");
        String answer = userIn.next();
        if(answer.toLowerCase().equals("test")) {
            CarparkTestCases.runTests();
            return;
        }
        System.out.println("Booting carpark management system...");
        startCPD();
        entryExitLoop();
    }
    private void startCPD() {
        System.out.print("Please enter the number of floors of the parking garage: ");
        Integer floors = userIn.nextInt();
        System.out.print("Please enter the number of parking spots per floor: ");
        Integer spotsPerFloor = userIn.nextInt();
        cpd = new CarparkDriver(floors, spotsPerFloor);
        cpd.loadRegistry();
        System.out.println("Success, garage management system online.");
    }
    private void entryExitLoop() {
        String answer;
        while(true) {
            System.out.print("\nWould you like to start the entry or exit terminal simulation? (entry/exit): ");
            answer = userIn.next();
            if(answer.toLowerCase().equals("entry"))
                entryLoop();
            else if(answer.toLowerCase().equals("exit"))
                exitLoop();
            else
                System.out.println("Error: Not a valid command");
        }
    }
    private void entryLoop() {
        System.out.printf("\nWelcome to the gated parking garage entry terminal.\n" +
                "Registered users park free by providing their registration card number.\n" +
                "Guests may park for a fee by providing their credit card.\n" +
                "You may go back to the entry/exit selection system by entering 'back'.\n");
        String answer;
        while(true) {
            System.out.print("\nAre you a registered member of this garage? (y/n/back):");
            answer = userIn.next();
            if(answer.toLowerCase().equals("y")) {
                System.out.printf("Please enter your four digit registration card number: ");
                answer = userIn.next();
                if(cpd.isValidPermCard(answer)) {
                    cpd.genFromDB(answer);
                    System.out.printf(cpd.parkCurrent());
                }
                else {
                    System.out.printf("Error: card not registered");
                }
            }
            else if(answer.toLowerCase().equals("n")) {
                System.out.print("Nonmembers are charged up to $10 daily (accumulating every 30 minutes) for parking.\n");
                System.out.print("If you want to precede, please enter your credit card number, otherwise, enter 'back': ");
                answer = userIn.next();
                if(answer.toLowerCase().equals("back")) {
                    continue;
                }
                else {
                    cpd.genFromID(answer);
                    System.out.println(cpd.parkCurrent());
                }
            }
            else if(answer.toLowerCase().equals("back")) {
                return;
            }
            else {
                System.out.println("Error: not a valid command.");
                continue;
            }
            return;
        }
    }
    private void exitLoop() {
        while(true) {
            String answer;
            System.out.printf("Welcome to the parking garage exit/payment terminal.\nPlease enter your card number" +
                    " or enter 'back' to go back:");
            answer = userIn.next();
            if(cpd.isParked(answer)) {
                System.out.println(cpd.unparkAndGetReceipt(answer));
                return;
            }
            else if(answer.toLowerCase().equals("back")) {
                return;
            }
            else {
                System.out.println("No record of this card is found, please try again.");
            }
        }
    }
}
