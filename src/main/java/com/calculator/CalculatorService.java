package com.calculator;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Contiene las reglas de negocio y los cálculos de la calculadora.
 */
public class CalculatorService {
    private static final int MAX_RESULT_PRECISION = 1_000;

    public BigDecimal parseNumber(String value) {
        return new BigDecimal(value);
    }

    public BigDecimal calculate(BigDecimal firstNumber, String operator, BigDecimal secondNumber) {
        BigDecimal result = switch (operator) {
            case "+" -> firstNumber.add(secondNumber);
            case "-" -> firstNumber.subtract(secondNumber);
            case "*" -> firstNumber.multiply(secondNumber);
            case "/" -> divide(firstNumber, secondNumber);
            default -> throw new IllegalArgumentException("Operador no soportado: " + operator);
        };

        if (result.precision() > MAX_RESULT_PRECISION) {
            throw new CalculationException("Error: resultado fuera de rango");
        }

        return result;
    }

    public String formatResult(BigDecimal result) {
        if (result.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        return result.stripTrailingZeros().toPlainString();
    }

    /**
     * Aplica el formato anglosajón de pantalla sin alterar el valor que usa el modelo.
     */
    public String formatDisplayNumber(String value) {
        if (value.isEmpty() || value.equals("-")) {
            return value;
        }

        boolean isNegative = value.startsWith("-");
        String unsignedValue = isNegative ? value.substring(1) : value;
        String[] parts = unsignedValue.split("\\.", -1);
        String integerPart = addThousandsSeparators(parts[0]);
        String formattedValue = parts.length == 2 ? integerPart + "." + parts[1] : integerPart;

        return isNegative ? "-" + formattedValue : formattedValue;
    }

    private BigDecimal divide(BigDecimal firstNumber, BigDecimal secondNumber) {
        if (secondNumber.compareTo(BigDecimal.ZERO) == 0) {
            throw new CalculationException("Error: división por cero");
        }
        return firstNumber.divide(secondNumber, MathContext.DECIMAL128);
    }

    private String addThousandsSeparators(String integerPart) {
        StringBuilder formatted = new StringBuilder(integerPart);
        for (int index = formatted.length() - 3; index > 0; index -= 3) {
            formatted.insert(index, ',');
        }
        return formatted.toString();
    }

    public static class CalculationException extends RuntimeException {
        public CalculationException(String message) {
            super(message);
        }
    }
}
