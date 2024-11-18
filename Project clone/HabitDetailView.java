package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Habit;

/**
 * Displays detailed information about a specific habit.
 */
public class HabitDetailView {

    private final Habit habit;

    public HabitDetailView(Habit habit) {
        this.habit = habit;
    }

    /**
     * Shows a window with the details of the selected habit.
     */
    public void showDetails() {
        // Create a new stage for the detail view
        Stage detailStage = new Stage();
        detailStage.setTitle("Habit Details: " + habit.getName());

        // Layout for the details
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-spacing: 10;");

        // Labels for displaying habit details
        Label nameLabel = new Label("Name: " + habit.getName());
        Label typeLabel = new Label("Type: " + (habit.isMeasurable() ? "Measurable" : "Non-Measurable"));

        layout.getChildren().addAll(nameLabel, typeLabel);

        // Additional details for measurable habits
        if (habit.isMeasurable()) {
            Label targetLabel = new Label("Target Value: " + habit.getTargetValue());
            layout.getChildren().add(targetLabel);
        }

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> detailStage.close());
        layout.getChildren().add(closeButton);

        // Scene setup
        Scene scene = new Scene(layout, 350, 250);
        detailStage.setScene(scene);
        detailStage.show();
    }
}
