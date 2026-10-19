package GUI;

public class GetNameLogin {
    private static String loggedInUsername;
    private static String loggedInFullName;

    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static void setLoggedInFullName(String fullName) {
        loggedInFullName = fullName;
    }

    public static String getLoggedInFullName() {
        return loggedInFullName;
    }
    
    public static void clear() {
        loggedInFullName = null;
    }
}
