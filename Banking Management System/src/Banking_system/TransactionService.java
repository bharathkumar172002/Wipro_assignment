package Banking_system;
import java.sql.*;
import java.util.Scanner;

public class TransactionService {
    public static void deposit(Scanner sc) {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Account ID: ");
            int accId = sc.nextInt();
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();
            

            PreparedStatement ps = con.prepareStatement(
            		
                "UPDATE accounts SET balance = balance + ? WHERE account_id=?");
            ps.setDouble(1, amount);
            ps.setInt(2, accId);
            ps.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO transactions (account_id, transaction_type, amount) VALUES (?, 'Deposit', ?)");
            ps2.setInt(1, accId);
            ps2.setDouble(2, amount);
            ps2.executeUpdate();

            System.out.println("✅ Deposit Successful!");

            ps.close();
            ps2.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void withdraw(Scanner sc) {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Account ID: ");
            int accId = sc.nextInt();
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE accounts SET balance = balance - ? WHERE account_id=? AND balance >= ?");
            ps.setDouble(1, amount);
            ps.setInt(2, accId);
            ps.setDouble(3, amount);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                PreparedStatement ps2 = con.prepareStatement(
                    "INSERT INTO transactions (account_id, transaction_type, amount) VALUES (?, 'Withdraw', ?)");
                ps2.setInt(1, accId);
                ps2.setDouble(2, amount);
                ps2.executeUpdate();
                ps2.close();
                System.out.println("✅ Withdraw Successful!");
            } else {
                System.out.println("❌ Insufficient Balance!");
            }

            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transfer(Scanner sc) {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter From Account ID: ");
            int fromAcc = sc.nextInt();
            System.out.print("Enter To Account ID: ");
            int toAcc = sc.nextInt();
            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();

            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(
                "UPDATE accounts SET balance = balance - ? WHERE account_id=? AND balance >= ?");
            ps1.setDouble(1, amount);
            ps1.setInt(2, fromAcc);
            ps1.setDouble(3, amount);
            int rows = ps1.executeUpdate();

            if (rows > 0) {
                PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE accounts SET balance = balance + ? WHERE account_id=?");
                ps2.setDouble(1, amount);
                ps2.setInt(2, toAcc);
                ps2.executeUpdate();

                PreparedStatement ps3 = con.prepareStatement(
                    "INSERT INTO transactions (account_id, transaction_type, amount) VALUES (?, 'Transfer', ?)");
                ps3.setInt(1, fromAcc);
                ps3.setDouble(2, amount);
                ps3.executeUpdate();

                PreparedStatement ps4 = con.prepareStatement(
                    "INSERT INTO transactions (account_id, transaction_type, amount) VALUES (?, 'Transfer', ?)");
                ps4.setInt(1, toAcc);
                ps4.setDouble(2, amount);
                ps4.executeUpdate();

                con.commit();
                System.out.println("✅ Transfer Successful!");

                ps2.close();
                ps3.close();
                ps4.close();
            } else {
                System.out.println("❌ Insufficient Balance!");
                con.rollback();
            }

            ps1.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
