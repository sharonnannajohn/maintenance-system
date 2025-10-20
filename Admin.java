
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
}