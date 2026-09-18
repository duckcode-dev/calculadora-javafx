package com.calculator;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private void handleBackspace(ActionEvent event) {
        calculator.deleteLastCharacter();
        updateDisplay();
    }

    @FXML
    private void handleToggleSign(ActionEvent event) {
        calculator.toggleSign();
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

    @FXML
    private void handleKeyPress(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        if (keyCode.isDigitKey() || keyCode.isKeypadKey()) {
            String digit = event.getText();
            if (digit.matches("\\d")) {
                calculator.appendDigit(digit);
                updateDisplay();
                event.consume();
                return;
            }
        }

        switch (keyCode) {
                case DECIMAL:
                case PERIOD:
                    calculator.appendDecimalSeparator();
                    break;
                case ADD:
                case PLUS:
                    calculator.selectOperator("+");
                    break;
                case SUBTRACT:
                case MINUS:
                    calculator.selectOperator("-");
                    break;
                case MULTIPLY:
                    calculator.selectOperator("*");
                    break;
                case DIVIDE:
                    calculator.selectOperator("/");
                    break;
                case ENTER:
                case EQUALS:
                    calculator.calculateResult();
                    break;
                case BACK_SPACE:
                    calculator.deleteLastCharacter();
                    break;
                case DELETE:
                case ESCAPE:
                    calculator.clear();
                    break;
            default:
                return;
        }

        updateDisplay();
        event.consume();
    }

    private void updateDisplay() {
        display.setText(calculator.getDisplay());
    }
}
