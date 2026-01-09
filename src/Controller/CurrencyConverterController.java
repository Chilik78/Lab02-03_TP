package Controller;

import Model.CalculatorModel;
import View.CurrencyConverterView;
import javafx.collections.ListChangeListener;

public class CurrencyConverterController {
    private CalculatorModel model;
    private CurrencyConverterView view;
    
    public CurrencyConverterController(CalculatorModel model, CurrencyConverterView view) {
        this.model = model;
        this.view = view;
        
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        // Кнопка конвертации
        view.getConvertButton().setOnAction(e -> handleConvert());
        
        // Кнопка обмена валют
        view.getSwapButton().setOnAction(e -> view.swapCurrencies());
        
        // Кнопка очистки истории
        view.getClearHistoryButton().setOnAction(e -> handleClearHistory());
        
        // Обновление истории при изменении
        model.getConversionHistory().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                updateHistoryView();
            }
        });
    }
    
    private void handleConvert() {
        try {
            String fromCurrency = view.getFromCurrencyCombo().getValue();
            String toCurrency = view.getToCurrencyCombo().getValue();
            double amount = Double.parseDouble(view.getAmountField().getText());
            
            double result = model.convertCurrency(fromCurrency, toCurrency, amount);
            
            String resultText = String.format("%.2f %s = %.2f %s", 
                amount, fromCurrency, result, toCurrency);
            view.updateResult(resultText);
            
            // Обновляем историю после конвертации
            updateHistoryView();
            
        } catch (NumberFormatException e) {
            view.updateResult("Ошибка: неверная сумма");
        } catch (Exception e) {
            view.updateResult("Ошибка конвертации");
        }
    }
    
    private void handleClearHistory() {
        model.clearConversionHistory();
        updateHistoryView();
    }
    
    private void updateHistoryView() {
        view.updateHistory(model.getConversionHistory());
    }
}