package com.calculator;

import java.math.BigDecimal;

public class CalculatorModel {
    private final CalculatorService calculatorService;
    private String display = "0";
    private String operator = "";
    private BigDecimal number1 = BigDecimal.ZERO;
    private boolean startNewNumber = true;
    private boolean hasError = false;

    public CalculatorModel() {
        this(new CalculatorService());
    }

    CalculatorModel(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

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

        BigDecimal displayedNumber = calculatorService.parseNumber(display);
        if (!operator.isEmpty() && !startNewNumber) {
            BigDecimal result = calculateAndHandleError(displayedNumber);
            if (result == null) {
                return;
            }
            number1 = result;
            display = calculatorService.formatResult(result);
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

        BigDecimal result = calculateAndHandleError(calculatorService.parseNumber(display));
        if (result == null) {
            return;
        }

        display = calculatorService.formatResult(result);
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
        return hasError ? display : calculatorService.formatDisplayNumber(display);
    }

    private BigDecimal calculateAndHandleError(BigDecimal number2) {
        try {
            return calculatorService.calculate(number1, operator, number2);
        } catch (CalculatorService.CalculationException exception) {
            display = exception.getMessage();
            resetAfterError();
            return null;
        }
    }

    private void resetAfterError() {
        operator = "";
        number1 = BigDecimal.ZERO;
        startNewNumber = true;
        hasError = true;
    }
}
