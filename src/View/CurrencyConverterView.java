package View;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.List;

public class CurrencyConverterView {
    private VBox root;
    private ComboBox<String> fromCurrencyCombo;
    private ComboBox<String> toCurrencyCombo;
    private TextField amountField;
    private TextField resultField;
    private Button convertButton;
    private Button swapButton;
    private ListView<String> historyList;
    private Button clearHistoryButton;
    
    // Основные валюты
    private static final String[] CURRENCIES = {
        "USD", "EUR", "GBP", "JPY", "RUB", "CNY", "CHF", "CAD", "AUD"
    };
    
    public CurrencyConverterView() {
        createView();
    }
    
    private void createView() {
        root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #2c3e50;");
        root.setAlignment(Pos.TOP_CENTER);
        
        // Заголовок
        Label title = new Label("Конвертер валют");
        title.setFont(Font.font("Arial", 24));
        title.setStyle("-fx-text-fill: white;");
        
        // Панель выбора валют
        HBox currencyPanel = new HBox(10);
        currencyPanel.setAlignment(Pos.CENTER);
        
        fromCurrencyCombo = createCurrencyCombo();
        fromCurrencyCombo.setValue("USD");
        
        swapButton = new Button("⇄");
        swapButton.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;");
        swapButton.setPrefSize(40, 30);
        
        toCurrencyCombo = createCurrencyCombo();
        toCurrencyCombo.setValue("EUR");
        
        Label fromLabel = new Label("Из:");
        fromLabel.setStyle("-fx-text-fill: white;");

        Label toLabel = new Label("В:");
        toLabel.setStyle("-fx-text-fill: white;");

        currencyPanel.getChildren().addAll(
            fromLabel, fromCurrencyCombo,
            swapButton,
            toLabel, toCurrencyCombo
        );
        
        // Панель ввода суммы
        HBox amountPanel = new HBox(10);
        amountPanel.setAlignment(Pos.CENTER);
        
        amountField = new TextField("1.00");
        amountField.setPrefWidth(150);
        amountField.setFont(Font.font("Arial", 14));
        amountField.setStyle("-fx-background-color: white;");
        
        Label summLabel =  new Label("Сумма:");
        summLabel.setStyle("-fx-text-fill: white;");

        amountPanel.getChildren().addAll(
            summLabel, amountField
        );
        
        // Кнопка конвертации
        convertButton = new Button("Конвертировать");
        convertButton.setStyle("-fx-background-color: #1abc9c; -fx-text-fill: white; -fx-font-size: 14px;");
        convertButton.setPrefWidth(200);
        
        // Поле результата
        resultField = new TextField();
        resultField.setEditable(false);
        resultField.setFont(Font.font("Arial", 16));
        resultField.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        resultField.setPrefWidth(300);
        
        
        // История конвертаций
        Label historyLabel = new Label("История конвертаций:");
        historyLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        
        historyList = new ListView<>();
        historyList.setPrefHeight(150);
        historyList.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        
        clearHistoryButton = new Button("Очистить историю");
        clearHistoryButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        
        // Добавляем все элементы в корневой контейнер
        root.getChildren().addAll(
            title, currencyPanel, amountPanel, convertButton,
            resultField, historyLabel, historyList, clearHistoryButton
        );
    }
    
    private ComboBox<String> createCurrencyCombo() {
        ComboBox<String> combo = new ComboBox<>(FXCollections.observableArrayList(CURRENCIES));
        combo.setPrefWidth(100);
        combo.setStyle("-fx-background-color: white;");
        return combo;
    }
    
    public VBox getRoot() {
        return root;
    }
    
    // Геттеры
    public ComboBox<String> getFromCurrencyCombo() {
        return fromCurrencyCombo;
    }
    
    public ComboBox<String> getToCurrencyCombo() {
        return toCurrencyCombo;
    }
    
    public TextField getAmountField() {
        return amountField;
    }
    
    public TextField getResultField() {
        return resultField;
    }
    
    public Button getConvertButton() {
        return convertButton;
    }
    
    public Button getSwapButton() {
        return swapButton;
    }
    
    public ListView<String> getHistoryList() {
        return historyList;
    }
    
    public Button getClearHistoryButton() {
        return clearHistoryButton;
    }
    
    public void updateResult(String result) {
        resultField.setText(result);
    }
    
    public void updateHistory(List<String> history) {
        historyList.setItems(FXCollections.observableArrayList(history));
    }
    
    public void swapCurrencies() {
        String from = fromCurrencyCombo.getValue();
        String to = toCurrencyCombo.getValue();
        fromCurrencyCombo.setValue(to);
        toCurrencyCombo.setValue(from);
    }
}