
public class User extends Profile {
    public User(int id, String name, String email, String password) {
        super(id, name, email, password, "User");
    }

    public Request createRequest(int requestId, String description) {
        return new Request(requestId, description, this); // ✅ pass this user
    }
}