/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author ghazallah
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final Pattern VALID_PASSWORD_REGEX = 
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",Pattern.CASE_INSENSITIVE);
    
    private static final Pattern VALID_PHONE_REGEX = 
            Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");
    
    public static final Pattern VALID_EMAIL_REGEX = 
            Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
    private static Matcher matcher;
    
    public static boolean validatePhone(String phone){
        matcher = VALID_PHONE_REGEX.matcher(phone);
        return matcher.find();
    }
    
    public static boolean validatePassword(String password){
        matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }
    
    public static boolean validateEmail(String email){
       matcher = VALID_EMAIL_REGEX.matcher(email);
        return matcher.find();
    }
}
