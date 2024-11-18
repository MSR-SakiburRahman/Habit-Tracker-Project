package view;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Habit;

import java.time.LocalDate;
import java.util.List;

/**
 * Displays analytics for a specific habit using a bar chart.
 */
public class AnalyticsView {

    private final Habit habit;

    public AnalyticsView(Habit habit) {
        this.habit = habit;
    }

    /**
     * Show analytics for the selected habit.
     */
    public void showAnalytics() {
        // Create a new stage for analytics
        Stage analyticsStage = new Stage();
        analyticsStage.setTitle("Analytics for: " + habit.getName());

        // VBox as the root layout
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Title for the analytics
        Label titleLabel = new Label("Habit Analytics: " + habit.getName());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10;");

        // Bar chart for habit progress
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Completion Status");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Habit Completion Progress");

        // Populate the bar chart with data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Completion Status");

        // Fetch completion dates and populate the chart
        List<LocalDate> completionDates = habit.getCompletionDates();
        for (LocalDate date : completionDates) {
            String formattedDate = date.toString();
            series.getData().add(new XYChart.Data<>(formattedDate, 1)); // Mark completed as 1
        }

        barChart.getData().add(series);

        // Add all elements to the root layout
        root.getChildren().addAll(titleLabel, barChart);

        // Scene setup
        Scene scene = new Scene(root, 800, 600);
        analyticsStage.setScene(scene);
        analyticsStage.show();
    }
}
