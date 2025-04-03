CREATE DATABASE store;
USE store;

CREATE TABLE Product (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100),
    Price DOUBLE,
    Quantity INT
);
import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/store";
    static final String USER = "rohit";
    static final String PASSWORD = "Sql@13677";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            int choice;
            
            do {
                System.out.println("\n1. Insert Product\n2. View Products\n3. Update Product\n4. Delete Product\n5. Exit");
                System.out.print("Enter choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> insertProduct(conn, scanner);
                    case 2 -> viewProducts(conn);
                    case 3 -> updateProduct(conn, scanner);
                    case 4 -> deleteProduct(conn, scanner);
                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice.");
                }
            } while (choice != 5);
            
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter product name: ");
        String name = scanner.next();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
            System.out.println("Product inserted successfully.");
        }
    }

    public static void viewProducts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Product";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ProductID") + ", Name: " + rs.getString("ProductName") +
                                   ", Price: " + rs.getDouble("Price") + ", Quantity: " + rs.getInt("Quantity"));
            }
        }
    }

    public static void updateProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter ProductID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();

        String sql = "UPDATE Product SET Price = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, price);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Product updated successfully.");
        }
    }

    public static void deleteProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter ProductID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM Product WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Product deleted successfully.");
        }
    }
}
