CREATE DATABASE college;
USE college;

CREATE TABLE Student (
    StudentID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50),
    Department VARCHAR(50),
    Marks DOUBLE
);
public class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    // Constructor, Getters & Setters
    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    // Getters and setters omitted for brevity
}
import java.sql.*;

public class StudentController {
    private Connection conn;

    public StudentController() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "password");
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setDouble(3, student.getMarks());
            pstmt.executeUpdate();
        }
    }

    // Other CRUD methods (update, delete, fetch) follow the same pattern.
}
public class Main {
    public static void main(String[] args) {
        // Implement menu-driven user interaction here
    }
}
