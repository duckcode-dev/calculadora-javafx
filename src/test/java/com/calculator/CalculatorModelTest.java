package com.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CalculatorModelTest {

    @Test
    void calculatesBasicOperations() {
        assertEquals("5", calculate("2", "+", "3"));
        assertEquals("2", calculate("5", "-", "3"));
        assertEquals("12", calculate("4", "*", "3"));
        assertEquals("2.5", calculate("5", "/", "2"));
    }

    @Test
    void resolvesPendingOperationWhenSelectingAnotherOperator() {
        CalculatorModel calculator = new CalculatorModel();

        enterNumber(calculator, "2");
        calculator.selectOperator("+");
        enterNumber(calculator, "3");
        calculator.selectOperator("*");
        enterNumber(calculator, "4");
        calculator.calculateResult();

        assertEquals("20", calculator.getDisplay());
    }

    @Test
    void showsErrorAndRecoversAfterDivisionByZero() {
        CalculatorModel calculator = new CalculatorModel();

        enterNumber(calculator, "5");
        calculator.selectOperator("/");
        enterNumber(calculator, "0");
        calculator.calculateResult();

        assertEquals("Error: división por cero", calculator.getDisplay());

        enterNumber(calculator, "7");

        assertEquals("7", calculator.getDisplay());
    }

    @Test
    void clearResetsCalculatorState() {
        CalculatorModel calculator = new CalculatorModel();

        enterNumber(calculator, "8");
        calculator.selectOperator("+");
        calculator.clear();
        enterNumber(calculator, "2");
        calculator.calculateResult();

        assertEquals("2", calculator.getDisplay());
    }

    @Test
    void allowsOnlyOneDecimalSeparatorPerNumber() {
        CalculatorModel calculator = new CalculatorModel();

        calculator.appendDecimalSeparator();
        calculator.appendDigit("5");
        calculator.appendDecimalSeparator();

        assertEquals("0.5", calculator.getDisplay());
    }

    @Test
    void deletesLastEnteredCharacter() {
        CalculatorModel calculator = new CalculatorModel();

        enterNumber(calculator, "123");
        calculator.deleteLastCharacter();

        assertEquals("12", calculator.getDisplay());
    }

    @Test
    void togglesTheSignOfTheCurrentNumber() {
        CalculatorModel calculator = new CalculatorModel();

        enterNumber(calculator, "5");
        calculator.toggleSign();
        calculator.selectOperator("+");
        enterNumber(calculator, "2");
        calculator.calculateResult();

        assertEquals("-3", calculator.getDisplay());
    }

    private String calculate(String firstNumber, String operator, String secondNumber) {
        CalculatorModel calculator = new CalculatorModel();
        enterNumber(calculator, firstNumber);
        calculator.selectOperator(operator);
        enterNumber(calculator, secondNumber);
        calculator.calculateResult();
        return calculator.getDisplay();
    }

    private void enterNumber(CalculatorModel calculator, String number) {
        for (char digit : number.toCharArray()) {
            if (digit == '.') {
                calculator.appendDecimalSeparator();
            } else {
                calculator.appendDigit(String.valueOf(digit));
            }
        }
    }
}
