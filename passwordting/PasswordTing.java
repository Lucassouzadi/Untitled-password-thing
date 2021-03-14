package passwordting;

import javax.swing.*;
import java.io.File;

public class PasswordTing {
    public static void main(String[] args) {
        try {
            bora();
        } catch (Exception ex) {
            ex.printStackTrace();
            StackTraceElement[] sT = ex.getStackTrace();
            String stackTrace = ex.toString();
            for (StackTraceElement sT1 : sT)
                stackTrace = stackTrace + "\n" + sT1;
            JOptionPane.showMessageDialog(null, stackTrace, "Deu Ruim", 0);
        }
    }

    private static void bora() {
        Menager alan = new Menager();
        String titulo = "Super Encriptador Loko de Senhas v8.004";
        File encryptedPasswordsDir = new File("encryptedPasswords");
        Object[] options1 = {"Decrypt", "Encrypt"};
        boolean continuar;
        for (continuar = true; continuar; ) {
            String account, passwordList[], user, option, password;
            File contentFile;
            String key;
            Content content, conteudo;
            int option1 = JOptionPane.showOptionDialog(null, "Escolha uma opção", titulo, -1, 3, null, options1, "Decrypt");
            switch (option1) {
                case 1:
                    account = JOptionPane.showInputDialog(null, "Conta", titulo, 3);
                    if (account == null)
                        continue;
                    account = account.trim();
                    if (account.equals("")) {
                        JOptionPane.showMessageDialog(null, "Inválido", titulo, 2);
                        continue;
                    }
                    user = alan.hiddenInput(titulo, "Usu\n(se não houver usuário, deixe em branco e prossiga)");
                    if (user == null)
                        continue;
                    if (!"".equals(user) && !alan.hiddenInput(titulo, "Digite novamente o Usuário").equals(user)) {
                        JOptionPane.showMessageDialog(null, "Inválido", titulo, 2);
                        continue;
                    }
                    password = alan.hiddenInput(titulo, "Senha:");
                    if (password == null)
                        continue;
                    if (password.equals("")) {
                        JOptionPane.showMessageDialog(null, "Inválido", titulo, 2);
                        continue;
                    }
                    if (!alan.hiddenInput(titulo, "Digite novamente a Senha:").equals(password)) {
                        JOptionPane.showMessageDialog(null, "Inválido", titulo, 2);
                        continue;
                    }
                    key = alan.hiddenInput(titulo, "Chave:");
                    if (key == null)
                        continue;
                    if (key.equals("")) {
                        JOptionPane.showMessageDialog(null, "Inválido", titulo, 2);
                        continue;
                    }
                    if (!alan.hiddenInput(titulo, "Digite novamente a Chave:").equals(key)) {
                        JOptionPane.showMessageDialog(null, "Inválido", titulo, 2);
                        continue;
                    }
                    if ("".equals(user)) {
                        content = new Content(account, alan.encrypt(password, key));
                    } else {
                        content = new Content(account, alan.encrypt(password, key), alan.encrypt(user, key));
                    }
                    if (!encryptedPasswordsDir.exists())
                        encryptedPasswordsDir.mkdir();
                    alan.save(new File(encryptedPasswordsDir + "\\" + account), content);
                    JOptionPane.showMessageDialog(null, "Senha salva com Sucesso!", titulo, 1);
                case 0:
                    if (!encryptedPasswordsDir.exists())
                        encryptedPasswordsDir.mkdir();
                    passwordList = encryptedPasswordsDir.list();
                    if (passwordList.length == 0) {
                        JOptionPane.showMessageDialog(null, "Pasta de senhas vazia!", titulo, 2);
                        continue;
                    }
                    option = (String) JOptionPane.showInputDialog(null, "Selecione a conta", titulo, -1, null, (Object[]) passwordList, null);
                    if (option == null)
                        continue;
                    contentFile = new File(encryptedPasswordsDir + "\\" + option);
                    key = alan.hiddenInput(titulo, "Chave:");
                    if (key == null)
                        continue;
                    conteudo = (Content) alan.read(contentFile);
                    conteudo.setPassword(alan.decrypt(conteudo.getPassword(), key));
                    if (conteudo.hasUser())
                        conteudo.setUsername(alan.decrypt(conteudo.getUsername(), key));
                    JOptionPane.showMessageDialog(null, conteudo);
                case -1:
                    continuar = false;
            }
        }
    }
}
