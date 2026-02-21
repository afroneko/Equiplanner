package com.lisa.equiplanner;

import com.lisa.equiplanner.Controllers.*;
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
    private RiderController rc;
    private InstructorController ic;
    private LessonController lc;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        //Een keer globaal database initiëren om vaker te gebruiken
        Database db = new Database();

        //Verschillende controllers initialiseren om mee te kunnen geven aan de views
        ac = new AdminController(db);
        hc = new HorseController(db);
        rc = new RiderController(db);
        ic = new InstructorController(db);
        lc = new LessonController(db, ic, rc, hc);
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

    // --- Methode om verschillende overviews in te laden en hier de navbar aan mee te geven ---
    public void showOverview(String activeItem) {
        BorderPane layout = new BorderPane();

        // --- Navbar ---
        NavBar navBar = new NavBar(this);
        layout.setLeft(navBar.getNavBar(activeItem));

        // --- Conditionele pagina inhoud ---
        Pane content;
        switch (activeItem) {
            case "Paarden overzicht":
                content = new HorseOverview(hc).getContent();
                break;
            case "Ruiter overzicht":
                content = new RiderOverview(rc).getContent();
                break;
            case "Instructeur overzicht":
                content = new InstructorOverview(ic).getContent();
                break;
            case "Lessen overzicht":
                content = new LessonOverview(lc).getContent();
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