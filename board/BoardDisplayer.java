package board;

import javax.swing.*;
import java.awt.*;

public class BoardDisplayer extends JPanel implements BoardModelChangeListener {
    private BoardModel model;

    private RockDisplayer[][] rockDisplayers =new RockDisplayer[BoardModel.rowNum()][BoardModel.columnNum()];

    public BoardDisplayer(BoardModel model){
        super();
        this.model=model;
        setOpaque(true);
        setBackground(new Color(28, 147, 49));

        LayoutManager layout=new GridLayout(BoardModel.rowNum(),BoardModel.columnNum());
        ((GridLayout) layout).setVgap(2);
        ((GridLayout) layout).setHgap(2);
        setLayout(layout);

        for(int i = 0; i<model.rowNum(); i++){
            for(int j = 0; j<model.columnNum(); j++){
                RockDisplayer r=new RockDisplayer();
                rockDisplayers[i][j]=r;
                add(r);
            }
        }
        loadBoard();

    }

    public void loadBoard(){
        for(int i = 0; i<model.rowNum(); i++){
            for(int j = 0; j<model.columnNum(); j++){
                rockDisplayers[i][j].setKind(model.get(i,j));
            }
        }
        updateUI();
    }

    @Override
    public void boardModelDataChanged() {
        loadBoard();
    }

}