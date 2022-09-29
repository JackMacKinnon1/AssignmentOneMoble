package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.util.Objects;



enum previousBtn {
    DIGIT, //0
    OPERATOR, //1
    EQUALS, //2
    CLEAR, //3
    DECIMAL,
    POSNEG,
    BACKSPACE
}





public class MainActivity extends AppCompatActivity {
    performCalculationsClass calculate = new performCalculationsClass();

    //variables for the number on screen
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private previousBtn previousBtnClk;

    //Creating controls
    Button clearButton, divideButton, backspaceButton, sevenButton,
        eightButton, nineButton, multiplyButton, fourButton, fiveButton,
        sixButton, subtractButton, oneButton, twoButton, threeButton,
        addButton, posNegButton, zeroButton, decimalButton, equalsButton;


    TextView resultEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //instance of controls
        clearButton = findViewById(R.id.clearButton);
        divideButton = findViewById(R.id.divideButton);
        backspaceButton = findViewById(R.id.backspaceButton);
        sevenButton = findViewById(R.id.sevenButton);
        eightButton = findViewById(R.id.eightButton);
        nineButton = findViewById(R.id.nineButton);
        multiplyButton = findViewById(R.id.multiplyButton);
        fourButton = findViewById(R.id.fourButton);
        fiveButton = findViewById(R.id.fiveButton);
        sixButton = findViewById(R.id.sixButton);
        subtractButton = findViewById(R.id.subtractButton);
        oneButton = findViewById(R.id.oneButton);
        twoButton = findViewById(R.id.twoButton);
        threeButton = findViewById(R.id.threeButton);
        addButton = findViewById(R.id.addButton);
        posNegButton = findViewById(R.id.posNegButton);
        zeroButton = findViewById(R.id.zeroButton);
        decimalButton = findViewById(R.id.decimalButton);
        equalsButton = findViewById(R.id.equalsButton);
        resultEditText = findViewById(R.id.resultEditText);

        //adding listeners
        zeroButton.setOnClickListener(onButtonClicked);
        oneButton.setOnClickListener(onButtonClicked);
        twoButton.setOnClickListener(onButtonClicked);
        threeButton.setOnClickListener(onButtonClicked);
        fourButton.setOnClickListener(onButtonClicked);
        fiveButton.setOnClickListener(onButtonClicked);
        sixButton.setOnClickListener(onButtonClicked);
        sevenButton.setOnClickListener(onButtonClicked);
        eightButton.setOnClickListener(onButtonClicked);
        nineButton.setOnClickListener(onButtonClicked);


        clearButton.setOnClickListener(clearButtonClicked);
        addButton.setOnClickListener(mathOpSelect);
        subtractButton.setOnClickListener(mathOpSelect);
        multiplyButton.setOnClickListener(mathOpSelect);
        divideButton.setOnClickListener(mathOpSelect);

        equalsButton.setOnClickListener(equalsClicked);
        posNegButton.setOnClickListener(flipPosNeg);
        decimalButton.setOnClickListener(placeDecimal);
        backspaceButton.setOnClickListener(backspaceListener);



    } //end onCreate

    //Creating listener for all number buttons to use
    private View.OnClickListener onButtonClicked = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            String digitClicked = "";

            if (previousBtnClk == previousBtn.OPERATOR || previousBtnClk == previousBtn.EQUALS) {updateScreen("0");} //Checks if previous btn is an operator OR a equals

            if (Objects.equals(getDisplayedNum(), "0")) {updateScreen(digitClicked);}

            switch (v.getId()) {

                case R.id.zeroButton:
                    digitClicked = "0";
                    break;
                case R.id.oneButton:
                    digitClicked = "1";
                    break;
                case R.id.twoButton:
                    digitClicked = "2";
                    break;
                case R.id.threeButton:
                    digitClicked = "3";
                    break;
                case R.id.fourButton:
                    digitClicked = "4";
                    break;
                case R.id.fiveButton:
                    digitClicked = "5";
                    break;
                case R.id.sixButton:
                    digitClicked = "6";
                    break;
                case R.id.sevenButton:
                    digitClicked = "7";
                    break;
                case R.id.eightButton:
                    digitClicked = "8";
                    break;
                case R.id.nineButton:
                    digitClicked = "9";
                    break;
                default:
                    break;
            }//end switch
            updateScreen(getDisplayedNum() + digitClicked);
            updateClick(previousBtn.DIGIT);
        }//end method onClick
    };//end inner class

    private View.OnClickListener clearButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            refreshVariables();
            updateScreen("0");
            updateClick(previousBtn.CLEAR);
        }
    };

    private View.OnClickListener mathOpSelect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (previousBtnClk == previousBtn.OPERATOR) {
                updateScreen(removeLast(getDisplayedNum(), " .$"));
            }
            else if (!Objects.equals(operator, "")) {
                performCalcAndUpdate();
            }
            switch (view.getId()) {
                case R.id.addButton:
                    operator = "+";
                    break;
                case R.id.subtractButton:
                    operator = "-";
                    break;
                case R.id.multiplyButton:
                    operator = "x";
                    break;
                case R.id.divideButton:
                    operator = "/";
                    break;
                default:
                    break;
            }
            firstNumber = Double.parseDouble(getDisplayedNum());
            updateScreen(String.format("%s %s", getDisplayedNum(), operator));
            updateClick(previousBtn.OPERATOR);
        }
    };


    private View.OnClickListener equalsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (Objects.equals(operator, "")) {
                return;
            }
            performCalcAndUpdate();
            refreshVariables();
            updateClick(previousBtn.EQUALS);
        }
    };

    private View.OnClickListener flipPosNeg = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (!getDisplayedNum().matches("-?[0-9]+[\\.]?[0-9]*")) return; //Checks if there is a char in the string that is not a negative or a digit


            if (!getDisplayedNum().matches(".*[1-9].*")) return;//if the display does contain at least one number between 1-9 it will return
            updateScreen(calculate.flipNum(getDisplayedNum()));
            updateClick(previousBtn.POSNEG);
        }
    };

    private View.OnClickListener placeDecimal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (!getDisplayedNum().contains(".")) {
                updateScreen(getDisplayedNum() + ".");
            }
            updateClick(previousBtn.DECIMAL);
        }
    };

    private View.OnClickListener backspaceListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Objects.equals(getDisplayedNum(), "0")) { //Returns because if the display is set to "0" we dont want zero to be removed
                return;
            }


            String regex;

            if (previousBtnClk == previousBtn.OPERATOR) {
                operator = "";
                regex = " .$"; //Will remove a space in the regex as well as the last char because an operator has an added space before the number
            }
            else {
                regex = ".$";
            }

            updateScreen(removeLast(getDisplayedNum(), regex));

            if (getDisplayedNum().length() == 0) updateScreen("0"); //Sets the screen display to zero if backspace is pressed with only one number in it
            updateClick(previousBtn.BACKSPACE);
        }
    };

    private void performCalcAndUpdate() {
        secondNumber = Double.parseDouble(getDisplayedNum());
        String displayNum = calculate.calculate(firstNumber, secondNumber, operator);
        updateScreen(displayNum);
    }

    private void updateScreen(String textForScreen) {
        resultEditText.setText(textForScreen);
    }

    private void refreshVariables() {
        operator = "";
        firstNumber = 0;
        secondNumber = 0;


    }

    private void updateClick(previousBtn btnClick) {
        previousBtnClk = btnClick;
    }
    private String getDisplayedNum() {
        return resultEditText.getText().toString();
    }
    private String removeLast(String inputStr, String regex) {
        return inputStr.replaceAll(regex, "");
    }
} //end main