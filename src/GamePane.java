import javafx.application.Platform;

import javafx.event.Event;
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


import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


class GamePane extends BorderPane {
    private Label text;

    Button btnSettings;
    Button btnSkill;
    Mines mines;
    private  Label lb_time, lb_remain, lb_exp;
    int time;

    GridPane mineArea;
    Timer timer;



    GamePane(){

        VBox textArea;

        textArea = new VBox();
        text = new Label("Welcome!\n\n");

        Label text2 = new Label("Remain Mines:\n");
        Label text3 = new Label("Explored Grids:\n");
        Label text4 = new Label("Remain Time:\n");

        text.setStyle("-fx-font-size: 13px; -fx-alignment: baseline-center; -fx-text-fill: slategray");
        text2.setStyle("-fx-font-size: 13px; -fx-alignment: baseline-center; -fx-text-fill: slategray");
        text3.setStyle("-fx-font-size: 13px; -fx-alignment: baseline-center; -fx-text-fill: slategray");
        text4.setStyle("-fx-font-size: 13px; -fx-alignment: baseline-center; -fx-text-fill: slategray");



        lb_time = new Label();
        lb_time.setStyle("-fx-font-size: 26px; -fx-alignment: center; -fx-text-fill: brown");

        lb_remain = new Label();
        lb_remain.setStyle("-fx-font-size: 26px; -fx-alignment: center; -fx-text-fill: goldenrod");

        lb_exp = new Label();
        lb_exp.setStyle("-fx-font-size: 26px; -fx-alignment: center; -fx-text-fill: darkgreen");

        mines = new Mines();

        textArea.getChildren().addAll(text, text2, lb_remain, text3, lb_exp, text4, lb_time);

        textArea.setPrefSize(150,325);

        VBox systemArea = new VBox();
        btnSettings = new Button("Settings");
        btnSkill = new Button("Skill");
        btnSettings.setMinWidth(110);
        systemArea.getChildren().addAll(textArea, btnSettings, btnSkill);
        systemArea.setSpacing(20);
        //systemArea.setAlignment(Pos.BASELINE_LEFT);

        this.setRight(systemArea);

        Label toMap;

        toMap = new Label("Zoom in to select difficulty");
        toMap.setStyle("-fx-font-size: 20px; -fx-alignment: baseline-center; -fx-text-fill: slategray");

        this.setTop(toMap);

        this.setStyle("-fx-padding: 40px; -fx-alignment: baseline-center; -fx-spacing: 20px");





    }


    void init(){

        time = 90;

        mineArea = mines.init(100, 10);

        updateInfo();

        this.getChildren().removeAll(mineArea);
        this.setCenter(mineArea);
        //System.out.println(btnSkill.getText());

        btnSettings.setGraphic(new ImageView(new Image("/img/settings.png")));

        ImageView imgv = new ImageView();

        switch (btnSkill.getText()){
            case "Shield" :{
                imgv.setImage(new Image("/img/shield.png"));
                break;
            }
            case "Detector":{
                imgv.setImage(new Image("/img/detector.png"));
                text.setText("Welcome!\nDrag to use Detector\n\n");
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
        btnSkill.setMinWidth(110);



        if(btnSkill.getText().equals("Detector")){




            btnSkill.setOnDragDetected(event -> {
                Dragboard db = btnSkill.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();

                content.putImage(new Image("/img/detector.png"));
                db.setContent(content);

                event.consume();
            });

            btnSkill.setOnDragDone(Event::consume);

        }

        this.setOnZoom(event -> {

            if(event.getZoomFactor() < 1){
                try{
                    timer.cancel();

                }catch (Exception ignored){

                }
                Map map = new Map();
                map.init();
                Scene ms = new Scene(map, Game.width, Game.height);
                Game.stage.setScene(ms);
            }
        });
        //TODO time
        setTime();



    }




    void updateInfo(){

        lb_remain.setText(Integer.toString(mines.numMine - mines.flagNum));
        lb_exp.setText(Integer.toString(sum(mines.isClicked)));



        //System.out.println(mines.numMine+" "+mines.flagNum);
        if(mines.numMine - mines.flagNum == 0){
            int[] a = mines.pos;
            int[] b = mines.getFlagPos();

            Arrays.sort(a);
            Arrays.sort(b);

            for(int i = 0; i < mines.numMine; i++) {
                //System.out.println("a&b " + a[i] + " " + b[i]);

                if(a[i] != b[i]){
                    return;
                }
            }

            try{
                timer.cancel();

            }catch(Exception ignored){

            }

            Alert alt = new Alert(Alert.AlertType.INFORMATION);
            alt.setContentText("You win!");
            alt.showAndWait();
            Game.stage.setScene(Game.mScene);




        }

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
                    Platform.runLater(() -> lb_time.setText(Integer.toString(time)));
                    time--;

                    if(time == 0){

                        Platform.runLater(() -> {
                            if ( ! btnSkill.getText().equals("Timer") ||
                                    (btnSkill.getText().equals("Timer") && btnSkill.isDisable())) {
                                timer.cancel();

                                Alert alt = new Alert(Alert.AlertType.CONFIRMATION);
                                alt.setContentText("Time is up");

                                ButtonType btype = new ButtonType("Back to main menu");
                                alt.getButtonTypes().setAll(btype);

                                alt.showAndWait();
                                Game.stage.setScene(Game.mScene);


                            }else{
                                time += 30;
                                btnSkill.setDisable(true);
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
