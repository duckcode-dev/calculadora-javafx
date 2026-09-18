package com.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CalculatorServiceTest {
    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void calculatesBasicOperations() {
        assertEquals("5", calculate("2", "+", "3"));
        assertEquals("2", calculate("5", "-", "3"));
        assertEquals("12", calculate("4", "*", "3"));
        assertEquals("2.5", calculate("5", "/", "2"));
    }

    @Test
    void keepsDecimalCalculationsExactAndReadable() {
        assertEquals("0.3", calculate("0.1", "+", "0.2"));
        assertEquals("0.02", calculate("0.1", "*", "0.2"));
        assertEquals("0.3", calculate("0.9", "/", "3"));
        assertEquals("1.23", calculate("1.20", "+", "0.03"));
    }

    @Test
    void formatsZeroAndRemovesUnnecessaryTrailingZeros() {
        assertEquals("0", calculatorService.formatResult(new BigDecimal("0.00")));
        assertEquals("42", calculatorService.formatResult(new BigDecimal("42.000")));
    }

    @Test
    void formatsDisplayNumbersWithEnglishThousandsSeparators() {
        assertEquals("100,000", calculatorService.formatDisplayNumber("100000"));
        assertEquals("1,234.56", calculatorService.formatDisplayNumber("1234.56"));
        assertEquals("-1,000.", calculatorService.formatDisplayNumber("-1000."));
    }

    @Test
    void rejectsDivisionByZero() {
        CalculatorService.CalculationException exception = assertThrows(
                CalculatorService.CalculationException.class,
                () -> calculatorService.calculate(BigDecimal.ONE, "/", BigDecimal.ZERO)
        );

        assertEquals("Error: división por cero", exception.getMessage());
    }

    @Test
    void rejectsResultsOutsideSupportedPrecision() {
        BigDecimal tooLargeNumber = new BigDecimal("1" + "0".repeat(1_000));

        CalculatorService.CalculationException exception = assertThrows(
                CalculatorService.CalculationException.class,
                () -> calculatorService.calculate(tooLargeNumber, "*", BigDecimal.TWO)
        );

        assertEquals("Error: resultado fuera de rango", exception.getMessage());
    }

    private String calculate(String firstNumber, String operator, String secondNumber) {
        BigDecimal result = calculatorService.calculate(
                calculatorService.parseNumber(firstNumber),
                operator,
                calculatorService.parseNumber(secondNumber)
        );
        return calculatorService.formatResult(result);
    }
}
