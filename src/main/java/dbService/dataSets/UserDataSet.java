package dbService.dataSets;

public class UserDataSet {
    private final String login;
    private final String password;
    private final String email;

    public UserDataSet(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "User{" + "login='" + login + '\'' + ", password='" + password + '\'' + ", email='" + email + '\'' + '}';
    }
}
