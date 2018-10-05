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
    static Scene mScene;
    Scene gScene;
    Scene sScene;
    Scene mapScene;

    static Stage stage;

    public void start(Stage pStage) throws Exception {


        pStage.setTitle("MineSweeper");
        stage = pStage;// a copy of

        mPane = new MainPane();
        mPane.init();
        mScene = new Scene(mPane, 800, 600);
        mScene.getStylesheets().add("/css/mainScene.css");

        sPane = new SkillPane();
        sPane.init();
        sScene = new Scene(sPane, 800, 600);
        sScene.getStylesheets().add("/css/skillScene.css");



        gPane = new GamePane();
        gScene = new Scene(gPane, 800, 600);



        map = new Map();
        map.init();
        mapScene = new Scene(map, 800, 600);





        // the first scene of this game is main scene
        // contains two buttons, Go and Exit


        //TODO change first scene here
        stage.setScene(mScene);
        //pStage.setScene(mapScene);
        stage.show();


        // go to the skill scene
        mPane.btnGo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            stage.setScene(sScene);

        });

        // exit the game
        mPane.btnExit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            stage.close();
        });

        // the user can choose 3 kinds of skills.
        sPane.vbx1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gPane.init();
            gPane.btnSkill.setText("Shield");
            stage.setScene(gScene);
            gScene.getStylesheets().add("/css/gameScene.css");


        });

        sPane.vbx2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gPane.init();
            gPane.btnSkill.setText("Detector");
            pStage.setScene(gScene);
            gScene.getStylesheets().add("/css/gameScene.css");


        });

        sPane.vbx3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            gPane.init();
            gPane.btnSkill.setText("Timer");
            stage.setScene(gScene);
            gScene.getStylesheets().add("/css/gameScene.css");


        });


    }


}


