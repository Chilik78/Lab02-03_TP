package Model;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CalculatorModel {
    private double firstNumber;
    private double secondNumber;
    private String operator;
    private String currentInput;
    private boolean startNewNumber;
    private CurrencyService currencyService;
    private ObservableList<String> conversionHistory;
    
    public CalculatorModel() {
        clear();
        currencyService = new CurrencyService();
        conversionHistory = FXCollections.observableArrayList();
    }
    
    public void inputNumber(String number) {
        if (startNewNumber) {
            currentInput = number;
            startNewNumber = false;
        } else {
            currentInput += number;
        }
    }
    
    public void inputDecimal() {
        if (startNewNumber) {
            currentInput = "0.";
            startNewNumber = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
    }
    
    public void setOperator(String newOperator) {
        if (!operator.isEmpty() && !startNewNumber) {
            calculate();
        }
        
        if (!currentInput.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
            operator = newOperator;
            startNewNumber = true;
        }
    }
    
    public void calculate() {
        if (operator.isEmpty() || currentInput.isEmpty()) return;
        
        secondNumber = Double.parseDouble(currentInput);
        double result = 0;
        
        switch (operator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "×":
                result = firstNumber * secondNumber;
                break;
            case "÷":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    currentInput = "Ошибка";
                    return;
                }
                break;
        }
        
        if (result == (long) result) {
            currentInput = String.valueOf((long) result);
        } else {
            currentInput = String.valueOf(result);
        }
        
        operator = "";
        startNewNumber = true;
    }
    
    public void clear() {
        currentInput = "";
        operator = "";
        firstNumber = 0;
        secondNumber = 0;
        startNewNumber = true;
    }
    
    public void toggleSign() {
        if (!currentInput.isEmpty() && !currentInput.equals("0") && !currentInput.equals("Ошибка")) {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
        }
    }
    
    public void calculatePercentage() {
        if (!currentInput.isEmpty() && !currentInput.equals("Ошибка")) {
            double number = Double.parseDouble(currentInput);
            double result = number / 100;
            
            if (result == (long) result) {
                currentInput = String.valueOf((long) result);
            } else {
                currentInput = String.valueOf(result);
            }
            
            startNewNumber = true;
        }
    }
    
    public String getCurrentInput() {
        return currentInput.isEmpty() ? "0" : currentInput;
    }
    
    public void setCurrentInput(String currentInput) {
        this.currentInput = currentInput;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public boolean isStartNewNumber() {
        return startNewNumber;
    }

    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        double result = currencyService.convertCurrency(fromCurrency, toCurrency, amount);
        
        // Сохраняем в историю
        String historyEntry = String.format("%.2f %s = %.2f %s", 
            amount, fromCurrency, result, toCurrency);
        conversionHistory.add(historyEntry);
        
        return result;
    }
    
    public ObservableList<String> getConversionHistory() {
        return conversionHistory;
    }
    
    public void clearConversionHistory() {
        conversionHistory.clear();
    }
    
    public double getLastConversionResult() {
        if (conversionHistory.isEmpty()) return 0;
        
        String lastEntry = conversionHistory.get(conversionHistory.size() - 1);
        // Парсим результат из строки истории
        String[] parts = lastEntry.split("=");
        if (parts.length > 1) {
            try {
                String resultStr = parts[1].trim().split(" ")[0];
                return Double.parseDouble(resultStr);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }
    
    // Добавим метод для получения текущего значения как double
    public double getCurrentValue() {
        try {
            return Double.parseDouble(currentInput);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}