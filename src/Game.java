import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Game extends Application {

    // layout of the game scene
    static GamePane gPane;

    // layout of the skill scene
    private SkillPane sPane;

    // declare main scene, game scene, skill scene
    static Scene mScene;
    static Scene gScene;
    private Scene sScene;

    static int width = 700;
    static int height = 550;

    static Stage stage;

    public void start(Stage pStage) {


        pStage.setTitle("MineSweeper");
        stage = pStage;// a copy of

        // layout of the main scene
        MainPane mPane = new MainPane();
        mPane.init();
        mScene = new Scene(mPane, width, height);

        sPane = new SkillPane();
        sPane.init();
        sScene = new Scene(sPane, width, height);
        sScene.getStylesheets().add("/css/skillScene.css");



        gPane = new GamePane();
        gScene = new Scene(gPane, width, height);




        pStage.setOnCloseRequest(event -> {
            try{
                gPane.timer.cancel();
            }catch (Exception ignored){

            }
        });

        // the first scene of this game is main scene
        // contains two buttons, Go and Exit

        //TODO change first scene here
        stage.setScene(mScene);
        //pStage.setScene(mapScene);
        stage.show();


        // go to the skill scene
        mPane.btnGo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> stage.setScene(sScene));

        // exit the game
//        mPane.btnExit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            stage.close();
//        });

        // the user can choose 3 kinds of skills.

        for(int i = 0; i < 3; i++){
            int t = i;
            sPane.vbx[t].setOnMouseClicked(event -> {
                gPane.btnSkill.setText(sPane.lb[t].getText());

                gPane.init();

                pStage.setScene(gScene);
                gScene.getStylesheets().add("/css/gameScene.css");

            });
        }

        gPane.btnSettings.setOnMouseClicked(event -> {
            Map map = new Map();
            map.init();
            Scene ms = new Scene(map, width, height);
            Game.stage.setScene(ms);

        });



    }

}


