package fr.umontpellier.lpbr.s3;

import fr.umontpellier.lpbr.s3.views.View;
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

        if (inputTextField.getLength()>requiredLength) {
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

        if (!inputTextField.getText().matches("[a-zA-Z]+")) {
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
}
