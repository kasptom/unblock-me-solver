import controller.GameBoardController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.GameBoardView;
import java.io.IOException;
/**
 * Created by throzendar on 16/04/16.
 */
public class App extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Unblock Me Solver");
        initRootLayout();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void initRootLayout() throws Exception {
        try {
            // get layout from view
            GameBoardView gameBoardView = new GameBoardView();
            BorderPane mainLayout = gameBoardView.getMainLayout();
            // add layout to a scene and show them all
            Scene scene = new Scene(mainLayout, gameBoardView.getSceneWidth(), gameBoardView.getSceneHeight());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // don't do this in common apps
            e.printStackTrace();
        }
    }

}
