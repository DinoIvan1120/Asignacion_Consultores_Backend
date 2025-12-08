package com.IngSoftware.proyectosgr.util;

import java.util.regex.Pattern;

public class DataUtils {
    public static String emailRegexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static Boolean validateEmail(String email) throws Exception{
        return Pattern.compile(emailRegexPattern)
                .matcher(email).matches();
    }

}
