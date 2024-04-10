package office.project360;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:identifier.sqlite"; // Updated for server.sqlite

    // Method to insert user and potentially create a patient record
    public static long insertUser(String username, String password, String role) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        String sqlUser = "INSERT INTO users(username, password, role) VALUES(?,?,?)";
        // SQLite might not support getGeneratedKeys in the way you expect, so you might be doing this instead:
        String sqlLastId = "SELECT last_insert_rowid()"; // Correct for SQLite

        long userId = -1; // Default/failure value
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) { // This flag is generally ignored by SQLite JDBC

            pstmtUser.setString(1, username);
            pstmtUser.setString(2, hashed);
            pstmtUser.setString(3, role);
            int affectedRows = pstmtUser.executeUpdate();

            if (affectedRows > 0) {
                // Here's where you might encounter issues
                // Let's use a method compatible with SQLite
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sqlLastId)) { // Execute the query to get the last inserted ID
                    if (rs.next()) {
                        userId = rs.getLong(1); // Retrieve the last inserted rowid
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userId;
    }




    private static void insertPatient(Connection conn, long userId, String username) throws SQLException {
        String uniquePatientID = generateUniquePatientID(conn); // Modified to check uniqueness within the database
        String sqlPatient = "INSERT INTO patients(patient_id, user_id, username) VALUES(?,?,?)";

        try (PreparedStatement pstmtPatient = conn.prepareStatement(sqlPatient)) {
            pstmtPatient.setString(1, uniquePatientID);
            pstmtPatient.setLong(2, userId);
            pstmtPatient.setString(3, username);  // Include the username
            pstmtPatient.executeUpdate();
        }
    }

    private static String generateUniquePatientID(Connection conn) throws SQLException {
        // Generate a unique patient ID
        String patientId;
        do {
            patientId = String.format("%05d", (int) (Math.random() * 100000));
        } while (!isPatientIdUnique(conn, patientId));
        return patientId;
    }

    private static boolean isPatientIdUnique(Connection conn, String patientId) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) FROM patients WHERE patient_id = ?";
        try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
            pstmtCheck.setString(1, patientId);
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; // True if no existing record with the same patient_id
                }
            }
        }
        return false; // Default to false to be safe
    }
    public static boolean checkUserCredentials(String username, String password, String role) {
        String sql = "SELECT password FROM users WHERE username = ? AND role = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, role);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    return BCrypt.checkpw(password, storedHash);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    private static void insertDoctor(Connection conn, long userId, String username) throws SQLException {
        String sqlDoctor = "INSERT INTO doctors(id, username) VALUES(?,?)";

        try (PreparedStatement pstmtDoctor = conn.prepareStatement(sqlDoctor)) {
            pstmtDoctor.setLong(1, userId); // Set the user ID
            pstmtDoctor.setString(2, username);
            pstmtDoctor.executeUpdate();
        }
    }
    private static void insertNurse(Connection conn, long userId, String username) throws SQLException {
        String sqlNurse = "INSERT INTO Nurse(id, username) VALUES(?,?)";

        try (PreparedStatement pstmtNurse = conn.prepareStatement(sqlNurse)) {
            pstmtNurse.setLong(1, userId); // Set the user ID
            pstmtNurse.setString(2, username);
            pstmtNurse.executeUpdate();
        }
    }

    public static void updateNames(long userId, String role, String firstName, String lastName) {
        String sqlUpdate = "";

        // Determine the correct SQL statement based on the user's role
        if ("Patient".equals(role)) {
            sqlUpdate = "UPDATE patients SET FirstName = ?, LastName = ? WHERE user_id = ?";
        } else if ("Doctor".equals(role)) {
            sqlUpdate = "UPDATE doctors SET firstname = ?, lastname = ? WHERE id = ?";
        } else if ("Nurse".equals(role)) {
            sqlUpdate = "UPDATE Nurse SET firstname = ?, lastname = ? WHERE id = ?";
        }

        if (!sqlUpdate.isEmpty()) { // Ensure the SQL statement was set
            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
                // Set the parameters for the prepared statement
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setLong(3, userId);

                // Execute the update
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    // Handle the case where the update didn't affect any rows (e.g., user ID not found)
                    System.out.println("Update failed: User ID not found or role does not match.");
                }
            } catch (SQLException e) {
                System.out.println("Database error occurred during name update: " + e.getMessage());
            }
        } else {
            // Handle the case where the role was not recognized
            System.out.println("Update failed: Role not recognized.");
        }
    }


}
