package com.example.calculator;

import java.util.Objects;

public class performCalculationsClass {
    public String calculate(double num1, double num2, String operator) {
        double returnCalc = 0;
        String returnString;
        switch (operator) { //Determine which operator was provided
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
                    return "NaN"; //Returns NaN because it was a division by 0
                }
                returnCalc = num1 / num2;
                break;
        }

        returnString = String.format("%.2f", returnCalc);
        returnString = returnString.replaceAll(".00$", ""); //Replaces .00 with empty string because the zeros are redundant
        return returnString;
    }

    public String flipNum(String num) {
        String returnValue = "";
        if (num.startsWith("-")) { //Checks if the first value in the string is a negative sin
            returnValue = num.replaceFirst("^-", ""); //Replaces the negative with empty string
        }
        else {
            returnValue = "-" + num; //Adds a negative to the front of the number given
        }

        return returnValue;

    }



}
