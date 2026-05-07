package Banking_system;
import java.sql.*;
import java.util.Scanner;

public class CustomerService {
    public static void registerCustomer(Scanner sc) {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Phone: ");
            String phone = sc.nextLine();
            System.out.print("Enter Address: ");
            String address = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO customers (name, phone, address) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);

            ps.executeUpdate();
            System.out.println("✅ Customer Registered Successfully!");

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void viewCustomers() {
        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

            System.out.println("\n📋 Customer List:");
            while (rs.next()) {
                System.out.println(rs.getInt("customer_id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("phone") + " | " +
                                   rs.getString("address"));
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
