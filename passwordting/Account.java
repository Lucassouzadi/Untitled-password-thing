package passwordting;

import java.io.Serializable;

public class Account implements Serializable {
    private String name;

    private String username;

    private String password;

    private boolean hasUser;

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
        this.hasUser = false;
    }

    public Account(String name, String password, String username) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.hasUser = true;
    }

    public boolean hasUser() {
        return this.hasUser;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String toString() {
        String output = "Conta: " + this.name + "\n";
        if (this.hasUser)
            output = output + "Usuário: " + this.username + "\n";
        return output + "Senha: " + this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHasUser(boolean hasUser) {
        this.hasUser = hasUser;
    }
}
