package com.dantn.weblaptop.util;

public class ConvertStringToArray {
    public static String[] toArray(String str) {
        if (str.isBlank())
            return null;
        String regex = "[,\\-\\.]";
        String[] array = str.split(regex);
        return trimArray(array);
    }

    public static String[] trimArray(String[] arr) {
        for (String str : arr) {
            str = str.trim();
        }
        return arr;
    }

    public static void main(String[] args) {
        for (String str : toArray("1,2,3,4")) {
            System.out.println(str);
        }
    }
}
