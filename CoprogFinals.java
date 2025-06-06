import java.util.*;
import java.util.regex.*;
import java.time.*;
import java.time.format.*;
import java.io.*;

public class CoprogFinals {
    static Scanner scn = new Scanner(System.in);
    static boolean isTransactionRunning = false;
    static boolean isRunning = true;
    static String logInUser = "";

    public static void clearscreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static boolean authenticator(ArrayList<String> usernames, ArrayList<String> passwords){
        while (usernames.isEmpty() && passwords.isEmpty()){
            System.out.println("No registered accounts detected, please register an account!");
            System.out.println("Press enter to continue!");
            scn.nextLine();
            signUp(usernames, passwords);
        }
        while (true){
            System.out.println("Select a number");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            String decision = "";
            while (true) {
                try {
                    decision = scn.nextLine();
                    if (decision.equals("1") || decision.equals("2")) {
                        break;
                    }
                    System.out.println("Invalid option! Try again!");
                }
                catch (Exception e) {
                    System.out.println("Input error.");
                }
            }

            if (decision.equals("1")){
                boolean authenticated = logIn(usernames, passwords);
                if (authenticated){
                    return true;
                }
            } else if (decision.equals("2")){
                signUp(usernames, passwords);
            }
        }
    }

    public static void signUp(ArrayList<String> usernames, ArrayList<String> passwords){
        while (true){
            Pattern usernamePattern = Pattern.compile("^[a-z]+(?:_[a-z]+)?_[a-z]+@dlsl\\.edu\\.ph$");
            Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,20}$");
            System.out.println("Username must be your De La Salle email (ex. aryl_ross_manalo@dlsl.edu.ph)");
            System.out.print("Input username:");
            String tempUsername = "";
            while (true) {
                try {
                    tempUsername = scn.nextLine();
                    break;
                }
                catch (Exception e) {
                    System.out.println("Input error.");
                }
            }
            
            System.out.println("Password must consist of 8-20 characters, lower and uppercase characters, a digit, and a special character.");
            System.out.print("Input password: ");
            String tempPassword = "";
            while (true) {
                try {
                    tempPassword = scn.nextLine();
                    break;
                }
                catch (Exception e) {
                    System.out.println("Input error.");
                }
            }

            Matcher usernameMatcher = usernamePattern.matcher(tempUsername);
            Matcher passwordMatcher = passwordPattern.matcher(tempPassword);

            if (!usernameMatcher.matches() && !passwordMatcher.matches()){
                System.out.println("Username and Password are Invalid! Please try again");
                System.out.println("Press enter to proceed");
                scn.nextLine();
            } else if (!usernameMatcher.matches()){
                System.out.println("Username Invalid! Please try again");
                System.out.println("Press enter to proceed!");
                scn.nextLine();
            } else if (!passwordMatcher.matches()){
                System.out.println("Password Invalid! Please try again");
                System.out.println("Press enter to proceed!");
                scn.nextLine();
            } else{
                usernames.add(tempUsername);
                passwords.add(tempPassword);
                System.out.println("Signed up successfully!");
                break;
            }
        }
    }

    public static boolean logIn(ArrayList<String> usernames, ArrayList<String> passwords){
        boolean isAccepted = false;
        while(true){
            System.out.print("Username: ");
            String inputUser = scn.nextLine();
            System.out.print("Password: ");
            String inputPass = scn.nextLine();

            for (int i=0;i<usernames.size();i++){
                if (inputUser.equals(usernames.get(i)) && inputPass.equals(passwords.get(i))){
                    isAccepted = true;
                    logInUser = inputUser;
                }
            }
            if (isAccepted){
                System.out.println("Credentials Accepted!");
                System.out.println("Press enter to continue");
                scn.nextLine();
                break;
            } else {
                System.out.println("Credentials are wrong! Please try again!");
                System.out.println("Press enter to continue");
                scn.nextLine();
            }
        }
        return true;
    }

    public static void addItems(ArrayList<String> cart, ArrayList<Double> price, ArrayList<Integer> quantity){
        clearscreen();
        scn.nextLine();
        double itemPrice = 0;
        boolean adding = true;
        int quantityItem=0;
        System.out.println("Enter Item(s)");
        while (adding){
            System.out.print("Enter Item name (Enter exit to exit): ");
            String itemName = "";
            while (true) {
                try {
                    itemName = scn.nextLine().toUpperCase();
                    break;
                }
                catch (Exception e) {
                    System.out.println("Input error.");
                }
            }
            if (itemName.equals("EXIT")){
                adding = false;
                System.out.println("Press enter to proceed");
                scn.nextLine();
            } else {
                while (true) {
                    System.out.print("Enter Quantity: ");
                    try {
                        quantityItem = scn.nextInt();
                        break;
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a number.");
                        scn.nextLine();
                    }
                }
                while (true) {
                    System.out.print("Enter item price: ");
                    try {
                        itemPrice = scn.nextDouble();
                        break;
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a number.");
                        scn.nextLine();
                    }
                }
                scn.nextLine();
            }
            cart.add(itemName);
            price.add(itemPrice);
            quantity.add(quantityItem);
            cart.remove("EXIT");
        }
    }

    public static void removeItems(ArrayList<String> cart, ArrayList<Double> price, ArrayList<Integer> quantity){
        clearscreen();
        boolean removing = true;
        while (removing){
            System.out.println("Select item to remove: ");
            for (int i=0;i<cart.size();i++){
                int numDisplay = i + 1;
                System.out.println(numDisplay + ". " + cart.get(i));
            }
            int removeItem = -1;
            while (true) {
                try {
                    removeItem = scn.nextInt() - 1;
                    break;
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    scn.nextLine();
                }
            }
            cart.remove(removeItem);
            price.remove(removeItem);
            quantity.remove(removeItem);

            System.out.println("Keep removing? (y/n)");
            scn.nextLine();
            String remove = "";
            while (true) {
                try {
                    remove = scn.nextLine().toLowerCase();
                    if (remove.equals("y") || remove.equals("n")) {
                        break;
                    }
                    System.out.println("Please enter 'y' or 'n'.");
                }
                catch (Exception e) {
                    System.out.println("Input error.");
                }
            }
            
            if (remove.equals("y")){
                removing = true;
            } else if (remove.equals("n")){
                removing = false;
            } else {
                System.out.println("Select valid option! Removing will continue.");
            }
        }   
    }

    public static void displayItems(ArrayList<String> cart, ArrayList<Double> price, ArrayList<Integer> quantity){
        if (cart.isEmpty()){
            System.out.println("No items in cart");
            System.out.println("Press enter to continue!");
            scn.nextLine();
        } else if (!cart.isEmpty()){
            System.out.println("Cart:");
            for (int i=0;i<cart.size();i++){
                System.out.println(i+1+ ". " + cart.get(i) + " " + quantity.get(i) + "x  PHP " + price.get(i) );
            }
            System.out.println("Press enter to continue!");
            scn.nextLine();
        }
    }

    public static void payment(ArrayList<String> cart, ArrayList<Double> price, ArrayList<Integer> quantity){
        clearscreen();
        boolean paying = true;
        int totalPrice = 0;
        int amountPaid = 0;
        int change = 0;

        for (int i=0;i<cart.size();i++){
            totalPrice += (price.get(i) * quantity.get(i));               
        }

        while(paying){
            System.out.println("Items: ");
            for (int i=0;i<cart.size();i++){
                int numDisplay = i + 1;
                System.out.println(numDisplay + ". " + cart.get(i) + " " + price.get(i) + " x" + quantity.get(i));
            }

            totalPrice -= amountPaid;
            System.out.println("Total price: " + totalPrice);

            while (true) {
                System.out.print("Input amount paid: ");
                try {
                    amountPaid = scn.nextInt();
                    break;
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    scn.nextLine();
                }
            }
            scn.nextLine();
            change = amountPaid - totalPrice;
            
         if (amountPaid>=totalPrice){
            System.out.println("Change: PHP" + change);
            paying = false;
         } else {
            System.out.println("Insufficient Payment!");
         }
        }
        isTransactionRunning = receipt(cart, quantity, price, amountPaid, change, totalPrice, logInUser);
    }

    public static boolean receipt(ArrayList<String> cart, ArrayList<Integer> quantity, ArrayList<Double> price, int amountPaid, int change, int totalPrice, String logInUser){
        clearscreen();
        for (int i=0;i<cart.size();i++){
            int numDisplay = i + 1;
            System.out.println(numDisplay + ". " + cart.get(i) + " X" + quantity.get(i));
        }

        System.out.println("Total Price: PHP" + totalPrice);
        System.out.println("Amount Paid: PHP" + amountPaid);
        System.out.println("Change: PHP" + change);

        isRunning = false;

        fileHandling(cart, price, quantity, logInUser, totalPrice, amountPaid, change);

        return true;
    }
    
    public static void fileHandling(ArrayList<String> cart, ArrayList<Double> price, ArrayList<Integer> quantity, String logInUser, int totalPrice, int amountPaid, int change){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        try{
            File file = new File("transactions.txt");
        } catch (Exception e){
            System.out.println("An error occured. Please try again!");
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))){
            writer.newLine();
            writer.write("Transaction Made By: " + logInUser);  
            writer.newLine();
            writer.write("Trancation Date and Time: " + formattedDateTime);
            writer.newLine();
            writer.newLine();
            writer.write("Items: ");
            writer.newLine();
            for (int i=0;i<cart.size();i++){
                writer.write(cart.get(i) + " " + quantity.get(i) + "x " + price.get(i));
                writer.newLine();
            }
            writer.newLine();
            writer.write("Total Price: PHP" + totalPrice);
            writer.newLine();
            writer.write("Amount Paid: PHP" + amountPaid);
            writer.newLine();
            writer.write("Change: PHP" + change);
            writer.newLine();
            writer.write("----------------------------------------------------------------------------------");
        } catch (Exception e){
            System.out.println("An error occured. Please try again!");
        }
    }

    public static void editItems(ArrayList<String> cart, ArrayList<Double> price, ArrayList<Integer> quantity){
        clearscreen();
        if (cart.isEmpty()){
            System.out.println("No items in cart to edit.");
            System.out.println("Press enter to continue!");
            scn.nextLine();
            return;
        }
        boolean editing = true;
        while (editing) {
            System.out.println("Select item to edit:");
            for (int i = 0; i < cart.size(); i++) {
                int numDisplay = i + 1;
                System.out.println(numDisplay + ". " + cart.get(i) + " " + quantity.get(i) + "x  PHP " + price.get(i));
            }
            int editIndex = -1;
            while (true) {
                System.out.print("Enter item number to edit: ");
                try {
                    editIndex = scn.nextInt() - 1;
                    if (editIndex >= 0 && editIndex < cart.size()) {
                        break;
                    }
                    System.out.println("Invalid index. Try again.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    scn.nextLine();
                }
            }
            scn.nextLine();
            System.out.println("What do you want to edit?");
            System.out.println("1. Name");
            System.out.println("2. Quantity");
            System.out.println("3. Price");
            int editChoice = 0;
            while (true) {
                System.out.print("Enter choice: ");
                try {
                    editChoice = scn.nextInt();
                    if (editChoice >= 1 && editChoice <= 3) {
                        break;
                    }
                    System.out.println("Invalid choice. Try again.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    scn.nextLine();
                }
            }
            scn.nextLine();
            if (editChoice == 1) {
                System.out.print("Enter new name: ");
                String newName = "";
                while (true) {
                    try {
                        newName = scn.nextLine().toUpperCase();
                        break;
                    } catch (Exception e) {
                        System.out.println("Input error.");
                    }
                }
                cart.set(editIndex, newName);
            } else if (editChoice == 2) {
                int newQuantity = 0;
                while (true) {
                    System.out.print("Enter new quantity: ");
                    try {
                        newQuantity = scn.nextInt();
                        if (newQuantity > 0) {
                            break;
                        }
                        System.out.println("Quantity must be positive.");
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a number.");
                        scn.nextLine();
                    }
                }
                quantity.set(editIndex, newQuantity);
                scn.nextLine();
            } else if (editChoice == 3) {
                double newPrice = 0;
                while (true) {
                    System.out.print("Enter new price: ");
                    try {
                        newPrice = scn.nextDouble();
                        if (newPrice >= 0) {
                            break;
                        }
                        System.out.println("Price must be non-negative.");
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a number.");
                        scn.nextLine();
                    }
                }
                price.set(editIndex, newPrice);
                scn.nextLine();
            }
            System.out.println("Item updated successfully!");
            System.out.println("Edit another item? (y/n)");
            String anotherEdit = "";
            while (true) {
                try {
                    anotherEdit = scn.nextLine().toLowerCase();
                    if (anotherEdit.equals("y") || anotherEdit.equals("n")) {
                        break;
                    }
                    System.out.println("Please enter 'y' or 'n'.");
                } catch (Exception e) {
                    System.out.println("Input error.");
                }
            }
            if (!anotherEdit.equals("y")) {
                editing = false;
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<String> usernames = new ArrayList<String>();
        ArrayList<String> passwords = new ArrayList<String>();
        ArrayList<String> cart = new ArrayList<String>();
        ArrayList<Double> price = new ArrayList<Double>();
        ArrayList<Integer> quantity = new ArrayList<Integer>(); 
        while (true) {
          
            boolean isLoggingOut = false;
            boolean validated = authenticator(usernames, passwords);
            if(validated){ 
                while (true){
                    clearscreen();
                    isRunning = true;
                    while(isRunning){
                        System.out.println("Cash Register");
                        System.out.println("1. Add Item(s)");
                        System.out.println("2. Display Item(s)");
                        System.out.println("3. Remove Item(s)");
                        System.out.println("4. Proceed to payment");
                        System.out.println("5. Edit Item(s)");
                        System.out.println("6. Exit Program");
                        System.out.println("7. Log out");
                        int decision = 0;
                        while (true) {
                            try {
                                decision = scn.nextInt();
                                scn.nextLine();
                                if (decision >= 1 && decision <= 7) {
                                    break;
                                }
                                System.out.println("Please select a valid option!");
                            }
                            catch (InputMismatchException e) {
                                System.out.println("Invalid input! Please enter a number.");
                                scn.nextLine();
                            }
                        }

                        if (decision == 1) {
                            addItems(cart, price, quantity);
                        } else if (decision == 2) {
                            displayItems(cart, price, quantity);
                        } else if (decision == 3) {
                            removeItems(cart, price, quantity);
                        } else if (decision == 4) {
                            payment(cart, price, quantity);
                        } else if (decision == 5) {
                            editItems(cart, price, quantity);
                        } else if (decision == 6) {
                            System.exit(0);
                        } else if (decision == 7) {
                            isLoggingOut = true;
                            break;
                        }
                    }
                    if (isLoggingOut) {
                        break;
                    }
                    System.out.println("Make another transaction? (y/n)");
                    String anotherTransaction = "";
                    while (true) {
                        try {
                            anotherTransaction = scn.nextLine().toLowerCase();
                            if (anotherTransaction.equals("y") || anotherTransaction.equals("n")) {
                                break;
                            }
                            System.out.println("Please enter 'y' or 'n'.");
                        }
                        catch (Exception e) {
                            System.out.println("Input error.");
                        }
                    }
                    if (!anotherTransaction.equals("y")){
                        System.exit(0);
                    } else{
                        isRunning = true;
                        cart.clear();
                        quantity.clear();
                        price.clear();
                    }
                }
            }
        }
    }
}