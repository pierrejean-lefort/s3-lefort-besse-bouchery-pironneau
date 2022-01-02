open module ihm.echec {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    requires java.sql;
    requires java.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires net.bytebuddy;
    requires itextpdf;

    exports fr.umontpellier.lpbr.s3;
    exports fr.umontpellier.lpbr.s3.views;
    exports fr.umontpellier.lpbr.s3.views.ourviews;
    exports fr.umontpellier.lpbr.s3.SystemTournoi;
    exports fr.umontpellier.lpbr.s3.Methode;
}
