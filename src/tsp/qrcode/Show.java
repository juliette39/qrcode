package tsp.qrcode;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import java.util.List;

import static tsp.qrcode.Coder.coder;
import static tsp.qrcode.qrCode.format;

public class Show extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static int pixel = 20;
    public static double cote = (format + 8)*pixel;

    public void start(Stage stage) {
        String message = "HELLO WORLD";

        qrCode code = coder(message);
        String path = "file:./res/";

        stage.setTitle("QR Code");
        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setScene(scene);

        Canvas canvas = new Canvas(cote, cote);
        List<Node> enfant = group.getChildren();
        enfant.add(canvas);
        stage.setResizable(false);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image image = new Image(path + "0.png", cote, cote, false, false);
        gc.drawImage(image, 0, 0);

        for (int i = 0; i < format; i++) {
            for (int j = 0; j < format; j++) {
                String name = code.get(i, j) + ".png";
                Image module = new Image(path + name, pixel, pixel, false, false);
                gc.drawImage(module, (j + 4)*pixel, (i + 4)*pixel);
            }
        }
        stage.show();
    }
}