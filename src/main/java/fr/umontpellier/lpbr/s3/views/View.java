package fr.umontpellier.lpbr.s3.views;

import fr.umontpellier.lpbr.s3.EchecIHM;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class View {
    private static View view;
    private static EchecIHM ihm;
    private static Map<String, Scene> scenes;
    private static Class lastScene;

    private View() { scenes = new HashMap<String, Scene>(); }

    public static View getView() {
        if (view == null) view = new View();
        return view;
    }

    public View setIhm(EchecIHM ihm) {
        View.ihm = ihm;

        return this;
    }

    public static Class getLastScene() {
        return lastScene;
    }

    public static void setLastScene(Class lastScene) {
        View.lastScene = lastScene;
    }

    public static EchecIHM getIhm() {
        return ihm;
    }

    public View setScene(Class view, boolean forceReaload) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        if (ihm == null) return this;

        String fxmlPath = (String) view.getDeclaredField("fxmlPath").get(null);
        Scene scene = scenes.get(fxmlPath);
        if (scene != null && !forceReaload) {
            Stage stage = ihm.getPrimaryStage();
            stage.setScene(scene);
            System.out.println("Stage switched to " + view);
            return this;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));

        fxmlLoader.setController(view.getConstructor().newInstance());

        try {
            Node node = fxmlLoader.load();
            Pane root = new Pane(node);
            scene = new Scene(root);

            Stage stage = ihm.getPrimaryStage();
            stage.setScene(scene);
            stage.show();

            scenes.put(fxmlPath, scene);

            System.out.println("Scene created & stage set to " + fxmlPath);
        } catch(IOException exception) {
            throw new RuntimeException(exception);
        }

        return this;
    }

    public View setScene(Class view) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        return setScene(view, false);
    }
}
