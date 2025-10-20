import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

public class Admin extends Profile {
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, "Admin");
    }

    public void assignWorker(Request request, Worker worker) {
        request.setAssignedWorker(worker);
        System.out.println("Worker " + worker.getName() + " assigned to request " + request.getId());
    }

    public void filterRequestByStatus(List<Request> requests, String status) {
        System.out.println("Requests with status: " + status);
        for (Request r : requests) {
            if (r.getStatus().equalsIgnoreCase(status)) {
                System.out.println(r);
            }
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