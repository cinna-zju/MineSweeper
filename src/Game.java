import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Game extends Application {

    // layout of the main scene
    MainPane mPane;

    // layout of the game scene
    static GamePane gPane;

    // layout of the skill scene
    SkillPane sPane;

    //
    Map map;

    // declare main scene, game scene, skill scene
    Scene mScene;
    Scene gScene;
    Scene sScene;
    Scene mapScene;

    public void start(Stage pStage) throws Exception {

        pStage.setTitle("MineSweeper");

        mPane = new MainPane();
        mPane.init();
        mScene = new Scene(mPane, 800, 600);
        mScene.getStylesheets().add("/css/mainScene.css");


        gPane = new GamePane();
        gPane.init();
        gScene = new Scene(gPane, 800, 600);
        gScene.getStylesheets().add("/css/gameScene.css");

        sPane = new SkillPane();
        sPane.init();
        sScene = new Scene(sPane, 800, 600);
        sScene.getStylesheets().add("/css/skillScene.css");

        map = new Map();
        map.init();
        mapScene = new Scene(map, 800, 600);





        // the first scene of this game is main scene
        // contains two buttons, Go and Exit


        //TODO change first scene here
        pStage.setScene(mScene);
        //pStage.setScene(mapScene);
        pStage.show();


        // go to the skill scene
        mPane.btnGo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            pStage.setScene(sScene);

        });

        // exit the game
        mPane.btnExit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            pStage.close();
        });

        // the user can choose 3 kinds of skills.
        sPane.vbx1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gPane.btnSkill.setText("Shield");
            pStage.setScene(gScene);

        });

        sPane.vbx2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gPane.btnSkill.setText("Detector");
            pStage.setScene(gScene);

        });

        sPane.vbx3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gPane.btnSkill.setText("Timer");
            pStage.setScene(gScene);

        });


    }

}


