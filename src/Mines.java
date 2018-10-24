
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import java.util.HashSet;
import java.util.Random;


class Mines extends Button {

    private Mines[][] btn;

    private int btnNum;
    private int size;
    int flagNum;
    int numMine;
    int[] pos; // mines index int
    int[][] isClicked;
    private int[][] isFlag;
    private int[][] isDetected;





    // Method: generate
    // Description: use random number to create specific number of mines
    // Input: number of mines
    // Output: An array contains the index
    private Object[] generate(int mineNum) {
        Object[] mines;

        HashSet<Integer> h = new HashSet<>();
        Random random = new Random();

        while (h.size() < mineNum) {

            h.add(random.nextInt(btnNum));

        }

        mines = h.toArray();

        return mines;

    }

    // Method: init
    // Description: initialize the mine area
    // Input: the number of grids
    // Output: a GridPane layout

    GridPane init(int numbers, int mineNum) {

        GridPane mineArea = new GridPane();

        btnNum = numbers;
        numMine = mineNum;

        flagNum = 0;

        pos = new int[mineNum];
        size = (int) Math.sqrt(numbers);
        //System.out.println("mineNumbers:" + mineNumbers);

        Object[] minesIdx;

        minesIdx = generate(mineNum);
        btn = new Mines[size][size];
        isClicked = new int[size][size];

        isFlag = new int[size][size];
        isDetected = new int[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){

                btn[i][j] = new Mines();
                btn[i][j].setId("");
                int btnsize = 450/size;
                btn[i][j].setMinHeight(btnsize);
                btn[i][j].setMinWidth(btnsize);
                btn[i][j].setPadding(new Insets(0));

                isClicked[i][j] = 0;
                isFlag[i][j] = 0;
                isDetected[i][j] = 0;
                mineArea.add(btn[i][j], i, j);

        }

        for (int i = 0; i < mineNum; i++) {
            pos[i] = Integer.parseInt(minesIdx[i].toString());
            int x = pos[i] / size;
            int y = pos[i] % size;
            //TODO for debug
            btn[x][y].setId("M");
        }

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {

                final int x = i;
                final int y = j;

                btn[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (isClicked[x][y] == 0) {
                        // left click
                        if (event.getButton() == MouseButton.PRIMARY) {
                            isClicked[x][y] = 1;
                            if (btn[x][y].getId().equals("")) {
                                btn[x][y].setStyle("-fx-background-color: #FFFFFF;");
                                calc(x, y);
                            }

                            if (btn[x][y].getId().equals("M")) {
                                btn[x][y].setStyle("-fx-background-color: #FF0000;");

                                if(Game.gPane.btnSkill.getText().equals("Shield") &&
                                        !Game.gPane.btnSkill.isDisable()) {
                                    Game.gPane.btnSkill.setDisable(true);
                                    isFlag[x][y] = 1;
                                    flagNum++;

//                                    Alert alt = new Alert(Alert.AlertType.INFORMATION);
//                                    alt.setContentText("Something happened but you are still alive");
//                                    alt.showAndWait();





                                }else{

                                    //TODO game over dialog
                                    Alert alt = new Alert(Alert.AlertType.CONFIRMATION);
                                    alt.setContentText("Game Over");
                                    Game.gPane.btnSkill.setDisable(false);

                                    try{
                                        Game.gPane.timer.cancel();
                                    }catch(Exception ignored){
                                        
                                    }

                                    ButtonType btype = new ButtonType("Back to main menu");
                                    alt.getButtonTypes().setAll(btype);

                                    alt.showAndWait();



                                    Game.stage.setScene(Game.mScene);



                                }


                            }

                        }
                        if (event.getButton() == MouseButton.SECONDARY) {
                            if (isFlag[x][y] == 0) {
                                btn[x][y].setStyle("-fx-background-color: #FFFF00;");
                                isFlag[x][y] = 1;
                                flagNum++;

                            } else {
                                btn[x][y].setStyle("-fx-background-color: #00EE99;");
                                isFlag[x][y] = 0;
                                flagNum--;

                            }
                        }
                    }

                    Game.gPane.updateInfo();


                });

                btn[x][y].setOnDragOver(event -> {
                    event.acceptTransferModes(TransferMode.MOVE);

                    event.consume();
                });


                btn[x][y].setOnDragDropped(event -> {

                    for(int i1 = -1; i1 < 2; i1++){
                        for (int j1 = -1; j1 < 2; j1++){
                            detect(x+ i1, y+ j1);
                        }
                    }

                    Game.gPane.updateInfo();
                    Game.gPane.btnSkill.setDisable(true);


                    event.consume();
                    event.setDropCompleted(true);

                    event.consume();

                });

                btn[x][y].setOnDragEntered(event -> {
                    if (event.getGestureSource() != btn[x][y]) {
                        event.acceptTransferModes(TransferMode.MOVE);
                        for (int i12 = -1; i12 < 2; i12++) {
                            for (int j12 = -1; j12 < 2; j12++) {
                                if (x + i12 < size && x + i12 >= 0 && y + j12 < size && y + j12 >= 0) {
                                    btn[x + i12][y + j12].setStyle("-fx-background-color: #AAAAAA;");
                                }
                            }
                        }

                    }

                    event.consume();
                });

                btn[x][y].setOnDragExited(event -> {
                    if (event.getGestureSource() != btn[x][y]) {
                        event.acceptTransferModes(TransferMode.MOVE);
                        for (int ii = -1; ii < 2; ii++) {
                            for (int jj = -1; jj < 2; jj++) {
                                if (x + ii < size && x + ii >= 0 && y + jj < size && y + jj >= 0) {
                                    if (isClicked[x + ii][y + jj] == 1) {
                                        btn[x + ii][y + jj].setStyle("-fx-background-color: #FFFFFF;");
                                    } else {
                                        btn[x + ii][y + jj].setStyle("-fx-background-color: #00EE99;");
                                    }

                                    if(isDetected[x+ ii][y+ jj] == -1) {
                                        btn[x + ii][y + jj].setStyle("-fx-background-color: #FF0000;");
                                        isFlag[x + ii][y + jj] = 1;
                                        flagNum = getFlagNum();
                                    }

                                }
                            }
                        }

                    }
                    Game.gPane.updateInfo();
                });
            }



