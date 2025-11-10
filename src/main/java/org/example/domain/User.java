package org.example.domain;

public class User {
    private final String login;
    private final String password;
    private Wallet wallet;

    public User(String login, String password, Wallet wallet) {
        this.login = login;
        this.password = password;
        this.wallet = wallet;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + login + '\'' +
                ", balance=" + wallet.getBalance() +
                '}';
    }
}
