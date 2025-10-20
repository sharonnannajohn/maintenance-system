import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User extends Profile {
    public User(int id, String name, String email, String password) {
        super(id, name, email, password, "User");
    }

    public Request createRequest(int requestId, String description) {
        return new Request(requestId, description, this); // ✅ pass this user
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