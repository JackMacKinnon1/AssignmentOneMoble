package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.util.Objects;



enum previousBtn {
    DIGIT,
    OPERATOR,
    EQUALS,
    CLEAR,
    DECIMAL,
    POSNEG,
    BACKSPACE
}





public class MainActivity extends AppCompatActivity {
    StringBuilder stringBuilder = new StringBuilder();
    performCalculationsClass calculate = new performCalculationsClass();

    //variables for the number on screen
    private String displayNum = "0";
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private String result = "";
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

            if (Objects.equals(displayNum, "0")) { //Clears display if a number is clicked and the display is currently 0
                displayNum = "";
            }

            switch (v.getId()) {
                case R.id.zeroButton:
                    displayNum += "0";
                    break;
                case R.id.oneButton:
                    displayNum += "1";
                    break;
                case R.id.twoButton:
                    displayNum += "2";
                    break;
                case R.id.threeButton:
                    displayNum += "3";
                    break;
                case R.id.fourButton:
                    displayNum += "4";
                    break;
                case R.id.fiveButton:
                    displayNum += "5";
                    break;
                case R.id.sixButton:
                    displayNum += "6";
                    break;
                case R.id.sevenButton:
                    displayNum += "7";
                    break;
                case R.id.eightButton:
                    displayNum += "8";
                    break;
                case R.id.nineButton:
                    displayNum += "9";
                    break;
                default:
                    stringBuilder.append("NaN");
                    break;
            }//end switch
            updateScreen(displayNum);
            updateClick(previousBtn.DIGIT);
        }//end method onClick
    };//end inner class

    private View.OnClickListener clearButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            refreshVariables();
            updateScreen("0");
            displayNum = resultEditText.getText().toString();
            updateClick(previousBtn.CLEAR);
        }
    };

    private View.OnClickListener mathOpSelect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (previousBtnClk == previousBtn.OPERATOR) {
                return;
            }
            if (!Objects.equals(operator, "")) {
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
            firstNumber = Double.parseDouble(displayNum);
            updateScreen(String.format("%s%s", displayNum, operator));
            displayNum = "0";
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

            if (!displayNum.matches(".*[1-9].*")) return;//if the display does contain at least one number between 1-9 it will return
            displayNum = calculate.flipNum(displayNum);
            updateScreen(displayNum);
            updateClick(previousBtn.POSNEG);
        }
    };

    private View.OnClickListener placeDecimal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (!displayNum.contains(".")) {
                displayNum += ".";
                updateScreen(displayNum);
            }
            updateClick(previousBtn.DECIMAL);
        }
    };

    private View.OnClickListener backspaceListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (previousBtnClk == previousBtn.OPERATOR) {
                operator = "";
                displayNum = resultEditText.getText().toString();
            }

            if (Objects.equals(displayNum, "0")) {
                return;
            }
            displayNum = displayNum.replaceAll(".$", "");
            if (displayNum.length() == 0) displayNum = "0";
            updateScreen(displayNum);
            updateClick(previousBtn.BACKSPACE);
        }
    };

    private void performCalcAndUpdate() {
        secondNumber = Double.parseDouble(displayNum);
        displayNum = calculate.calculate(firstNumber, secondNumber, operator);
        updateScreen(displayNum);
    }

    private void updateScreen(String textForScreen) {
        resultEditText.setText(textForScreen);
    }

    private void refreshVariables() {
        operator = "";
        displayNum = "0";
        firstNumber = 0;
        secondNumber = 0;


    }

    private void updateClick(previousBtn btnClick) {
        previousBtnClk = btnClick;
    }
} //end main