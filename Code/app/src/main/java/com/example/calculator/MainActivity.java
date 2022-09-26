package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.view.*;
import android.widget.*;


public class MainActivity extends AppCompatActivity {
    StringBuilder stringBuilder = new StringBuilder();
    performCalculationsClass calculate = new performCalculationsClass();

    //variables for the number on screen
    private double currentNumOnScreen;
    private double firstNumber;
    private double secondNumber;
    private String operator = "";
    private String result = "";

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



        currentNumOnScreen = 0;
        firstNumber = 0;
        secondNumber = 0;

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


    } //end onCreate

    //Creating listener for all number buttons to use
    private View.OnClickListener onButtonClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (currentNumOnScreen == 0) {
                stringBuilder.setLength(0);
            }
            switch (v.getId()) {
                case R.id.zeroButton:
                    stringBuilder.append(0);
                    break;
                case R.id.oneButton:
                    stringBuilder.append(1);
                    break;
                case R.id.twoButton:
                    stringBuilder.append(2);
                    break;
                case R.id.threeButton:
                    stringBuilder.append(3);
                    break;
                case R.id.fourButton:
                    stringBuilder.append(4);
                    break;
                case R.id.fiveButton:
                    stringBuilder.append(5);
                    break;
                case R.id.sixButton:
                    stringBuilder.append(6);
                    break;
                case R.id.sevenButton:
                    stringBuilder.append(7);
                    break;
                case R.id.eightButton:
                    stringBuilder.append(8);
                    break;
                case R.id.nineButton:
                    stringBuilder.append(9);
                    break;
                default:
                    stringBuilder.append("NaN");
                    break;
            }//end switch
            updateScreen(stringBuilder.toString(), true);
        }//end method onClick
    };//end inner class

    private View.OnClickListener clearButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            refreshVariables();
            updateScreen(stringBuilder.toString(), false);
        }
    };

    private View.OnClickListener mathOpSelect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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

            firstNumber = currentNumOnScreen;
            currentNumOnScreen = 0;
            stringBuilder.setLength(0);


        }
    };


    private View.OnClickListener equalsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (operator == "") {
                return;
            }
            secondNumber = currentNumOnScreen;
            result = calculate.calculate(firstNumber, secondNumber, operator);
            updateScreen(result, true);
            refreshVariables();
        }
    };

    private View.OnClickListener flipPosNeg = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (stringBuilder.charAt(0) == '-') {
                stringBuilder.deleteCharAt(0);
            }
            else {
                stringBuilder.insert(0, "-");
            }
            updateScreen(stringBuilder.toString(), true);
        }
    };

    private View.OnClickListener placeDecimal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stringBuilder.append(".");
            updateScreen(stringBuilder.toString(), true);
        }
    };

    private void updateScreen(String textForScreen, boolean updateDouble) {
        if (updateDouble) {
            currentNumOnScreen = Double.parseDouble(textForScreen);
        }
        resultEditText.setText(textForScreen);
    }

    private void refreshVariables() {
        operator = "";
        result = "";
        currentNumOnScreen = 0;
        firstNumber = 0;
        secondNumber = 0;
        stringBuilder.setLength(0);
        stringBuilder.append(0);

    }
} //end main