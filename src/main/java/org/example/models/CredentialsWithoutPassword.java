package org.example.models;

public class CredentialsWithoutPassword {
    private String login;
    private String password;

    public CredentialsWithoutPassword(String login, String password) {
        this.login = login;
        this.password = "";
    }

    public static CredentialsWithoutPassword from(Courier courier) {
        return new CredentialsWithoutPassword(courier.getLogin(), courier.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
