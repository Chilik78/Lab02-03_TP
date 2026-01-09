// App.java (обновленная версия)
import Controller.CalculatorController;
import Controller.CurrencyConverterController;
import Model.CalculatorModel;
import View.CalculatorView;
import View.CurrencyConverterView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {
    
    private enum Mode { CALCULATOR, CURRENCY_CONVERTER }
    private Mode currentMode = Mode.CALCULATOR;
    
    private CalculatorModel model;
    private CalculatorView calculatorView;
    private CurrencyConverterView currencyView;
    private CalculatorController calculatorController;
    private CurrencyConverterController currencyController;
    
    @Override
    public void start(Stage primaryStage) {
        model = new CalculatorModel();
        
        // Создаем представления
        calculatorView = new CalculatorView();
        currencyView = new CurrencyConverterView();
        
        // Создаем контроллеры
        calculatorController = new CalculatorController(model, calculatorView);
        currencyController = new CurrencyConverterController(model, currencyView);
        
        // Создаем панель переключения режимов
        HBox modeSwitch = new HBox(10);
        modeSwitch.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-background-color: #2c3e50;");
        
        Button calculatorBtn = new Button("Калькулятор");
        Button currencyBtn = new Button("Конвертер валют");
        
        calculatorBtn.setStyle("-fx-background-color: #1abc9c; -fx-text-fill: white;");
        currencyBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        
        calculatorBtn.setOnAction(e -> switchMode(Mode.CALCULATOR, primaryStage));
        currencyBtn.setOnAction(e -> switchMode(Mode.CURRENCY_CONVERTER, primaryStage));
        
        modeSwitch.getChildren().addAll(calculatorBtn, currencyBtn);
        
        // Основной контейнер
        BorderPane root = new BorderPane();
        root.setTop(modeSwitch);
        root.setCenter(calculatorView.getRoot());
        
        // Начальная сцена
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("Универсальный калькулятор с конвертацией валют");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void switchMode(Mode newMode, Stage stage) {
        currentMode = newMode;
        
        BorderPane root = (BorderPane) stage.getScene().getRoot();
        
        switch (newMode) {
            case CALCULATOR:
                root.setCenter(calculatorView.getRoot());
                stage.setWidth(400);
                stage.setHeight(600);
                break;
            case CURRENCY_CONVERTER:
                root.setCenter(currencyView.getRoot());
                stage.setWidth(450);
                stage.setHeight(700);
                currencyView.updateHistory(model.getConversionHistory());
                break;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}