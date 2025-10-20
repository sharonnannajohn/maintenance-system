
public class Request {
    private int id;
    private String description;
    private String status;
    private User createdBy;
    private Worker assignedWorker;

    public Request(int id, String description, User createdBy) {
        this.id = id;
        this.description = description;
        this.status = "Pending";
        this.createdBy = createdBy;
        this.assignedWorker = null;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {  // ✅ Added setter
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Worker getAssignedWorker() {
        return assignedWorker;
    }

    public void setAssignedWorker(Worker worker) {
        this.assignedWorker = worker;
    }

    public String toString() {
        return "Request " + id + ": " + description +
               " | Status: " + status +
               " | Created by: " + createdBy.getName() +
               (assignedWorker != null ? " | Assigned to: " + assignedWorker.getName() : " | Not assigned");
    }
    public void saveToDB() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO request (id, description, status, created_by, assigned_worker) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setString(2, this.description);
            ps.setString(3, this.status);
            ps.setInt(4, this.createdBy.getId());
            if (this.assignedWorker != null)
                ps.setInt(5, this.assignedWorker.getId());
            else
                ps.setNull(5, java.sql.Types.INTEGER);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}