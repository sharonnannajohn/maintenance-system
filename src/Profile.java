
public class Profile {
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    protected String role;

    public Profile(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String toString() {
        return id + " - " + name + " (" + role + ")";
    }
}