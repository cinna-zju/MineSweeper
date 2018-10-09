import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.swing.text.AbstractDocument;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class GamePane extends GridPane{
    public Label text;

    private SystemButton btnSettings;
    public SystemButton btnSkill;
    Mines mines;
    private VBox textArea;
    private VBox systemArea;
    private Label lb_time;
    private int time;

    private GridPane mineArea;
    public Timer timer;


    GamePane(){
        textArea = new VBox();
        text = new Label("Demo!");
        systemArea = new VBox();
        lb_time = new Label("Remain Time:\n");
        //TODO change font size in css?
        lb_time.setStyle("-fx-font-size: 20px;");
        mines = new Mines();

        textArea.getChildren().add(text);
        textArea.setPrefSize(120,250);

        btnSettings = new SystemButton("Settings");
        btnSkill = new SystemButton("Skill");
        systemArea.getChildren().addAll(btnSettings, btnSkill);
        systemArea.setSpacing(20);


        this.add(lb_time, 1, 0, 1, 1);
        this.add(textArea, 1,1, 1, 1);
        this.add(systemArea,1,2, 1, 1);

        setTime();



    }


    public void init(){

        time = 10;



        mineArea = mines.init(100);

        updateInfo();

        this.getChildren().removeAll(mineArea);
        this.add(mineArea, 0, 0, 1, 3);

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
                //TODO debug
                //btnSkill.setDisable(true);
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

    public void setTime(){
        try{

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            lb_time.setText("Remain Time:\n" + time);

                        }
                    });
                    time--;

                    if(time == 0){
                        //this.cancel();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (btnSkill.getText().equals("Timer") == false ||
                                        (btnSkill.getText().equals("Timer") && btnSkill.isDisable() == true)) {
                                    Alert alt = new Alert(Alert.AlertType.CONFIRMATION);
                                    alt.setContentText("Time is up");

                                    ButtonType btype = new ButtonType("Back to main menu");
                                    alt.getButtonTypes().setAll(btype);

                                    Optional<ButtonType> result = alt.showAndWait();
                                    if (result.get() == btype) {
                                        Game.stage.setScene(Game.mScene);

                                    }
                                }else{
                                    time += 10;
                                    btnSkill.setDisable(true);
                                }
                            }
                        });

                    }

                }
            },0, 1000);


        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
