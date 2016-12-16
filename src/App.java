 
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
 
public class App extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) throws Exception {
    	Label label = new Label("This is JavaFX!");
         ObservableList<String> data = FXCollections.observableArrayList(
                 "One","Two","Three"
         );
         ComboBox<String> combo = new ComboBox<String>(data);
         combo.setOnAction((ActionEvent)->{
             label.setText(combo.getValue());
         });
         BorderPane pane = new BorderPane();
         pane.setTop(label);
         pane.setCenter(combo);
         Scene scene = new Scene(pane, 320, 120);
         stage.setScene(scene);
         stage.show();
    }
 
}