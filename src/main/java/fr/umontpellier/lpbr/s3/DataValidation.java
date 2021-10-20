package fr.umontpellier.lpbr.s3;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DataValidation {
    public static boolean textFieldIsNull(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNull = false;
        String validationString = null;

        if (inputTextField.getText().isEmpty()) {
            isNull = true;
            validationString = validationText;
        }

        inputLabel.setText(validationString);
        return isNull;
    }

    public static boolean dataLength(TextField inputTextField, Label inputLabel, String validationText, String requiredLength) {
        boolean isDataLength = true;
        String validationString = null;

        if (!inputTextField.getText().matches("\\b\\w" + "{" + requiredLength + "}" + "\\b")) {
            isDataLength = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isDataLength;
    }

    public static boolean textAlphabet(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isAlphabet = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[a-zA-Z]+")) {
            isAlphabet = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isAlphabet;
    }

    public static boolean textNumeric(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNumeric = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[0-9]+")) {
            isNumeric = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isNumeric;
    }
}
