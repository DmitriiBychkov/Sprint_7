package org.example.models;

public class CredentialsWithoutLogin {
    private String login;
    private String password;

    public CredentialsWithoutLogin(String login, String password) {
        this.login = "";
        this.password = password;
    }

    public static CredentialsWithoutLogin from(Courier courier) {
        return new CredentialsWithoutLogin(courier.getLogin(), courier.getPassword());
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
