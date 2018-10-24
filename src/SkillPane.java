import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


class SkillPane extends BorderPane {
    VBox[] vbx;
    private HBox hbox;
    Label[] lb;
    private Label[] imgLb, textLb;

    private Label title;


    SkillPane(){
        vbx = new VBox[3];
        lb = new Label[3];
        imgLb = new Label[3];
        textLb = new Label[3];

        hbox = new HBox();
        title = new Label("Choose Your Equipment");
        title.setStyle("-fx-font-size: 24px; -fx-alignment: baseline-center; -fx-text-fill: slategray");
    }

    void init(){

        String[] equip = {"Shield", "Detector", "Timer"};
        String[] path = {"/img/shield.png", "/img/detector.png", "/img/timer.png"};
        String[] info = {"God bless you", "Tell you the truth", "Time is money"};


        for(int i = 0; i < 3; i++){
            lb[i] = new Label(equip[i]);
            lb[i].setStyle("-fx-font-size: 18px; -fx-text-fill: slategray");
            imgLb[i] = new Label();
            imgLb[i].setGraphic(new ImageView(new Image(path[i])));
            textLb[i] = new Label(info[i]);
            textLb[i].setStyle("-fx-font-size: 18px; -fx-text-fill: slategray");

            vbx[i] = new VBox(lb[i], imgLb[i], textLb[i]);
            vbx[i].setStyle("-fx-background-color:#00EE99; -fx-pref-width:180px; -fx-pref-height: 300px;");

            int t = i;
            vbx[i].setOnMouseEntered(event -> vbx[t].setStyle("-fx-background-color: #FFFF00; -fx-pref-width:180px; -fx-pref-height: 300px;"));

            vbx[i].setOnMouseExited(event -> vbx[t].setStyle("-fx-background-color:#00EE99; -fx-pref-width:180px; -fx-pref-height: 300px;"));

            hbox.getChildren().add(vbx[i]);

        }

        hbox.setStyle("-fx-spacing: 40px");


        this.setTop(title);

        this.setCenter(hbox);




        this.setStyle("-fx-spacing: 40px; -fx-alignment: baseline-center; -fx-padding: 40px");

    }

}
