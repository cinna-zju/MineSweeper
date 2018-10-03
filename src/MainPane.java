import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class MainPane extends StackPane {

    public SystemButton btnGo;
    public SystemButton btnExit;


    public void init(){

        btnGo = new SystemButton("GO!");
        btnExit = new SystemButton("EXIT");

        VBox btns = new VBox(btnGo, btnExit);
        btns.setSpacing(20);


        Label background = new Label();
        Label title = new Label();

        title.setGraphic(new ImageView(new Image("/img/title.png")));
        background.setGraphic(new ImageView(new Image("/img/back.png")));

        this.getChildren().addAll(background, title, btns);
        StackPane.setMargin(btns, new Insets(500,50,200,600));


    }



}
