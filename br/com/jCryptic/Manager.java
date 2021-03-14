package br.com.jCryptic;

import javax.swing.*;
import java.io.*;

public class Manager {
    private final char[] table = new char[]{
            ',', 'N', 'T', 'i', 'M', '.', 'j', 'C', '-', '@',
            'e', ':', '8', 'n', 'W', 'H', 'b', 'J', 'm', 'o',
            '3', '9', '7', 'B', 'h', '%', '2', 'f', 'w', 'y',
            '5', '#', 'E', 'a', '*', '!', 'Q', 'K', '0', 'S',
            'd', 'v', '&', 'U', 'Z', 'O', 's', 'A', 'R', 'G',
            'g', '1', ')', '(', 'I', 'r', 'p', 'V', 'u', 'q',
            'D', 'c', 'k', 'x', '6', 't', 'l', '$', '4', 'F',
            'Y', 'z', 'X', 'P', 'L'};

    public void test(String password, String key) {
        String trueKey = generateTrueKey(password, key);
        String encrypted = encrypt(password, key);
        String decrypted = decrypt(encrypted, key);
        System.out.println("Password: " + password);
        System.out.println("Key: " + key);
        System.out.println("TrueKey: " + trueKey);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        System.out.println(password.equals(decrypted));
    }

    private String generateTrueKey(String original, String key) {
        StringBuilder trueKey = new StringBuilder();
        int pos = (int) Math.pow(original.length(), original.length()) % key.length();
        int valor = value(key.charAt(pos));
        int i;
        for (i = 0; i < key.length(); i++) {
            valor *= value(key.charAt(i));
            valor += value(key.charAt(i));
        }
        if (valor < 0)
            valor *= -1;
        for (i = 0; i < original.length(); i++) {
            int numba = (i + 1) * valor * value(key.charAt(i % key.length()));
            numba %= this.table.length;
            if (numba < 0)
                numba *= -1;
            trueKey.append(this.table[numba]);
        }
        return trueKey.toString();
    }

    public String encrypt(String original, String key) {
        if ("".equals(key))
            throw new IllegalArgumentException("Chave vazia");
        StringBuilder encripted = new StringBuilder();
        key = generateTrueKey(original, key);
        for (int i = 0; i < original.length(); i++) {
            int pos = value(original.charAt(i)) - value(key.charAt(i)) + this.table.length;
            pos %= this.table.length;
            encripted.append(this.table[pos]);
        }
        return encripted.toString();
    }

    public String decrypt(String encryptedPassword, String key) {
        if ("".equals(key))
            return encryptedPassword;
        StringBuilder decrypted = new StringBuilder();
        key = generateTrueKey(encryptedPassword, key);
        for (int i = 0; i < encryptedPassword.length(); i++) {
            int pos = (value(encryptedPassword.charAt(i)) + value(key.charAt(i))) % this.table.length;
            decrypted.append(this.table[pos]);
        }
        return decrypted.toString();
    }

    public int value(char arg) {
        for (int i = 0; i < this.table.length; i++) {
            if (arg == this.table[i])
                return i;
        }
        throw new IllegalArgumentException("Invalid character \"" + arg + "\"");
    }

    public String hiddenInput(String title, String message) {
        String key = null;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        Object[] obj = {message, passwordField};
        Object[] stringArray = {"Submit", "Cancel"};
        if (0 == JOptionPane.showOptionDialog(null, obj, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj))
            key = new String(passwordField.getPassword());
        return key;
    }

    public void save(File f, Object o) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            try (ObjectOutputStream os = new ObjectOutputStream(fos)) {
                os.writeObject(o);
            }
        } catch (IOException e) {
            System.out.println("Erro ao gravar objeto.");
        }
    }

    public Account readAccount(File f) {
        Account o = null;
        try {
            FileInputStream fos = new FileInputStream(f);
            try (ObjectInputStream os = new ObjectInputStream(fos)) {
                o = (Account) os.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ce) {
            System.out.println("Objeto não encontrado.");
        }
        return o;
    }
}
