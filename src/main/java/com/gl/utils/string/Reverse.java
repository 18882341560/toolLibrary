package com.gl.utils.string;

/**
 * 字符串 字符反转
 */
public class Reverse {


    public static void main(String[] args) {
       String a = "-123654";
        System.out.println(reverse(a));
    }


    public static String reverse(String str){
        return new StringBuilder(str).reverse().toString();
    }


}
