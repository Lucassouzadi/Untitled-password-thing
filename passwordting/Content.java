package passwordting;

import java.io.Serializable;

public class Content implements Serializable {
    private String account;

    private String username;

    private String password;

    private boolean hasUser;

    public Content(String account, String password) {
        this.account = account;
        this.password = password;
        this.hasUser = false;
    }

    public Content(String account, String password, String username) {
        this.account = account;
        this.username = username;
        this.password = password;
        this.hasUser = true;
    }

    public boolean hasUser() {
        return this.hasUser;
    }

    public String getAccount() {
        return this.account;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String toString() {
        String output = "Conta: " + this.account + "\n";
        if (this.hasUser)
            output = output + "Usuário: " + this.username + "\n";
        return output + "Senha: " + this.password;
    }

    public void setAccount(String account) {
        this.account = account;
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
