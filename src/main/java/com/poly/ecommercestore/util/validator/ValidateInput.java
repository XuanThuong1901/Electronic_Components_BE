package com.poly.ecommercestore.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInput {
    public static boolean isStringValid(String input){
        String regex = "^[\\p{L} \\p{M}]+$";
        Pattern pattern = Pattern.compile(regex);
        // Tạo Matcher để so khớp chuỗi đầu vào với biểu thức chính quy
        Matcher matcher = pattern.matcher(input);
        // Kiểm tra xem chuỗi không chứa kí tự đặc biệt hoặc số
        return matcher.matches();
    }

    public static boolean isNumeric(String input){
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean isPhoneNumber(String input) {
        // Tạo biểu thức chính quy để kiểm tra chuỗi là số và có độ dài bằng 10
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean containsGmail(String input) {
        // Kiểm tra xem chuỗi có chứa "@gmail.com" hay không
        return input.contains("@gmail.com");
    }

    public static boolean isPasswordValid(String input) {
        // Kiểm tra xem độ dài của chuỗi có ít nhất 6 ký tự hay không
        return input.length() >= 6;
    }
}
