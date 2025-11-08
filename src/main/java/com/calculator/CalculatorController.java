package com.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class CalculatorController {
    @FXML
    private TextField display;

    private String currentNumber = "";
    private String operator = "";
    private double number1 = 0;
    private boolean startNewNumber = true;

    @FXML
    private void handleNumber(ActionEvent event) {
        if (startNewNumber) {
            display.setText("");
            startNewNumber = false;
        }
        String value = ((Button) event.getSource()).getText();
        display.setText(display.getText() + value);
    }

    @FXML
    private void handleOperator(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        if (!display.getText().isEmpty()) {
            number1 = Double.parseDouble(display.getText());
            operator = value;
            startNewNumber = true;
        }
    }

    @FXML
    private void handleEquals(ActionEvent event) {
        if (!display.getText().isEmpty() && !operator.isEmpty()) {
            double number2 = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "*":
                    result = number1 * number2;
                    break;
                case "/":
                    result = number1 / number2;
                    break;
            }

            display.setText(String.valueOf(result));
            operator = "";
            startNewNumber = true;
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        display.setText("");
        currentNumber = "";
        operator = "";
        number1 = 0;
        startNewNumber = true;
    }
}
