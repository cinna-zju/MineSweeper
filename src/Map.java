import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Map extends GridPane {

    Button[] nodes;
    private int nodeNum = 5;
    int[] col = {1,2,3,4,5};
    int[] row = {1,2,3,4,5};

    public void init(){

        nodes = new Button[nodeNum];

        for(int i = 0; i < nodeNum; i++){
            nodes[i] = new Button();
            this.add(nodes[i], col[i], row[i]);
        }






    }

}
