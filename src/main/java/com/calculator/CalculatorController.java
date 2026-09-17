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
    private boolean hasError = false;

    @FXML
    private void handleNumber(ActionEvent event) {
        if (startNewNumber) {
            display.setText("");
            startNewNumber = false;
            hasError = false;
        }
        String value = ((Button) event.getSource()).getText();
        display.setText(display.getText() + value);
    }

    @FXML
    private void handleOperator(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        if (!hasError && !display.getText().isEmpty()) {
            double displayedNumber = Double.parseDouble(display.getText());

            if (!operator.isEmpty() && !startNewNumber) {
                Double result = calculate(displayedNumber);
                if (result == null) {
                    return;
                }
                number1 = result;
                display.setText(String.valueOf(result));
            } else {
                number1 = displayedNumber;
            }

            operator = value;
            startNewNumber = true;
        }
    }

    @FXML
    private void handleEquals(ActionEvent event) {
        if (!display.getText().isEmpty() && !operator.isEmpty()) {
            double number2 = Double.parseDouble(display.getText());
            Double result = calculate(number2);
            if (result == null) {
                return;
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
        hasError = false;
    }

    private Double calculate(double number2) {
        switch (operator) {
            case "+":
                return number1 + number2;
            case "-":
                return number1 - number2;
            case "*":
                return number1 * number2;
            case "/":
                if (number2 == 0) {
                    showDivisionByZeroError();
                    return null;
                }
                return number1 / number2;
            default:
                return null;
        }
    }

    private void showDivisionByZeroError() {
        display.setText("Error: división por cero");
        operator = "";
        number1 = 0;
        startNewNumber = true;
        hasError = true;
    }
}
