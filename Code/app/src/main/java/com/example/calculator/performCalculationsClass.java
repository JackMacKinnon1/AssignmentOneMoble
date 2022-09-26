package com.example.calculator;

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
                returnCalc = num1 / num2;
                break;
        }

        if ((returnCalc % (int) returnCalc) != 0) {
            returnString = String.format("%.2f", returnCalc);
        }
        else {
            returnString = String.format("%d", (int)returnCalc);
        }

        return returnString;
    }



}