        return mineArea;


    }


    int getFlagNum(){
        int count = 0;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(isFlag[i][j] == 1){
                    count++;
                }
            }
        }
        return count;
    }

    // Method: calc
    // Desc: if there are mines around,
    // calculate the number of adjacent mines.
    // else, reveal the masked grids
    // Input: the x, y of the grid clicked
    // Output: void
    private void calc(int x, int y) {

        if(x >= size || x<0 || y >= size || y < 0)
            return;

        int numMine8 = getMineNumber(x, y);


        if (numMine8 == 0 ){
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {

                    if (x + i >= size || x + i < 0 || y + j >= size || y + j < 0
                            || isClicked[x + i][y + j] == 1 || isFlag[x + i][y + j] == 1)
                        continue;

                    btn[x + i][y + j].setStyle("-fx-background-color: #FFFFFF;");
                    isClicked[x + i][y + j] = 1;

                    calc(x + i, y + j);

                }
            }

        }else {

            btn[x][y].setText(Integer.toString(numMine8));
        }
    }


    //Method: detect
    private void detect(int x, int y){


        if(x >= size || x<0 || y >= size || y < 0)
            return;

        if (btn[x][y].getId().equals("M")){
            isDetected[x][y] = -1;

        }else{
            int numMine8 = getMineNumber(x, y);
            if (numMine8 != -1){
                isDetected[x][y] = numMine8;
                isClicked[x][y] = 1;

                btn[x][y].setText(Integer.toString(isDetected[x][y]));

            }
        }


    }



    private int getMineNumber(int x, int y){
        int numMine8 = 0;

        if(x >= size || x<0 || y >= size || y < 0)
            return -1;


        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                //System.out.println("x:" + (x+i) + " y:" + (y+j));
                if (x + i >= size || x + i < 0 || y + j >= size || y + j < 0
                        || isClicked[x + i][y + j] == 1)
                    continue;

                if (btn[x + i][y + j].getId().equals("M")) {
                    numMine8++;
                }
            }
        }

        return numMine8;

    }

    int[] getFlagPos(){
        int[] idx = new int[numMine];
        int i = 0;

        while(i < numMine){
            for(int p = 0; p < size; p++){
                for(int q = 0; q < size; q++){
                    if(isFlag[p][q] == 1){
                        idx[i] = p * size + q;
                        i++;
                    }
                }
            }
        }

        return idx;
    }




}


