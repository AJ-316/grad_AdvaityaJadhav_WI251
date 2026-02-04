package db;

public record UserData(int uid, String name, String role) {
    @Override
    public String toString() {
        if (role.equals("ADMIN"))
            return "UserData{ ADMIN USER }";

        return "\u001b[33mUserData{\u001b[0m" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                "\u001b[33m}\u001b[0m";
    }
}
