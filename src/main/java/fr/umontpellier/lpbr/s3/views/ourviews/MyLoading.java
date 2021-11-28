package fr.umontpellier.lpbr.s3.views.ourviews;

import fr.umontpellier.lpbr.s3.EchecIHM;
import fr.umontpellier.lpbr.s3.views.Loading;
import fr.umontpellier.lpbr.s3.views.View;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLoading extends Loading {
    public static String fxmlPath = "/fxml/loading.fxml";
    @FXML private ProgressBar progressBar;
    @FXML private ProgressIndicator progressIndicator;
    private Thread thread;

    private ChangeListener progressChange = new ChangeListener() {
        @Override
        public void changed(ObservableValue observableValue, Object o, Object t1) {
            double p = (double) t1;
            progressIndicator.setProgress(p);
            progressBar.setProgress(p);

            if (p >= 1 && thread != null && thread.isAlive()) {
                try {
                    View.getView().setScene(MyResultat.class, true);
                } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (p == -0.01) {
                int round = View.getIhm().getSelectedTournoi().gotCurrentRound();

                if (round == 0) round = 1;

                int finalRound = round;
                thread = new Thread(() -> {
                    System.out.println("New task for round " + finalRound);
                    EchecIHM.taskSetProgress(1);
                    View.getIhm().getSelectedTournoi().gotRepartition(finalRound);
                });
                thread.start();
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        View.getIhm().getProgress().addListener(progressChange);

        System.out.println("initied");
        View.getIhm().setProgress(0.0);
        View.getIhm().setProgress(-1.0); // faire s'executer le listener
    }
}
