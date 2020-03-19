package calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Button btnAC;
    @FXML
    Button btnPlsMin;
    @FXML
    Button btnPercent;
    @FXML
    Button btn1;
    @FXML
    Button btn2;
    @FXML
    Button btn3;
    @FXML
    Button btn4;
    @FXML
    Button btn5;
    @FXML
    Button btn6;
    @FXML
    Button btn7;
    @FXML
    Button btn8;
    @FXML
    Button btn9;
    @FXML
    Button btn0;
    @FXML
    Button btnDec;
    @FXML
    Button btnAdd;
    @FXML
    Button btnSub;
    @FXML
    Button btnMul;
    @FXML
    Button btnDiv;
    @FXML
    Button btnEq;
    @FXML
    Label output;
    @FXML
    Label operationLabel;


    public boolean decimal;
    public double number1;
    public double outputNumber;
    public Operation operation;
    public boolean operationNew = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Runs when a key is pressed on the keyboard while the window is focused.
     * Parses out the key and what button is tied to it on the calculator GUI, then clicks said button.
     *
     * @param e The keyboard event
     */
    @FXML
    public void pressKey(KeyEvent e) {
        System.out.println(e.getCode());
        Button b = parseKey(e.getCode());
        System.out.println(b);
        if (b != null) {
            b.fire();
        }
    }

    /**
     * The function that gets called when one of the number buttons is pressed on the keyboard.
     * Parses the value of the button and adds it to the output data.
     *
     * @param e The event passed in by javafx
     */
    @FXML
    public void pressNumberButton(ActionEvent e) {
        // If an operation was just applied, clear out the
        // data in the output so a new number can be typed.
        if (operationNew) {
            output.setText("");
            operationNew = false;
        }
        // If a decimal point was just added, add a decimal
        // point to the output bar directly before the next number.
        if (decimal) {
            output.setText(output.getText() + ".");
            decimal = false;
        }
        // Parse the number represented by the button. Simply pulls the number
        // off of the label on the button and parses it to an integer.
        Button source = (Button) e.getSource();
        int number = Integer.parseInt(source.getText());

        // Pull the current text and add a character to the end using string concatenation.
        String oText = output.getText();
        oText += number;
        // Parse the number back into a double and display it, as well as storing it for future use.
        outputNumber = Double.parseDouble(oText);
        setOutputText(outputNumber);

    }

    /**
     * Calculates values and sets the current operation to addition
     */
    @FXML
    public void addPressed() {
        if (operation != null && !operationNew) {
            calculate();
        }
        operation = Operation.ADD;
        operationLabel.setText("+");
        number1 = outputNumber;
        operationNew = true;
    }

    /**
     * Calculates values and sets the current operation to subtraction
     */
    @FXML
    public void subPressed() {
        if (operation != null && !operationNew) {
            calculate();
        }
        operation = Operation.SUBTRACT;
        operationLabel.setText("−");
        number1 = outputNumber;
        operationNew = true;
    }

    /**
     * Calculates values and sets the current operation to multiplication
     */
    @FXML
    public void mulPressed() {
        if (operation != null && !operationNew) {
            calculate();
        }
        operation = Operation.MULTIPLY;
        operationLabel.setText("×");
        number1 = outputNumber;
        operationNew = true;
    }

    /**
     * Calculates values and sets the current operation to division
     */
    @FXML
    public void divPressed() {
        if (operation != null && !operationNew) {
            calculate();
        }
        operation = Operation.DIVIDE;
        operationLabel.setText("÷");
        number1 = outputNumber;
        operationNew = true;
    }

    /**
     * Calculate the current values
     */
    @FXML
    public void eqPressed() {
        if (operation != null) {
            calculate();
        }
        operation = null;
        operationNew = true;
    }

    /**
     * Sets the output text to be a double or an integer, based off what's passed in.
     * @param number
     */
    public void setOutputText(double number) {
        if (isDouble(number)) {
            output.setText("" + number);
        } else {
            output.setText("" + (int) number);
        }
    }

    /**
     * Perform the calculations based on the current operation and the stored number
     */
    public void calculate() {
        System.out.println(number1);
        switch (operation) {
            case ADD:
                outputNumber = number1 + outputNumber;
                break;
            case SUBTRACT:
                outputNumber = number1 - outputNumber;
                break;
            case MULTIPLY:
                outputNumber = number1 * outputNumber;
                break;
            case DIVIDE:
                // Make sure you aren't dividing by zero. Set the output to 0 if you are.
                if (outputNumber != 0) {
                    outputNumber = number1 / outputNumber;
                } else {
                    outputNumber = 0;
                }
                break;
        }
        setOutputText(outputNumber);
        operation = null;
        operationLabel.setText("");
    }

    /**
     * Negate the current number and display it
     */
    @FXML
    public void pressNegateButton() {
        outputNumber *= -1;
        output.setText("" + outputNumber);
    }

    /**
     * Clears out the current number being typed, or the entire equation if the current output number is 0.
     */
    @FXML
    public void pressClearButton() {
        if (number1 == 0) {
            operation = null;
            operationLabel.setText("");
        }
        if (outputNumber == 0) {
            number1 = 0;
        }
        outputNumber = 0;
        output.setText("0");
    }

    /**
     * Adds a decimal point to the output. Will not show up until another digit is typed afterwards.
     * See <code>pressNumberButton()</code> for the rest of the implementation.
     */
    @FXML
    public void decimalPressed() {
        if (!isDouble(outputNumber)) {
            decimal = true;
        }
    }

    /**
     * Called when the percent button is pressed on the GUI.
     * Multiplies the current value in the output bar by 100.
     */
    @FXML
    public void percentPressed() {
        outputNumber *= 100;
        setOutputText(outputNumber);
    }

    /**
     * Tests whether a number has a decimal form or is just an integer
     *
     * @param number The number to test if it's a double
     * @return true if a value past the decimal exists
     */
    private static boolean isDouble(double number) {
        return !(Math.floor(number) == number);
    }

    /**
     * Takes a keyboard key that is pressed and returns the GUI button associated with that key.
     * For some reason enter doesn't appear to return a keyboard value (which is strange)
     *
     * @param key The keyboard keycode from the event
     * @return The GUI button
     */
    private Button parseKey(KeyCode key) {
        switch (key) {
            case DIGIT0:
            case NUMPAD0:
                return btn0;
            case DIGIT1:
            case NUMPAD1:
                return btn1;
            case DIGIT2:
            case NUMPAD2:
                return btn2;
            case DIGIT3:
            case NUMPAD3:
                return btn3;
            case DIGIT4:
            case NUMPAD4:
                return btn4;
            case DIGIT5:
            case NUMPAD5:
                return btn5;
            case DIGIT6:
            case NUMPAD6:
                return btn6;
            case DIGIT7:
            case NUMPAD7:
                return btn7;
            case DIGIT8:
            case NUMPAD8:
                return btn8;
            case DIGIT9:
            case NUMPAD9:
                return btn9;
            case ENTER:
                return btnEq;
            case MULTIPLY:
                return btnMul;
            case DIVIDE:
                return btnDiv;
            case ADD:
                return btnAdd;
            case SUBTRACT:
                return btnSub;
            case DECIMAL:
            case PERIOD:
                return btnDec;
            case C:
            case DELETE:
                return btnAC;
            default:
                return null;
        }
    }

}

enum Operation {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE
}