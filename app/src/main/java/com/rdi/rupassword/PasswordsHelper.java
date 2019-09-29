package com.rdi.rupassword;

public class PasswordsHelper {
    private final String[] russians;
    private final String[] latin;

    public PasswordsHelper(String[] russians, String[] latin) {
        this.russians = russians;
        this.latin = latin;
        if (russians.length != latin.length) {
            throw new IllegalArgumentException();
        }
    }

    public String convert(CharSequence sourse) {
        StringBuilder result= new StringBuilder();

        for (int i = 0; i < sourse.length(); i++) {
            char c = sourse.charAt(i);
            String s = String.valueOf(c);
            boolean found = false;
            for (int j = 0; j < russians.length; j++) {
                if (russians[j].equals(s)) {
                    result.append(latin[j]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                result.append(c);
            }
        }

        return String.valueOf(result);


    }

}
