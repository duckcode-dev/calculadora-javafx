package com.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class CalculatorController {
    @FXML
    private TextField display;

    private final CalculatorModel calculator = new CalculatorModel();

    @FXML
    private void handleNumber(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        calculator.appendDigit(value);
        updateDisplay();
    }

    @FXML
    private void handleDecimal(ActionEvent event) {
        calculator.appendDecimalSeparator();
        updateDisplay();
    }

    @FXML
    private void handleOperator(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        calculator.selectOperator(value);
        updateDisplay();
    }

    @FXML
    private void handleEquals(ActionEvent event) {
        calculator.calculateResult();
        updateDisplay();
    }

    @FXML
    private void handleClear(ActionEvent event) {
        calculator.clear();
        updateDisplay();
    }

    private void updateDisplay() {
        display.setText(calculator.getDisplay());
    }
}
