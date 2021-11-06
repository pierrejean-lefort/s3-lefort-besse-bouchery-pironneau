package fr.umontpellier.lpbr.s3;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/** Les fonctions de cette classe renvoie vrai (true) lorsque tout vas bien
 *  Et renvoient faux (false) en cas d'erreur.
 */
public class DataValidation {
    /** @return true if nothing is wrong*/
    public static boolean textFieldIsNull(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNull = true;
        String validationString = null;

        if (inputTextField.getText().isEmpty()) {
            isNull = false;
            validationString = validationText;
        }

        inputLabel.setText(validationString);
        return isNull;
    }
    /** @return true if nothing is wrong*/
    public static boolean dataLength(TextField inputTextField, Label inputLabel, String validationText, int requiredLength) {
        boolean isDataLength = true;
        String validationString = null;

        if (inputTextField.getText().length()>requiredLength) {
            isDataLength = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isDataLength;
    }
    /** @return true if nothing is wrong*/
    public static boolean textAlphabet(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isAlphabet = true;
        String validationString = null;

        if (!inputTextField.getText().matches("^([a-zA-Z]{2,}[a-zA-Z]{1,}'?-?[a-zA-Z]{2,}([a-zA-Z]{1,})?)")) {
            isAlphabet = false;
            validationString = validationText;
        }
        inputLabel.setText(validationString);
        return isAlphabet;
    }
    /** @return true if String is numeric*/
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
    /** @return true if nothing is wrong*/
    public static boolean joueurExiste(Joueur j, Tournoi t, Label inputLabel, String validationText){
        boolean exists = true;
        String validationString = null;

        for (Participe p : t.getParticipation()) {
            System.out.println("J: " + j);
            System.out.println("P: " + p.getJoueur());
            System.out.println("-----");
            if (p.getJoueur().equals(j)) {
                exists = false;
                validationString = validationText;
                break;
            }
        }
        inputLabel.setText(validationText);
        return exists;
    }
    /*
    public static boolean dataVal idatorText(TextField inputTextField, Label inPutLabel) {
        boolean test1 = true;
        boolean test2 = true;
        boolean test3 = true;
        if (inputTextField.getText().isEmpty()) test1 = DataValidation.textFieldIsNull(inputTextField, inPutLabel, "A remplir");
        else if (inputTextField.getText().length()>1) {
            test2 = DataValidation.dataLength(inputTextField, inPutLabel, "Trop long");
        }
        else {
            test3 = DataValidation.textAlphabet(inputTextField, inPutLabel, "Des lettres ?");
        }
        return (test1&&test2&&test3);
    }

    public static boolean dataValidatorNum(TextField inputTextField, Label inPutLabel) {
        boolean test1 = true;
        boolean test2 = true;
        boolean test3 = true;
        if (inputTextField.getText().isEmpty()) test1 = DataValidation.textFieldIsNull(inputTextField, inPutLabel, "A remplir");
        else if (inputTextField.getText().length()>1) {
            test2 = DataValidation.dataLength(inputTextField, inPutLabel, "Trop long");
        }
        else {
            test3 = DataValidation.textNumeric(inputTextField, inPutLabel, "Des chiffres ?");
        }
        return (test1&&test2&&test3);
    }
    */
}
