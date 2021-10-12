open module ihm.echec {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    requires java.sql;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.persistence;
    requires java.naming;
    requires net.bytebuddy;
    requires dom4j;
    requires jdk.xml.dom;
//    requires org.hibernate.orm.jpamodelgen;

    exports fr.umontpellier.lpbr.s3;
    exports fr.umontpellier.lpbr.s3.views;
    exports fr.umontpellier.lpbr.s3.views.ourviews;
}
