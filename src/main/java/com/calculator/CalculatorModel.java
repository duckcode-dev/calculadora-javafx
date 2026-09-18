package com.calculator;

import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatorModel {
    private static final int MAX_RESULT_PRECISION = 1_000;

    private String display = "0";
    private String operator = "";
    private BigDecimal number1 = BigDecimal.ZERO;
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

    public void deleteLastCharacter() {
        if (hasError) {
            clear();
            return;
        }
        if (display.isEmpty()) {
            return;
        }

        startNewNumber = false;
        display = display.substring(0, display.length() - 1);
        if (display.equals("-")) {
            display = "";
        }
    }

    public void toggleSign() {
        if (hasError || display.isEmpty()) {
            return;
        }

        startNewNumber = false;
        if (display.startsWith("-")) {
            display = display.substring(1);
        } else {
            display = "-" + display;
        }
    }

    public void selectOperator(String selectedOperator) {
        if (hasError || display.isEmpty()) {
            return;
        }

        BigDecimal displayedNumber = new BigDecimal(display);
        if (!operator.isEmpty() && !startNewNumber) {
            BigDecimal result = calculate(displayedNumber);
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

        BigDecimal result = calculate(new BigDecimal(display));
        if (result == null) {
            return;
        }

        display = formatResult(result);
        operator = "";
        startNewNumber = true;
    }

    public void clear() {
        display = "0";
        operator = "";
        number1 = BigDecimal.ZERO;
        startNewNumber = true;
        hasError = false;
    }

    public String getDisplay() {
        return display;
    }

    private BigDecimal calculate(BigDecimal number2) {
        BigDecimal result;

        switch (operator) {
            case "+":
                result = number1.add(number2);
                break;
            case "-":
                result = number1.subtract(number2);
                break;
            case "*":
                result = number1.multiply(number2);
                break;
            case "/":
                if (number2.compareTo(BigDecimal.ZERO) == 0) {
                    showDivisionByZeroError();
                    return null;
                }
                result = number1.divide(number2, MathContext.DECIMAL128);
                break;
            default:
                return null;
        }

        if (result.precision() > MAX_RESULT_PRECISION) {
            showOverflowError();
            return null;
        }

        return result;
    }

    private String formatResult(BigDecimal result) {
        if (result.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        return result.stripTrailingZeros().toPlainString();
    }

    private void showDivisionByZeroError() {
        display = "Error: división por cero";
        resetAfterError();
    }

    private void showOverflowError() {
        display = "Error: resultado fuera de rango";
        resetAfterError();
    }

    private void resetAfterError() {
        operator = "";
        number1 = BigDecimal.ZERO;
        startNewNumber = true;
        hasError = true;
    }
}
