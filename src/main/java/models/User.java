package models;

import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private static final int MAX_EMAIL_LENGTH = 20;
    private static final int MAX_PASSWORD_LENGTH = 25;
    private static final int MAX_NAME_LENGTH = 30;

    public String email;
    public String password;

    public String name;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }


    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String password, String name) {
        this.email = null;
        this.password = password;
        this.name = name;
    }

    public static User getRandomUser() {
        return new User(
                getRandomEmail(),
                getRandomPassword(),
                getRandomName()
        );
    }

    public static String getRandomPassword(){
        return getRandomString(MAX_PASSWORD_LENGTH);
    }

    public static String getRandomName(){
        return getRandomString(MAX_NAME_LENGTH);
    }

    public static String getRandomEmail(){
        return getRandomString(MAX_EMAIL_LENGTH/2) + "@" + getRandomString(MAX_EMAIL_LENGTH/2) + "." + getRandomString(2);
    }

    private static String getRandomString(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[email: " + getEmail() + "; password: " + getPassword() + "; name: " + getName() + "]";
    }

    public String toJson() {
        return new GsonBuilder().serializeNulls().create().toJson(this);
    }
}