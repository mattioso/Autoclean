package uk.co.appoly.autoclean;

public class LoginDatabase {

    static Boolean Login(String email, String password) {
        if (email.equals("matt@gmail.com") && password.equals("secret")) {
            return true;
        } else if (email.equals("m") && password.equals("s")) {
            return true;
        }
        return false;
    }

}
