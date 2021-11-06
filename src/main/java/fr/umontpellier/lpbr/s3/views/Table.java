package fr.umontpellier.lpbr.s3.views;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public abstract class Table extends AnchorPane {
    private Label table;
    public Table() {
        super();
        table = new Label();
        table.setStyle("-fx-text-fill: orange");
    }

    public Label getTable() {
        return table;
    }
}
