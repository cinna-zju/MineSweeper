import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.swing.text.AbstractDocument;


public class GamePane extends GridPane{
    public Label text;
    private ProgressBar remainTime;
    private SystemButton btnSettings;
    public SystemButton btnSkill;
    Mines mines;


    public void init(){

        VBox textArea = new VBox();
        text = new Label("Demo!");
        textArea.getChildren().add(text);
        textArea.setPrefSize(120,250);

        VBox systemArea = new VBox();
        systemArea.setSpacing(20);


        HBox timeArea = new HBox();
        Label lb_time = new Label("time");



        remainTime = new ProgressBar();
        IntegerProperty seconds = new SimpleIntegerProperty();
        remainTime.progressProperty().bind(seconds.divide(60.0));
        Timeline tl = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(remainTime.progressProperty(), 0)),
                new KeyFrame(Duration.minutes(1), e-> {

                }, new KeyValue(seconds, 60))
        );

        timeArea.getChildren().addAll(lb_time, remainTime);

        tl.setCycleCount(Animation.INDEFINITE);
        //tl.play();




        mines = new Mines();

        GridPane mineArea = mines.init(100);

        btnSettings = new SystemButton("Settings");
        btnSkill = new SystemButton("Skill");
        systemArea.getChildren().addAll(btnSettings, btnSkill);

        this.add(timeArea, 1, 0, 1, 1);
        this.add(mineArea, 0, 0, 1, 3);
        this.add(textArea, 1,1, 1, 1);
        this.add(systemArea,1,2, 1, 1);


        btnSkill.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = btnSkill.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(btnSkill.getText());
                db.setContent(content);
                event.consume();
            }
        });

        btnSkill.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                btnSkill.setDisable(true);
                event.consume();

            }
        });




    }


    public void updateInfo(){
        text.setText("Demo!\n"+"Remain: " + (mines.mineNum - mines.flagNum) +
                "\nExplored:" + sum(mines.isClicked));

    }

    public int sum(int[][] aa){
        int aaSum = 0;
        for(int i = 0; i < aa.length; i++) {
            for (int j = 0; j < aa[i].length; j++) {
                aaSum += aa[i][j];
            }
        }
            return aaSum;

    }

}
