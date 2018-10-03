import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SkillPane extends HBox{

    VBox vbx1 = new VBox();
    VBox vbx2 = new VBox();
    VBox vbx3 = new VBox();


    public void init(){

        Label lb1 = new Label("Shield");
        Label lb2 = new Label("Detector");
        Label lb3 = new Label("Timer");

        Label imgLb1 = new Label();
        Label imgLb2 = new Label();
        Label imgLb3 = new Label();

        imgLb1.setGraphic(new ImageView(new Image("/img/shield.png")));
        imgLb2.setGraphic(new ImageView(new Image("/img/detector.png")));
        imgLb3.setGraphic(new ImageView(new Image("/img/timer.png")));

        Label txtLb1 = new Label("God bless you");
        Label txtLb2 = new Label("Tell you the truth");
        Label txtLb3 = new Label("Time is money");

        vbx1.getChildren().addAll(lb1, imgLb1, txtLb1);
        vbx2.getChildren().addAll(lb2, imgLb2, txtLb2);
        vbx3.getChildren().addAll(lb3, imgLb3, txtLb3);


        this.getChildren().addAll(vbx1, vbx2, vbx3);


    }




}
