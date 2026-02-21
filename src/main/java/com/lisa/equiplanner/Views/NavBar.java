package com.lisa.equiplanner.Views;

import com.lisa.equiplanner.Equiplanner;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class NavBar {

    private Equiplanner app;

    public NavBar(Equiplanner app) {
        this.app = app;
    }

    public Pane getNavBar(String activeItem) {
        FlowPane navBar = new FlowPane();
        navBar.setOrientation(javafx.geometry.Orientation.VERTICAL);
        navBar.setPrefSize(165, 550);
        navBar.setPadding(new Insets(30, 0, 0, 0));

        navBar.getChildren().addAll(
                generateNavItem("Paarden overzicht", activeItem.equals("Paarden overzicht")),
                generateNavItem("Ruiter overzicht", activeItem.equals("Ruiter overzicht")),
                generateNavItem("Lessen overzicht", activeItem.equals("Lessen overzicht"))
        );

        return navBar;
    }

    private Node generateNavItem(String name, boolean active) {
        Label label = new Label(name);
        label.setMinWidth(165);
        label.setPadding(new Insets(10));
        label.setStyle(active
                ? "-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: #B22424;"
                : "-fx-font-weight: normal; -fx-font-size: 16;");

//         Click handler: swap overview pages
        label.setOnMouseClicked(e -> app.showOverview(name));
        return label;
    }
}