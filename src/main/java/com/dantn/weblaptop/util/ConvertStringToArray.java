package com.dantn.weblaptop.util;

import com.dantn.weblaptop.dto.response.AnhSanPhamResponse;
import com.dantn.weblaptop.dto.response.SerialNumberResponse;
import com.dantn.weblaptop.entity.sanpham.AnhSanPham;
import com.dantn.weblaptop.entity.sanpham.SerialNumber;

import java.util.List;
import java.util.Set;

public class ConvertStringToArray {
    public static String[] toArray(String str) {
        if (str == null || str.isBlank())
            return null;
        String regex = "[,\\-\\.]";
        String[] array = str.split(regex);
        return trimArray(array);
    }

    public static String[] toArraySplitImageUrl(String str) {
        if (str == null || str.isBlank())
            return null;
        String[] array = str.split(",");
        return trimArray(array);
    }

    public static String[] trimArray(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
        }
        return arr;
    }

    public static String setSeriNumberToNameString(List<SerialNumberResponse> set){
        StringBuilder builder = new StringBuilder();

        for (SerialNumberResponse sr : set) {
            builder.append(sr.getMa()).append(", ");
        }
        if (builder.length() > 0)
            builder.setLength(builder.length() - 2);
        return builder.toString();
    }

    public static String setAnhSanPhamToNameString(List<AnhSanPhamResponse> set){
        if (set == null || set.size() == 0) return "";
        StringBuilder builder = new StringBuilder();

        for (AnhSanPhamResponse a : set) {
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
