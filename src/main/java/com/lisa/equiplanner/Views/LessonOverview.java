package com.lisa.equiplanner.Views;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class LessonOverview {
    public Pane getContent() {
        Pane content = new Pane();
        Label label = new Label("Ruiter overzicht");
        label.setLayoutX(20);
        label.setLayoutY(50);
        content.getChildren().add(label);
        return content;
    }
}
