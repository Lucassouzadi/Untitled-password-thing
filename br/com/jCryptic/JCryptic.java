package br.com.jCryptic;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

public class JCryptic {

    public static final String TITLE = "JCryptic v10.0";
    public static final int DECRYPT = 0;
    public static final int ENCRYPT = 1;

    public static void main(String[] args) {
        try {
            mainLoop();
        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuilder stackTrace = new StringBuilder(ex.toString());
            for (var stackTraceElement : ex.getStackTrace())
                stackTrace.append("\n").append(stackTraceElement);
            JOptionPane.showMessageDialog(null, stackTrace.toString(), TITLE + " - ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void mainLoop() {
        Manager manager = new Manager();
        File encryptedPasswordsDir = new File("encryptedPasswords");
        String[] options1 = new String[]{"Decrypt", "Encrypt"};
        boolean continuar = true;
        while (continuar) {
            String accountName, passwordList[], user, option, password, input;
            switch (JOptionPane.showOptionDialog(null, "Escolha uma opção", TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1, "Decrypt")) {
                case ENCRYPT:
                    accountName = JOptionPane.showInputDialog(null, "Conta", TITLE, JOptionPane.QUESTION_MESSAGE);
                    if (accountName == null)
                        continue;
                    accountName = accountName.trim();
                    if ("".equals(accountName)) {
                        JOptionPane.showMessageDialog(null, "Inválido", TITLE, JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    user = manager.hiddenInput(TITLE, "Usuário:\n(se não houver usuário, deixe em branco e prossiga)");
                    if (user == null)
                        continue;
                    if (!"".equals(user) && !manager.hiddenInput(TITLE, "Digite novamente o Usuário").equals(user)) {
                        JOptionPane.showMessageDialog(null, "Inválido", TITLE, JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    password = manager.hiddenInput(TITLE, "Senha:");
                    if (password == null)
                        continue;
                    if (password.equals("")) {
                        JOptionPane.showMessageDialog(null, "Inválido", TITLE, JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    if (!manager.hiddenInput(TITLE, "Digite novamente a Senha:").equals(password)) {
                        JOptionPane.showMessageDialog(null, "Inválido", TITLE, JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    input = manager.hiddenInput(TITLE, "Chave:");
                    if (input == null)
                        continue;
                    if ("".equals(input)) {
                        JOptionPane.showMessageDialog(null, "Inválido", TITLE, JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    if (!manager.hiddenInput(TITLE, "Digite novamente a Chave:").equals(input)) {
                        JOptionPane.showMessageDialog(null, "Inválido", TITLE, JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    Account accountToEncrypt;
                    if ("".equals(user)) {
                        accountToEncrypt = new Account(accountName, manager.encrypt(password, input));
                    } else {
                        accountToEncrypt = new Account(accountName, manager.encrypt(password, input), manager.encrypt(user, input));
                    }
                    if (!encryptedPasswordsDir.exists())
                        encryptedPasswordsDir.mkdir();
                    manager.save(new File(encryptedPasswordsDir + "\\" + accountName), accountToEncrypt);
                    JOptionPane.showMessageDialog(null, "Senha salva com Sucesso!", TITLE, JOptionPane.INFORMATION_MESSAGE);
                    break;
                case DECRYPT:
                    if (!encryptedPasswordsDir.exists())
                        encryptedPasswordsDir.mkdir();
                    passwordList = encryptedPasswordsDir.list();
                    if (Objects.nonNull(passwordList) && passwordList.length == 0) {
                        JOptionPane.showMessageDialog(null, "Pasta de senhas vazia!", TITLE, JOptionPane.WARNING_MESSAGE);
                        continue;
                    }
                    option = (String) JOptionPane.showInputDialog(null, "Selecione a conta", TITLE, JOptionPane.PLAIN_MESSAGE, null, passwordList, null);
                    if (option == null)
                        continue;
                    File accountFileToDecrypt = new File(encryptedPasswordsDir + "\\" + option);
                    input = manager.hiddenInput(TITLE, "Chave:");
                    if (input == null)
                        continue;
                    Account accountToDecrypt = manager.readAccount(accountFileToDecrypt);
                    accountToDecrypt.setPassword(manager.decrypt(accountToDecrypt.getPassword(), input));
                    if (accountToDecrypt.hasUser())
                        accountToDecrypt.setUsername(manager.decrypt(accountToDecrypt.getUsername(), input));
                    JOptionPane.showMessageDialog(null, accountToDecrypt, TITLE, JOptionPane.INFORMATION_MESSAGE, null);
                    break;
                case -1:
                    continuar = false;
                    break;
            }
        }
    }
}
