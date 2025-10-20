import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Worker extends Profile {
    public Worker(int id, String name, String email, String password) {
        super(id, name, email, password, "Worker");
    }

    public void viewAssignedRequest(Request request) {
        if (request.getAssignedWorker() != null && request.getAssignedWorker().getId() == this.getId()) {
            System.out.println("Viewing request: " + request);
        } else {
            System.out.println("No request assigned or mismatch.");
        }
    }

    public void updateRequestStatus(Request request, String status) {
        if (request.getAssignedWorker() != null && request.getAssignedWorker().getId() == this.getId()) {
            request.setStatus(status);
            System.out.println("Request " + request.getId() + " updated to status: " + status);
        } else {
            System.out.println("You are not assigned to this request.");
        }
    }
    public void saveToDB() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO profile (id, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.getId());
            ps.setString(2, this.getName());
            ps.setString(3, this.email);
            ps.setString(4, this.password);
            ps.setString(5, this.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}