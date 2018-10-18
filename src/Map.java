import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Map extends BorderPane {

    Button[] lv;
    private int nodeNum = 5;
    HBox hbox;


    public void init(){

        lv = new Button[nodeNum];
        hbox = new HBox();

        for(int i = 0; i < nodeNum; i++){
            lv[i] = new Button("Dif: "+i);
            hbox.getChildren().add(lv[i]);

            int t = i;

            lv[i].setOnMouseClicked(event -> {
                Game.gPane.getChildren().removeAll(Game.gPane.mineArea);
                Game.gPane.mineArea = Game.gPane.mines.init((11+2*t)*(11+2*t));
                Game.gPane.getChildren().add(Game.gPane.mineArea);
                Game.gScene.getStylesheets().add("/css/gameScene.css");

                Game.stage.setScene(Game.gScene);

                //Game


            });

        }

        this.setCenter(hbox);


    }

}
