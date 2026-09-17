package com.calculator;

import java.math.BigDecimal;

public class CalculatorModel {
    private String display = "";
    private String operator = "";
    private double number1 = 0;
    private boolean startNewNumber = true;
    private boolean hasError = false;

    public void appendDigit(String digit) {
        if (startNewNumber) {
            display = "";
            startNewNumber = false;
            hasError = false;
        }
        display += digit;
    }

    public void appendDecimalSeparator() {
        if (startNewNumber) {
            display = "0.";
            startNewNumber = false;
            hasError = false;
        } else if (!display.contains(".")) {
            display += ".";
        }
    }

    public void selectOperator(String selectedOperator) {
        if (hasError || display.isEmpty()) {
            return;
        }

        double displayedNumber = Double.parseDouble(display);
        if (!operator.isEmpty() && !startNewNumber) {
            Double result = calculate(displayedNumber);
            if (result == null) {
                return;
            }
            number1 = result;
            display = formatResult(result);
        } else {
            number1 = displayedNumber;
        }

        operator = selectedOperator;
        startNewNumber = true;
    }

    public void calculateResult() {
        if (display.isEmpty() || operator.isEmpty()) {
            return;
        }

        Double result = calculate(Double.parseDouble(display));
        if (result == null) {
            return;
        }

        display = formatResult(result);
        operator = "";
        startNewNumber = true;
    }

    public void clear() {
        display = "";
        operator = "";
        number1 = 0;
        startNewNumber = true;
        hasError = false;
    }

    public String getDisplay() {
        return display;
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

    private String formatResult(double result) {
        if (!Double.isFinite(result)) {
            return String.valueOf(result);
        }
        if (result == 0) {
            return "0";
        }
        return BigDecimal.valueOf(result).stripTrailingZeros().toPlainString();
    }

    private void showDivisionByZeroError() {
        display = "Error: división por cero";
        operator = "";
        number1 = 0;
        startNewNumber = true;
        hasError = true;
    }
}
