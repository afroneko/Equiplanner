package com.lisa.equiplanner;

import com.lisa.equiplanner.Controllers.AdminController;
import com.lisa.equiplanner.Controllers.HorseController;
import com.lisa.equiplanner.Views.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Equiplanner extends Application {

    private Stage stage;
    private AdminController ac;
    private AuthenticationView authView;
    private HorseController hc;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        ac = new AdminController(new Database());
        hc = new HorseController(new Database());
        authView = new AuthenticationView(ac);

        showLogin();
    }

    // --- Shows login scene ---
    public void showLogin() {
        stage.setScene(authView.getLoginScene(this));
        stage.setTitle("Login");
        stage.show();
    }

    // --- Shows register scene ---
    public void showRegister() {
        stage.setScene(authView.getRegisterScene(this));
        stage.setTitle("Register");
        stage.show();
    }

    // --- Shows start menu scene ---
    public void showMenu() {
        MenuView mv = new MenuView(this);
        stage.setScene(mv.getScene());
        stage.setTitle("Startmenu");
        stage.show();
    }

    // --- Shows different overviews and passes the navbar ---
    public void showOverview(String activeItem) {
        BorderPane layout = new BorderPane();

        // --- Navbar ---
        NavBar navBar = new NavBar(this);
        layout.setLeft(navBar.getNavBar(activeItem));

        // --- Conditional overview ---
        Pane content;
        switch (activeItem) {
            case "Paarden overzicht":
                content = new HorseOverview(hc).getContent();
                break;
            case "Ruiter overzicht":
                content = new RiderOverview().getContent();
                break;
            case "Lessen overzicht":
                content = new LessonOverview().getContent();
                break;
            default:
                content = new Pane();
        }
        layout.setCenter(content);

        Scene scene = new Scene(layout, 800, 550);
        stage.setScene(scene);
        stage.setTitle(activeItem);
    }

    public static void main(String[] args) {
        launch();
    }
}