package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;
import java.util.Random;

public class SmartHomeFX extends Application {

    private boolean lightOn = false;
    private boolean fanOn = false;
    private boolean tvOn = false;
    private int fanSpeed = 0;
    private final Random random = new Random();

    Label lightStatus = new Label("Light: OFF");
    Label fanStatus = new Label("Fan: OFF (Speed: 0)");
    Label tvStatus = new Label("TV: OFF");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Smart Home Control Panel");

        Button lightBtn = new Button("Toggle Light");
        lightBtn.setOnAction(e -> {
            lightOn = !lightOn;
            updateStatus();
        });

        Button fanBtn = new Button("Toggle Fan");
        Slider fanSlider = new Slider(0, 3, 0);
        fanSlider.setMajorTickUnit(1);
        fanSlider.setShowTickMarks(true);
        fanSlider.setShowTickLabels(true);
        fanSlider.setSnapToTicks(true);
        fanBtn.setOnAction(e -> {
            fanOn = !fanOn;
            if (!fanOn) fanSpeed = 0;
            updateStatus();
        });
        fanSlider.valueProperty().addListener((obs, o, n) -> {
            fanSpeed = n.intValue();
            fanOn = fanSpeed > 0;
            updateStatus();
        });

        Button tvBtn = new Button("Toggle TV");
        tvBtn.setOnAction(e -> {
            tvOn = !tvOn;
            updateStatus();
        });

        Button simulateBtn = new Button("Simulate Live Updates");
        simulateBtn.setOnAction(e -> simulateLiveUpdates());

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                new Label("Smart Home Control Panel"),
                lightBtn, lightStatus,
                new Separator(),
                fanBtn, fanStatus, new Label("Fan Speed:"), fanSlider,
                new Separator(),
                tvBtn, tvStatus,
                new Separator(),
                simulateBtn
        );

        Scene scene = new Scene(layout, 350, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateStatus() {
        lightStatus.setText("Light: " + (lightOn ? "ON" : "OFF"));
        fanStatus.setText("Fan: " + (fanOn ? "ON" : "OFF") + " (Speed: " + fanSpeed + ")");
        tvStatus.setText("TV: " + (tvOn ? "ON" : "OFF"));
    }

    private void simulateLiveUpdates() {
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> {
            lightOn = random.nextBoolean();
            tvOn = random.nextBoolean();
            fanSpeed = random.nextInt(4);
            fanOn = fanSpeed > 0;
            updateStatus();
        }));
        t.setCycleCount(5);
        t.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
