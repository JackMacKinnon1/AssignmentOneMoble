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
    BACKSPACE,
    STARTUP
}





public class MainActivity extends AppCompatActivity {
    performCalculationsClass calculate = new performCalculationsClass();

    //variables for the number on screen
    private String firstNumber = "0";
    private String secondNumber = "0";
    private String operator = "";
    private previousBtn previousBtnClk = previousBtn.STARTUP;

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


    private View.OnClickListener onButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String digitClicked = "";

            if (getDisplayedNum().equals("0") || previousBtnClk == previousBtn.OPERATOR) updateScreen(digitClicked);
            //If the display holds only zero, or the last button selected is a math operator display will clear to hold the number that was entered

            if (getDisplayedNum().matches("^-0")) {//Screen holds -0, so any number the user clicks will have a negative in front
                updateScreen(digitClicked);
                digitClicked += "-";
            }

            switch (v.getId()) {

                case R.id.zeroButton:
                    digitClicked += "0";
                    break;
                case R.id.oneButton:
                    digitClicked += "1";
                    break;
                case R.id.twoButton:
                    digitClicked += "2";
                    break;
                case R.id.threeButton:
                    digitClicked += "3";
                    break;
                case R.id.fourButton:
                    digitClicked += "4";
                    break;
                case R.id.fiveButton:
                    digitClicked += "5";
                    break;
                case R.id.sixButton:
                    digitClicked += "6";
                    break;
                case R.id.sevenButton:
                    digitClicked += "7";
                    break;
                case R.id.eightButton:
                    digitClicked += "8";
                    break;
                case R.id.nineButton:
                    digitClicked += "9";
                    break;
                default:
                    break;
            }//end switch

            String currentNumberOnScreen = getDisplayedNum();
            updateScreen(currentNumberOnScreen + digitClicked); //Appends clicked number to screen

            updateClick(previousBtn.DIGIT); //Sets the last button pressed as a digit
        }//End onClick method
    };//End inner class

    private View.OnClickListener mathOpSelect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (Objects.equals(firstNumber, "0")) firstNumber = getDisplayedNum();
            //If an operator is selected and the firstNumber is not set than the number on screen is saved as the first number

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
            }//end switch

            updateScreen(operator); //Clears the display and adds the operator to it
            updateClick(previousBtn.OPERATOR);
        }
    };

    private View.OnClickListener equalsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (Objects.equals(operator, "") || !isNum(getDisplayedNum())) {
                return;
            }
            performCalcAndUpdate();
            refreshVariables();
            updateClick(previousBtn.EQUALS);
        }//End onClick method
    };//End inner class

    private View.OnClickListener backspaceListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (getDisplayedNum().equals("0")) return; //Ends handler if display is already zero
            if (previousBtnClk == previousBtn.OPERATOR) return; //Ends handler if previous button clicked is an operator

            String updatedValue = getDisplayedNum().replaceAll(".$", "");
            if (updatedValue.matches("")) updatedValue = "0";
            updateScreen(updatedValue);
        }//End onClick method
    };//End inner class

    private View.OnClickListener placeDecimal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isNum(getDisplayedNum())) return; //If the value on screen is not a num it will return

            if (!getDisplayedNum().contains(".")) {
                updateScreen(getDisplayedNum() + ".");
            }
            updateClick(previousBtn.DECIMAL);
        }//End onClick method
    };//End inner class



    private View.OnClickListener flipPosNeg = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (!isNum(getDisplayedNum())) updateScreen("0"); //if screen is not a number, will clear screen and add -0 to it

            updateScreen(calculate.flipNum(getDisplayedNum()));
            updateClick(previousBtn.POSNEG);
        }//End onClick method
    };//End inner class

    private View.OnClickListener clearButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            refreshVariables();
            updateScreen("0");
            updateClick(previousBtn.CLEAR);
        }
    };







    private void performCalcAndUpdate() {
        secondNumber = getDisplayedNum();
        String displayNum = calculate.calculate(Double.parseDouble(firstNumber), Double.parseDouble(secondNumber), operator);
        updateScreen(displayNum);
    }//End perfomCalcAndUpdate method

    private void refreshVariables() {
        operator = "";
        firstNumber = "0";
        secondNumber = "0";
    }//End refreshVariables method

    private String getDisplayedNum() {return resultEditText.getText().toString();}

    private void updateScreen(String textForScreen) {resultEditText.setText(textForScreen);}

    private void updateClick(previousBtn btnClick) {previousBtnClk = btnClick;}

    private boolean isNum(String value) {return value.matches("-?[0-9]+[\\.]?[0-9]*");}
} //end main