package office.project360;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:identifier.sqlite"; // Updated for server.sqlite

    // Method to insert user and potentially create a patient record
    public static void insertUser(String username, String password, String role) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        String sqlUser = "INSERT INTO users(username, password, role) VALUES(?,?,?)";
        String sqlLastId = "SELECT last_insert_rowid()";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);
             Statement stmt = conn.createStatement()) {

            pstmtUser.setString(1, username);
            pstmtUser.setString(2, hashed); // Use hashed password
            pstmtUser.setString(3, role);
            int affectedRows = pstmtUser.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.executeQuery(sqlLastId)) {
                    if (rs.next()) {
                        long Id = rs.getLong(1); // Get the user ID of the newly inserted user
                        // Insert additional records based on the role
                        if ("Patient".equals(role)) {
                            insertPatient(conn, Id, username);
                        } else if ("Doctor".equals(role)) {
                            insertDoctor(conn, Id, username); // Pass Id here
                        } else if ("Nurse".equals(role)) {
                            insertNurse(conn, Id, username); // Pass Id here
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        String sqlNurse = "INSERT INTO nurses(id, username) VALUES(?,?)";

        try (PreparedStatement pstmtNurse = conn.prepareStatement(sqlNurse)) {
            pstmtNurse.setLong(1, userId); // Set the user ID
            pstmtNurse.setString(2, username);
            pstmtNurse.executeUpdate();
        }
    }



}
