package Main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


interface LambdaTry {
    void show();
}

/** This class creates the application! */
public class Main extends Application {

    /** This method starts the program and takes the user to the login screen.
     *  @param primaryStage Stage
     *  @throws IllegalStateException will throw exception if called more than once, caught and prints stacktrace
     *  @throws Exception Catches runtime exception and prints stacktrace for debugging issues. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
            primaryStage.setTitle("Greatest Thing Ever!");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Load Screen Error.");
            alert.showAndWait();
        }
    }

    /** This method is the first method that gets called when you run the program.
     *  @param args String[] */
    public static void main(String[] args) {
        LambdaTry obj;
        obj = () -> System.out.println("Hello");
        obj.show();

        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}