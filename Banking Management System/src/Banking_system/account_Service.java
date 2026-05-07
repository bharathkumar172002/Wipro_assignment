package Banking_system;
import java.sql.*;
import java.util.Scanner;

public class account_Service {
    public static void createAccount(Scanner sc) {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Customer ID: ");
            int customerId = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Account Type (Savings/Current): ");
            String type = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO accounts (customer_id, account_type, balance) VALUES (?, ?, ?)");
            ps.setInt(1, customerId);
            ps.setString(2, type);
            ps.setDouble(3, 0);

            ps.executeUpdate();
            System.out.println("✅ Account Created Successfully!");

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewAccounts() {
        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts");

            System.out.println("\n📋 Accounts List:");
            while (rs.next()) {
                System.out.println(rs.getInt("account_id") + " | " +
                                   rs.getInt("customer_id") + " | " +
                                   rs.getString("account_type") + " | " +
                                   rs.getDouble("balance"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
