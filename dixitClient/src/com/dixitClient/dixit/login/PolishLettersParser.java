package com.dixitClient.dixit.login;

public class PolishLettersParser {
    public static String parse(String stringToParse){
        char[] chars = stringToParse.toCharArray();
        for (int i=0; i<chars.length; i++) {
            if(chars[i]==321){
                chars[i]=(char)76;
            } else if(chars[i]==322){
                chars[i]=(char)108;
            } else if(chars[i]==263){
                chars[i]=(char)99;
            } else if(chars[i]==262){
                chars[i]=(char)67;
            } else if(chars[i]==243){
                chars[i]=(char)111;
            } else if(chars[i]==347){
                chars[i]=(char)115;
            } else if(chars[i]==211){
                chars[i]=(char)79;
            } else if(chars[i]==260){
                chars[i]=(char)65;
            } else if(chars[i]==261){
                chars[i]=(char)97;
            } else if(chars[i]==280){
                chars[i]=(char)69;
            } else if(chars[i]==281){
                chars[i]=(char)101;
            } else if(chars[i]==346){
                chars[i]=(char)83;
            } else if(chars[i]==379 || chars[i]==377){
                chars[i]=(char)90;
            } else if(chars[i]==378 || chars[i]==380){
                chars[i]=(char)122;
            }
        }
        String stringResult= "";
        for (char ch:chars) {
            stringResult += ch;
        }
        return stringResult;
    }
}
