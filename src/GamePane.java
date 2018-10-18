import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;


public class GamePane extends BorderPane {
    public Label text;

    public Button btnSettings;
    public Button btnSkill;
    Mines mines;
    private VBox textArea;
    private VBox systemArea;
    private Label lb_time;
    private int time;

    public GridPane mineArea;
    public Timer timer;

    private boolean isMap = false;



    GamePane(){
        textArea = new VBox();
        text = new Label("Demo!");
        systemArea = new VBox();
        lb_time = new Label("Remain Time:\n");
        //TODO change font size in css?
        lb_time.setStyle("-fx-font-size: 20px;");
        mines = new Mines();

        textArea.getChildren().addAll(text, lb_time);
        textArea.setPrefSize(120,250);

        btnSettings = new Button("Settings");
        btnSkill = new Button("Skill");
        systemArea.getChildren().addAll(textArea, btnSettings, btnSkill);
        systemArea.setSpacing(20);

        this.setRight(systemArea);

        this.setStyle("-fx-padding: 40px; -fx-alignment: baseline-center");

        //TODO set timer
        //setTime();



    }


    public void init(){

        time = 10;

        mineArea = mines.init(100);

        updateInfo();

        this.getChildren().removeAll(mineArea);
        this.setCenter(mineArea);
        System.out.println(btnSkill.getText());

        btnSettings.setGraphic(new ImageView(new Image("/img/settings.png")));

        ImageView imgv = new ImageView();

        switch (btnSkill.getText()){
            case "Shield" :{
                imgv.setImage(new Image("/img/shield.png"));
                break;
            }
            case "Detector":{
                imgv.setImage(new Image("/img/detector.png"));
                break;
            }
            case "Timer":{
                imgv.setImage(new Image("/img/timer.png"));
                break;
            }
        }

        imgv.setFitWidth(32);
        imgv.setFitHeight(32);

        btnSkill.setGraphic(imgv);


        if(btnSkill.getText().equals("Detector")){

            btnSkill.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Dragboard db = btnSkill.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();

                    //db.setDragView(new Image("/img/detector.png"));
                    //content.putString();
                    content.putImage(new Image("/img/detector.png"));
                    db.setContent(content);

                    event.consume();
                }
            });

            btnSkill.setOnDragDone(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    event.consume();
                }
            });

        }







        this.addEventHandler(ZoomEvent.ANY, event -> {
//            if(isMap == false){
//                isMap = true;
//                Map map = new Map();
//                map.init();
//                Scene mapScene = new Scene(map, 600,450);
//                Game.stage.setScene(mapScene);
//
//            }else{
//
//                Game.stage.setScene(Game.gScene);
//
//            }

        });



    }




    public void updateInfo(){
        text.setText("Demo!\n"+"Remain: " + (mines.mineNum - mines.flagNum) +
                "\nExplored:" + sum(mines.isClicked));

    }

    private int sum(int[][] aa){
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
                                if ( ! btnSkill.getText().equals("Timer") ||
                                        (btnSkill.getText().equals("Timer") && btnSkill.isDisable())) {
                                    Alert alt = new Alert(Alert.AlertType.CONFIRMATION);
                                    alt.setContentText("Time is up");

                                    ButtonType btype = new ButtonType("Back to main menu");
                                    final boolean b = alt.getButtonTypes().setAll(btype);

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
