
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

class Map extends BorderPane {

    private Label[] lv;


    void init(){

        int nodeNum = 5;
        lv = new Label[nodeNum];
        HBox hbox = new HBox();

        HBox lvArea = new HBox();

        for(int i = 0; i < nodeNum; i++){
            lv[i] = new Label();
            lv[i].setGraphic(new ImageView(new Image("/img/"+Integer.toString(i+1)+".png")));

            hbox.getChildren().add(lv[i]);

            int t = i;

            lv[i].setOnMouseEntered(event -> lv[t].setTranslateY(-3));

            lv[i].setOnMouseExited(event -> lv[t].setTranslateY(0));


            lv[i].setOnMouseClicked(event -> {

                Game.gPane.getChildren().removeAll(Game.gPane.mineArea);
                Game.gPane.mineArea = Game.gPane.mines.init((11+2*t)*(11+2*t), 10+5*t);
                Game.gPane.setCenter(Game.gPane.mineArea);
                Game.gPane.updateInfo();
                Game.gPane.btnSkill.setDisable(false);
                try{
                    Game.gPane.timer.cancel();

                }catch (Exception ignored){

                }

                //TODO time
                Game.gPane.time = 90 + 20 * t;
                Game.gPane.setTime();

                Game.gScene.getStylesheets().add("/css/gameScene.css");

                Game.stage.setScene(Game.gScene);


            });

        }

        hbox.setStyle("-fx-pref-height: 350px; -fx-spacing: 70px; -fx-alignment: baseline-center");
        lvArea.getChildren().add(hbox);
        this.setCenter(lvArea);


        Label title = new Label("Choose your Level");
        title.setStyle("-fx-font-size: 24px; -fx-alignment: top-left; -fx-text-fill: slategray; -fx-pref-height: 200px");

        this.setTop(title);

        this.setStyle("-fx-padding: 40px; -fx-spacing: 20px");

    }

}
