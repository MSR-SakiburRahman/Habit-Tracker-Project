package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Habit;
import java.util.List;

public class HistoryView {

    private Habit habit;

    public HistoryView(Habit habit) {
        this.habit = habit;
    }

    // Method to display the habit's completion history
    public void displayHistory() {
        Stage historyStage = new Stage();
        historyStage.setTitle("Completion History: " + habit.getName());

        // Layout for the history view
        VBox layout = new VBox(10);

        // Create a ListView to show the completion dates
        ListView<String> historyListView = new ListView<>();
        List<String> completionDates = habit.getCompletionDatesString(); // Assuming Habit has a method to get formatted completion dates
        historyListView.getItems().addAll(completionDates);

        // Add the history list to the layout
        layout.getChildren().add(historyListView);

        // Button to close the history window
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> historyStage.close());
        layout.getChildren().add(closeButton);

        // Scene setup
        Scene scene = new Scene(layout, 300, 200);
        historyStage.setScene(scene);
        historyStage.show();
    }
}
