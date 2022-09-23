package Models;

/** This class is an object constructor used to create User objects in the database and get/set their values.*/
public class User {
    private int userId;
    private String username;
    private String password;

    /** @param userId Int value of User ID
     * @param username String value of Username
     * @param password String value of Password */
    public User (int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /** Gets User ID parameter from database.
     * @return userId Integer value of Division ID */
    public int getUserId() {
        return userId;
    }

    /** Sets User ID parameter in database.
     * @param userId Integer value of User ID */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** Gets Username and Password parameter from database.
     * @return username String value of username */
    public String getUsername() {
        return username;
    }

    /** Sets Username parameter in database.
     * @param username String value of username */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Gets Password parameter from database.
     * @return password String value of password */
    public String getPassword() {
        return password;
    }

    /** Sets Password parameter in database.
     * @param password String value of password */
    public void setPassword(String password) {
        this.password = password;
    }
}
