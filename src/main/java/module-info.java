open module ihm.echec {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    requires java.sql;
    requires java.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires net.bytebuddy;
    requires jdk.xml.dom;
    requires org.hibernate.validator;
    requires java.validation;

    exports fr.umontpellier.lpbr.s3;
    exports fr.umontpellier.lpbr.s3.views;
    exports fr.umontpellier.lpbr.s3.views.ourviews;
}
