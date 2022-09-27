package com.example.calculator;

import java.util.Objects;

public class performCalculationsClass {
    public String calculate(double num1, double num2, String operator) {
        double returnCalc = 0;
        String returnString;
        switch (operator) {
            case "+":
                returnCalc = num1 + num2;
                break;
            case "-":
                returnCalc = num1 - num2;
                break;
            case "x":
                returnCalc = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    return "NaN";
                }
                returnCalc = num1 / num2;
                break;
        }

        returnString = String.format("%.2f", returnCalc);
        returnString = returnString.replaceAll(".00$", "");
        return returnString;
    }

    public String flipNum(String num) {
        double returnNum = -1 * (Double.parseDouble(num));
        String returnValue = "";
        returnValue = String.valueOf(returnNum);
        returnValue = returnValue.replaceAll(".0$", "");

        return returnValue;

    }



}
