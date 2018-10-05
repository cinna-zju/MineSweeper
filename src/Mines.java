import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;




public class Mines extends Button {

    private Object[] minesIdx;
    private Mines[][] btn;

    int mineNum = 10;
    int btnNum = 100;
    int size;
    int flagNum = 0;
    int[] pos;
    int[][] isClicked;
    int[][] isFlag;
    int[][] isDetected;





    // Method: generate
    // Description: use random number to create specific number of mines
    // Input: number of mines
    // Output: An array contains the index
    public Object[] generate() {
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

    public GridPane init(int numbers) {

        GridPane mineArea = new GridPane();

        btnNum = numbers;
        mineNum = (int) Math.sqrt(numbers);
        pos = new int[mineNum];
        size = (int) Math.sqrt(numbers);
        //System.out.println("mineNumbers:" + mineNumbers);

        minesIdx = generate();
        btn = new Mines[size][size];
        isClicked = new int[size][size];

        isFlag = new int[size][size];
        isDetected = new int[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){

                btn[i][j] = new Mines();
                isClicked[i][j] = 0;
                isFlag[i][j] = 0;
                isDetected[i][j] = 0;
                mineArea.add(btn[i][j], i, j);

        }

        for (int i = 0; i < mineNum; i++) {
            pos[i] = Integer.parseInt(minesIdx[i].toString());
            int x = pos[i] / size;
            int y = pos[i] % size;
            btn[x][y].setText("M");
        }

        //为什么 Lambda 表达式(匿名类) 不能访问非 final  的局部变量呢？
        // 因为实例变量存在堆中，而局部变量是在栈上分配，Lambda 表达(匿名类) 会在另一个线程中执行。
        // 如果在线程中要直接访问一个局部变量，可能线程执行时该局部变量已经被销毁了，
        // 而 final 类型的局部变量在 Lambda 表达式(匿名类) 中其实是局部变量的一个拷贝。


        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {

                final int x = i;
                final int y = j;

                btn[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (isClicked[x][y] == 0) {
                        // left click
                        if (event.getButton() == MouseButton.PRIMARY) {
                            isClicked[x][y] = 1;
                            if (btn[x][y].getText().equals("")) {
                                btn[x][y].setStyle("-fx-background-color: #FFFFFF;");
                                calc(x, y);
                            }

                            if (btn[x][y].getText().equals("M")) {
                                btn[x][y].setStyle("-fx-background-color: #FF0000;");

                                if(Game.gPane.btnSkill.getText().equals("Shield") &&
                                        Game.gPane.btnSkill.isDisable() == false) {
                                    Game.gPane.btnSkill.setDisable(true);

                                    Alert alt = new Alert(Alert.AlertType.INFORMATION);
                                    alt.setContentText("Something happened but you are still alive");
                                    alt.showAndWait();





                                }else{

                                    //TODO game over dialog
                                    Alert alt = new Alert(Alert.AlertType.CONFIRMATION);
                                    alt.setContentText("Game Over");

                                    ButtonType btype = new ButtonType("Back to main menu");
                                    alt.getButtonTypes().setAll(btype);

                                    Optional<ButtonType> result = alt.showAndWait();
                                    if (result.get() == btype){
                                        Game.stage.setScene(Game.mScene);

                                    }

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

                btn[x][y].setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        event.acceptTransferModes(TransferMode.MOVE);

                        event.consume();
                    }
                });


                btn[x][y].setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {

                        for(int i = -1; i < 2; i++){
                            for (int j = -1; j < 2; j++){
                                detect(x+i, y+j);
                            }
                        }

                        Game.gPane.updateInfo();

                        event.consume();
                        event.setDropCompleted(true);

                        event.consume();

                    }
                });

                btn[x][y].setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        if (event.getGestureSource() != btn[x][y]) {
                            event.acceptTransferModes(TransferMode.MOVE);
                            for (int i = -1; i < 2; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (x + i < size && x + i >= 0 && y + j < size && y + j >= 0) {
                                        btn[x + i][y + j].setStyle("-fx-background-color: #AAAAAA;");
                                    }
                                }
                            }

                        }

                        event.consume();
                    }
                });

                btn[x][y].setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        if (event.getGestureSource() != btn[x][y]) {
                            event.acceptTransferModes(TransferMode.MOVE);
                            for (int i = -1; i < 2; i++) {
                                for (int j = -1; j < 2; j++) {
                                    if (x + i < size && x + i >= 0 && y + j < size && y + j >= 0) {
                                        if (isClicked[x + i][y + j] == 1) {
                                            btn[x + i][y + j].setStyle("-fx-background-color: #FFFFFF;");
                                        } else {
                                            btn[x + i][y + j].setStyle("-fx-background-color: #00EE99;");
                                        }

                                        if(isDetected[x+i][y+j] == -1) {
                                            btn[x + i][y + j].setStyle("-fx-background-color: #FF0000;");
                                        }

                                    }
                                }
                            }

                        }
                    }
                });
            }



        return mineArea;


    }

    // Method: calc
    // Desc: if there are mines around,
    // calculate the number of adjacent mines.
    // else, reveal the masked grids
    // Input: the x, y of the grid clicked
    // Output: void
    public void calc(int x, int y) {

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
    public void detect(int x, int y){


        if(x >= size || x<0 || y >= size || y < 0)
            return;

        if (btn[x][y].getText().equals("M")){
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



    public int getMineNumber(int x, int y){
        int numMine8 = 0;

        if(x >= size || x<0 || y >= size || y < 0)
            return -1;


        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                //System.out.println("x:" + (x+i) + " y:" + (y+j));
                if (x + i >= size || x + i < 0 || y + j >= size || y + j < 0
                        || isClicked[x + i][y + j] == 1)
                    continue;

                if (btn[x + i][y + j].getText().equals("M")) {
                    numMine8++;
                }
            }
        }

        return numMine8;

    }




}


