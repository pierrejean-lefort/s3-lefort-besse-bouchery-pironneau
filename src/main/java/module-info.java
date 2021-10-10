open module ihm.bang {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    requires java.sql;

    exports fr.umontpellier.lpbr.s3;
    exports fr.umontpellier.lpbr.s3.views;
    exports fr.umontpellier.lpbr.s3.views.ourviews;
}
