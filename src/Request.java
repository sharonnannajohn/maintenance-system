
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
}