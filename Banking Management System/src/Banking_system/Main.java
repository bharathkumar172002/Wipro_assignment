package Banking_system;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

       
        System.out.print("👤 Username: ");
        String username = sc.nextLine();
        System.out.print("🔒 Password: ");
        String password = sc.nextLine();

        if (!LoginService.login(username, password)) {
            System.out.println("❌ Login failed. Exiting...");
            return;
        }

       
        while (true) {
            System.out.println("\n🏦 Banking Management Menu");
            System.out.println("1. Register Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Create Account");
            System.out.println("4. View Accounts");
            System.out.println("5. Deposit");
            System.out.println("6. Withdraw");
            System.out.println("7. Transfer");
            System.out.println("8. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> CustomerService.registerCustomer(sc);
                case 2 -> CustomerService.viewCustomers();
                case 3 -> account_Service.createAccount(sc);
                case 4 -> account_Service.viewAccounts();
                case 5 -> TransactionService.deposit(sc);
                case 6 -> TransactionService.withdraw(sc);
                case 7 -> TransactionService.transfer(sc);
                case 8 -> {
                    System.out.println("🔚 thanks!");
                    return;
                }
                default -> System.out.println("❓ Invalid option");
            }
        }
    }
}
