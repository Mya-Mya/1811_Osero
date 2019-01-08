package player;

import board.BoardModel;
import board.BoardModelChangeListener;
import game.RockPushListener;
import game.Turn;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Player extends JFrame implements BoardModelChangeListener {
    protected boolean pushable;

    protected List <RockPushListener> pushListeners =new LinkedList<RockPushListener>();
    protected BoardModel model;
    protected  Turn myColor;

    public Player(BoardModel model,Turn myColor){
        this.model=model;
        this.myColor=myColor;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400,400));
        //model.addModelDataChangeListener(this);
        setVisible(true);
    }

    public void setPushable(boolean pushable){
        this.pushable=pushable;
    }

    public boolean isPushable() {return pushable; }

    public void addRockPushListener(RockPushListener l){
        pushListeners.add(l);
    }

    protected void fireRockPushed(int i, int j){
        for(RockPushListener l: pushListeners)l.rockPushed(myColor,i,j);
    }

    @Override
    public void boardModelDataChanged() {

    }
}
