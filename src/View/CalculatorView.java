package View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CalculatorView {
    private BorderPane root;
    private TextField display;
    private Button[] numberButtons;
    private Button divideButton, multiplyButton, subtractButton, addButton;
    private Button equalsButton, clearButton, signButton, percentButton, decimalButton;

    public BorderPane getRoot() { return root; }
    public TextField getDisplay() { return display; }
    public Button[] getNumberButtons() { return numberButtons; }
    public Button getClearButton() { return clearButton; }
    public Button getSignButton() { return signButton; }
    public Button getPercentButton() { return percentButton; }
    public Button getDecimalButton() { return decimalButton; }
    public Button getEqualsButton() { return equalsButton; }
    public Button getDivideButton() { return divideButton; }
    public Button getMultiplyButton() { return multiplyButton; }
    public Button getSubstractButton() { return subtractButton; }
    public Button getAddButton() { return addButton; }
    
    public CalculatorView() {
        createView();
    }
    
    private void createView() {
        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #2c3e50;");
        
        createDisplay();
        createButtons();
    }
    
    private void createDisplay() {
        VBox displayContainer = new VBox(10);
        displayContainer.setPadding(new Insets(10));
        displayContainer.setAlignment(Pos.CENTER);
        
        display = new TextField("0");
        display.setFont(Font.font("Arial", 24));
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setEditable(false);
        display.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #1abc9c; -fx-border-width: 2px;");
        display.setPrefHeight(60);
        display.setPrefWidth(300);
        
        displayContainer.getChildren().add(display);
        root.setTop(displayContainer);
    }
    
    private void createButtons() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);
        
        // Функциональные кнопки
        clearButton = createStyledButton("C", "function");
        signButton = createStyledButton("±", "function");
        percentButton = createStyledButton("%", "function");
        divideButton = createStyledButton("÷", "operator");
        
        // Числовые кнопки
        numberButtons = new Button[10];
        for (int i = 0; i <= 9; i++) {
            numberButtons[i] = createStyledButton(String.valueOf(i), "number");
        }
        
        // Операторы
        multiplyButton = createStyledButton("×", "operator");
        subtractButton = createStyledButton("-", "operator");
        addButton = createStyledButton("+", "operator");
        equalsButton = createStyledButton("=", "equals");
        decimalButton = createStyledButton(".", "number");
        
        // Размещение кнопок в сетке
        grid.add(clearButton, 0, 0);
        grid.add(signButton, 1, 0);
        grid.add(percentButton, 2, 0);
        grid.add(divideButton, 3, 0);
        
        grid.add(numberButtons[7], 0, 1);
        grid.add(numberButtons[8], 1, 1);
        grid.add(numberButtons[9], 2, 1);
        grid.add(multiplyButton, 3, 1);
        
        grid.add(numberButtons[4], 0, 2);
        grid.add(numberButtons[5], 1, 2);
        grid.add(numberButtons[6], 2, 2);
        grid.add(subtractButton, 3, 2);
        
        grid.add(numberButtons[1], 0, 3);
        grid.add(numberButtons[2], 1, 3);
        grid.add(numberButtons[3], 2, 3);
        grid.add(addButton, 3, 3);
        
        // Кнопка 0 занимает две колонки
        grid.add(numberButtons[0], 0, 4, 2, 1);
        numberButtons[0].setPrefSize(130, 60);
        
        grid.add(decimalButton, 2, 4);
        grid.add(equalsButton, 3, 4);
        
        root.setCenter(grid);
    }
    
    private Button createStyledButton(String text, String type) {
        Button button = new Button(text);
        button.setPrefSize(60, 60);
        button.setFont(Font.font("Arial", 18));
        
        switch (type) {
            case "number":
                button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #7f8c8d;");
                button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #4a6572; -fx-text-fill: white; -fx-border-color: #7f8c8d;"));
                button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-border-color: #7f8c8d;"));
                break;
            case "operator":
                button.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-border-color: #d35400;");
                button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #d35400; -fx-text-fill: white; -fx-border-color: #d35400;"));
                button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-border-color: #d35400;"));
                break;
            case "equals":
                button.setStyle("-fx-background-color: #1abc9c; -fx-text-fill: white; -fx-border-color: #16a085;");
                button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #16a085; -fx-text-fill: white; -fx-border-color: #16a085;"));
                button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #1abc9c; -fx-text-fill: white; -fx-border-color: #16a085;"));
                break;
            case "function":
                button.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-border-color: #7f8c8d;");
                button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #bdc3c7; -fx-text-fill: white; -fx-border-color: #7f8c8d;"));
                button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-border-color: #7f8c8d;"));
                break;
        }
        
        return button;
    }
    
    public void updateDisplay(String text) {
        display.setText(text);
    }
}