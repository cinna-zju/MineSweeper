import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class MainPane extends StackPane {

    public Label btnGo;


    public void init(){

        btnGo = new Label("Play");

        btnGo.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 24px");



        Label background = new Label();
        Label title = new Label();

        ImageView mainTitle = new ImageView(new Image("/img/title.png"));
        mainTitle.setFitHeight(240);
        mainTitle.setFitWidth(640);
        title.setGraphic(mainTitle);

        background.setGraphic(new ImageView(new Image("/img/back.png")));

        btnGo.setGraphic(new ImageView(new Image("/img/play.png")));
        //btnExit.setGraphic(new ImageView(new Image("/img/exit.png")));



        this.getChildren().addAll(background, title, btnGo);
        StackPane.setMargin(btnGo, new Insets(300,0,100,350));


    }



}
