package test.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.application.Platform;

import Controller.CalculatorController;
import Model.CalculatorModel;
import View.CalculatorView;

public class CalculatorControllerTest extends Application {

    CalculatorView view;

    @Override
    public void start(Stage primaryStage) {
        view = new CalculatorView();
        CalculatorModel model = new CalculatorModel();
        CalculatorController controller = new CalculatorController(model, view);

        Scene scene = new Scene(view.getRoot(), 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Тест калькулятора");
        primaryStage.show();

        testOperatorsController();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void testOperatorsController() {
        try {
            System.out.println("\nТестирование сложения");
            // Тест: 2 + 3 = 5
            testAddOperator(2, 3, "5");
            // Тест: 1 + 5 = 6
            testAddOperator(1, 5, "6");

            System.out.println("\nТестирование деления");
            // Тест: 6 / 3 = 2
            testDivideOperator(6, 3, "2");
            // Тест: 9 / 3 = 2
            testDivideOperator(9, 3, "3");

            System.out.println("\nТестирование умножения");
            // Тест: 5 * 2 = 10
            testMultiplyOperator(5, 2, "10");
            // Тест: 3 * 3 = 9
            testMultiplyOperator(3, 3, "9");


            System.out.println("\nТестирование вычитания");
            // Тест: 5 - 2 = 3
            testSubstractionOperator(5, 2, "3");
            // Тест: 3 - 3 = 0
            testSubstractionOperator(3, 3, "0");
            // Тест: 4 - 3 = 1
            testSubstractionOperator(4, 3, "1");

            System.out.println("\nТестирование очищения");
            testClearBtn();

            System.out.println("\nТестирование кнопки замены знака");
            testSignBtn(3, "-3");

            System.out.println("\n========================================");
            System.out.println("✓ ВСЕ ТЕСТЫ ПРОЙДЕНЫ УСПЕШНО!");
        } catch (Exception err) {
            System.out.println("Тесты не были пройдены: " + err);
            Platform.exit();           
        }
    }

    private void testAddOperator(int firstIdxNum, int secondIdxNum, String expectedResult) {
        view.getNumberButtons()[firstIdxNum].fire();
        view.getAddButton().fire();
        view.getNumberButtons()[secondIdxNum].fire(); 
        view.getEqualsButton().fire();
        assertEquals(expectedResult, view.getDisplay().getText());
        System.out.println("Тест успешно пройден: \n Ожидаемое значение: " + expectedResult + "\n Полученное значение: " + view.getDisplay().getText());
    }

    private void testDivideOperator(int firstIdxNum, int secondIdxNum, String expectedResult) {
        view.getNumberButtons()[firstIdxNum].fire();
        view.getDivideButton().fire();
        view.getNumberButtons()[secondIdxNum].fire(); 
        view.getEqualsButton().fire();
        assertEquals(expectedResult, view.getDisplay().getText());
        System.out.println("Тест успешно пройден: \n Ожидаемое значение: " + expectedResult + "\n Полученное значение: " + view.getDisplay().getText());
    }

    private void testMultiplyOperator(int firstIdxNum, int secondIdxNum, String expectedResult) {
        view.getNumberButtons()[firstIdxNum].fire();
        view.getMultiplyButton().fire();
        view.getNumberButtons()[secondIdxNum].fire(); 
        view.getEqualsButton().fire();
        assertEquals(expectedResult, view.getDisplay().getText());
        System.out.println("Тест успешно пройден: \n Ожидаемое значение: " + expectedResult + "\n Полученное значение: " + view.getDisplay().getText());
    }

    private void testSubstractionOperator(int firstIdxNum, int secondIdxNum, String expectedResult) {
        view.getNumberButtons()[firstIdxNum].fire();
        view.getSubstractButton().fire();
        view.getNumberButtons()[secondIdxNum].fire(); 
        view.getEqualsButton().fire();
        assertEquals(expectedResult, view.getDisplay().getText());
        System.out.println("Тест успешно пройден: \n Ожидаемое значение: " + expectedResult + "\n Полученное значение: " + view.getDisplay().getText());
    }

    private void testClearBtn() {
        view.getNumberButtons()[1].fire();
        view.getClearButton().fire();
        assertEquals("0", view.getDisplay().getText());
        System.out.println("Тест успешно пройден: \n Ожидаемое значение: 0" + "\n Полученное значение: " + view.getDisplay().getText());
    }

    private void testSignBtn(int idxNum, String expectedResult) {
        view.getNumberButtons()[idxNum].fire();
        view.getSignButton().fire();
        assertEquals(expectedResult, view.getDisplay().getText());
        System.out.println("Тест успешно пройден: \n Ожидаемое значение: " + expectedResult + "\n Полученное значение: " + view.getDisplay().getText());
    }
}