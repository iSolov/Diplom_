package models;

public class AuthInfo {
    public Boolean success;
    public User user;
    public String accessToken;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String refreshToken;

    public String getAccessToken(){
        return accessToken.replace("Bearer ", "");
    }
}
