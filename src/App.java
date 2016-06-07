import controller.GameBoardConfig;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.GameBoardView;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by throzendar on 16/04/16.
 */
public class App extends Application {
    private Stage primaryStage;
    private static String configFileName;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Unblock Me Solver");
        initRootLayout();
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        if(args.length > 0)
            configFileName = args[0].trim();
        else{
            System.out.println("USAGE: App <config file name in res/configs/ directory>");
            System.exit(1);
        }
        launch(args);
    }

    private void initRootLayout() throws Exception {
        try {
            GameBoardConfig.getInstance("res/configs/" + configFileName);
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
