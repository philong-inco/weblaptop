package com.dantn.weblaptop.util;

import com.dantn.weblaptop.entity.sanpham.AnhSanPham;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;

import java.util.Set;

public class ConvertStringToArray {
    public static String[] toArray(String str) {
        if (str == null || str.isBlank())
            return null;
        String regex = "[,\\-\\.]";
        String[] array = str.split(regex);
        return trimArray(array);
    }

    public static String[] trimArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
        }
        return arr;
    }

    public static String setSeriNumberToNameString(Set<SerialNumber> set){
        StringBuilder builder = new StringBuilder();

        for (SerialNumber sr : set) {
            builder.append(sr.getMa()).append(", ");
        }
        if (builder.length() > 0)
            builder.setLength(builder.length() - 2);
        return builder.toString();
    }

    public static String setAnhSanPhamToNameString(Set<AnhSanPham> set){
        StringBuilder builder = new StringBuilder();

        for (AnhSanPham a : set) {
            builder.append(a.getUrl()).append(", ");
        }
        if (builder.length() > 0)
            builder.setLength(builder.length() - 2);
        return builder.toString();
    }


    public static void main(String[] args) {
        for (String str : toArray("1,2,3,4")) {
            System.out.println(str);
        }
    }
}
