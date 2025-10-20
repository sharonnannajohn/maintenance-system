
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Admin> admins = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Worker> workers = new ArrayList<>();
        List<Request> requests = new ArrayList<>();

        int adminId = 1, userId = 1, workerId = 1, requestId = 1;

        while (true) {
            System.out.println("\n===== Maintenance Request System =====");
            System.out.println("1. Register Admin");
            System.out.println("2. Register User");
            System.out.println("3. Register Worker");
            System.out.println("4. User: Create Request");
            System.out.println("5. Admin: Assign Worker");
            System.out.println("6. Worker: Update Request Status");
            System.out.println("7. Admin: Filter Requests by Status");
            System.out.println("8. Show All Requests");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
            case 1: // Register Admin
                System.out.print("Enter name: ");
                String an = sc.nextLine();
                System.out.print("Enter email: ");
                String ae = sc.nextLine();
                System.out.print("Enter password: ");
                String ap = sc.nextLine();

                Admin admin = new Admin(adminId++, an, ae, ap);
                admin.saveToDB();      // save in MySQL database
                admins.add(admin);     // keep in memory for current session
                System.out.println("Admin registered and saved to database!");
                break;


            case 2://Register User
                System.out.print("Enter name: ");
                String un = sc.nextLine();
                System.out.print("Enter email: ");
                String ue = sc.nextLine();
                System.out.print("Enter password: ");
                String up = sc.nextLine();

                User user = new User(userId++, un, ue, up);
                user.saveToDB();      // save in MySQL database
                users.add(user);      // keep in memory for current session
                System.out.println("User registered and saved to database!");
                break;



                case 3: // Register Worker
                    System.out.print("Enter name: ");
                    String wn = sc.nextLine();
                    System.out.print("Enter email: ");
                    String we = sc.nextLine();
                    System.out.print("Enter password: ");
                    String wp = sc.nextLine();

                    Worker worker = new Worker(workerId++, wn, we, wp);
                    worker.saveToDB();    // save in MySQL database
                    workers.add(worker);  // keep in memory for session
                    System.out.println("Worker registered and saved to database!");
                    break;


                case 4: // User create request
                    if (users.isEmpty()) {
                        System.out.println("No users registered.");
                        break;
                    }

                    System.out.println("Select User:");
                    for (User u : users)
                        System.out.println(u);

                    int uid = sc.nextInt();
                    sc.nextLine();

                    User selectedUser = null;
                    for (User u : users) {
                        if (u.getId() == uid) {
                            selectedUser = u;
                            break;
                        }
                    }

                    if (selectedUser == null) {
                        System.out.println("Invalid user.");
                        break;
                    }

                    System.out.print("Enter request description: ");
                    String desc = sc.nextLine();

                    Request r = selectedUser.createRequest(requestId++, desc);
                    r.saveToDB();        // save in MySQL database
                    requests.add(r);     // keep in memory
                    System.out.println("Request created and saved to database!");
                    break;


                   

                case 5:
                    if (admins.isEmpty() || requests.isEmpty() || workers.isEmpty()) {
                        System.out.println("Need at least 1 admin, worker, and request.");
                        break;
                    }

                    System.out.println("Select Admin:");
                    for (Admin a : admins) System.out.println(a);
                    int aid = sc.nextInt(); sc.nextLine();

                    Admin selectedAdmin = null;
                    for (Admin a : admins) {
                        if (a.getId() == aid) { selectedAdmin = a; break; }
                    }
                    if (selectedAdmin == null) { System.out.println("Invalid admin."); break; }

                    System.out.println("Select Request:");
                    for (Request rq : requests) System.out.println(rq);
                    int rid = sc.nextInt(); sc.nextLine();

                    Request selectedReq = null;
                    for (Request req : requests) {
                        if (req.getId() == rid) { selectedReq = req; break; }
                    }
                    if (selectedReq == null) { System.out.println("Invalid request."); break; }

                    System.out.println("Select Worker:");
                    for (Worker w : workers) System.out.println(w);
                    int wid = sc.nextInt(); sc.nextLine();

                    Worker selectedWorker = null;
                    for (Worker w : workers) {
                        if (w.getId() == wid) { selectedWorker = w; break; }
                    }
                    if (selectedWorker == null) { System.out.println("Invalid worker."); break; }

                    selectedAdmin.assignWorker(selectedReq, selectedWorker);

                    // Update database
                    try (Connection conn = DBConnection.getConnection()) {
                        String sql = "UPDATE request SET assigned_worker=? WHERE id=?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setInt(1, selectedWorker.getId());
                        ps.setInt(2, selectedReq.getId());
                        ps.executeUpdate();
                    } catch (SQLException e) { e.printStackTrace(); }

                    break;

                case 6:
                    if (workers.isEmpty() || requests.isEmpty()) {
                        System.out.println("No workers or requests.");
                        break;
                    }

                    System.out.println("Select Worker:");
                    for (Worker w : workers) System.out.println(w);
                    int wid2 = sc.nextInt(); sc.nextLine();

                    Worker selectedWorker2 = null;
                    for (Worker w : workers) {
                        if (w.getId() == wid2) { selectedWorker2 = w; break; }
                    }
                    if (selectedWorker2 == null) { System.out.println("Invalid worker."); break; }

                    System.out.println("Select Request to update:");
                    for (Request rq : requests) {
                        if (rq.getAssignedWorker() != null && rq.getAssignedWorker().getId() == selectedWorker2.getId())
                            System.out.println(rq);
                    }
                    int rid2 = sc.nextInt(); sc.nextLine();

                    Request selectedReq2 = null;
                    for (Request req : requests) {
                        if (req.getId() == rid2) { selectedReq2 = req; break; }
                    }
                    if (selectedReq2 == null) { System.out.println("Invalid request."); break; }

                    System.out.print("Enter new status: ");
                    String newStatus = sc.nextLine();

                    selectedWorker2.updateRequestStatus(selectedReq2, newStatus);

                    // Update database
                    try (Connection conn = DBConnection.getConnection()) {
                        String sql = "UPDATE request SET status=? WHERE id=?";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, newStatus);
                        ps.setInt(2, selectedReq2.getId());
                        ps.executeUpdate();
                    } catch (SQLException e) { e.printStackTrace(); }

                    break;


                case 7: // Admin filters requests
                    if (admins.isEmpty() || requests.isEmpty()) {
                        System.out.println("No admins or requests.");
                        break;
                    }
                    System.out.println("Select Admin:");
                    for (Admin a : admins) System.out.println(a);
                    int aid2 = sc.nextInt(); sc.nextLine();

                    Admin selectedAdmin2 = null;
                    for (Admin a : admins) {
                        if (a.getId() == aid2) {
                            selectedAdmin2 = a;
                            break;
                        }
                    }
                    if (selectedAdmin2 == null) {
                        System.out.println("Invalid admin.");
                        break;
                    }

                    System.out.print("Enter status to filter: ");
                    String fs = sc.nextLine();
                    selectedAdmin2.filterRequestByStatus(requests, fs);
                    break;

                case 8: // Show all requests
                    if (requests.isEmpty()) {
                        System.out.println("No requests.");
                    } else {
                        for (Request rq : requests) System.out.println(rq);
                    }
                    break;

                case 0: // Exit
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}