package player;

import board.BoardModel;
import game.Turn;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Human extends Player implements  ActionListener {
    protected RockButton[][] rockButtons =new RockButton[BoardModel.rowNum()][BoardModel.columnNum()];
    protected JPanel mainPanel =new JPanel();

    public Human(BoardModel model, Turn myColor){
        super(model,myColor);

        setBackground(new Color(147, 71, 41));
        setLayout(new BorderLayout());

        JLabel lMyColor=new JLabel(myColor.toString());
        lMyColor.setFont(new Font("メイリオ",Font.BOLD,25));
        getContentPane().add(lMyColor,BorderLayout.NORTH);

        LayoutManager layout=new GridLayout(BoardModel.rowNum(),BoardModel.columnNum());
        ((GridLayout) layout).setVgap(2);
        ((GridLayout) layout).setHgap(2);
        mainPanel.setLayout(layout);

        for(int i = 0; i<model.rowNum(); i++){
            for(int j = 0; j<model.columnNum(); j++){
                RockButton r=new RockButton();
                r.addActionListener(this);
                rockButtons[i][j]=r;
                mainPanel.add(r);
            }
        }
        loadBoard();
        getContentPane().add(mainPanel,BorderLayout.CENTER);
        pack();
    }

    public void loadBoard(){
        for(int i = 0; i<model.rowNum(); i++){
            for(int j = 0; j<model.columnNum(); j++){
                rockButtons[i][j].setKind(model.get(i,j));
            }
        }
    }

    @Override
    public void boardModelDataChanged() {
        loadBoard();
        mainPanel.updateUI();
        mainPanel.paintImmediately(new Rectangle(0,0,mainPanel.getWidth(),mainPanel.getHeight()));
    }

    @Override
    public void setPushable(boolean b){
        super.setPushable(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i<model.rowNum(); i++){
            for(int j = 0; j<model.columnNum(); j++){
                if(e.getSource()== rockButtons[i][j]){
                    fireRockPushed(i,j);
                }
            }
        }
    }


}