package uk.co.appoly.autoclean;

public class Database {

    static Boolean Login(String email, String password) {
        if (email.equals("matt@gmail.com") && password.equals("secret")) {
            return true;
        }
        return false;
    }

}
