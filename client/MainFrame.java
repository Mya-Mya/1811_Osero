package client;

import board.BoardModel;
import board.BoardDisplayer;
import board.BoardModelChangeListener;
import game.GameProceeder;
import game.ProceederAnnounceGettable;
import game.Turn;
import player.AI1;
import player.Human;
import player.Player;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
        implements   ProceederAnnounceGettable {

    JLabel announcer;
    Player white;
    Player black;
    MainFrame(String version){
        super(version);
        setPreferredSize(new Dimension(500,500));

        announcer=new JLabel("こんにちは");
        add(announcer,BorderLayout.SOUTH);

        model=new BoardModel();// Model

        white=new AI1(model,Turn.WHITE); // View, View -> Model
        black=new Human(model,Turn.BLACK); // View, View -> Model
        JPanel displayer=new BoardDisplayer(model); // View

        GameProceeder proceeder=new GameProceeder(model,this); // Controller, Controller -> View

        white.addRockPushListener(proceeder); // View -> Controller
        black.addRockPushListener(proceeder); // View -> Controller
        model.addModelDataChangeListener(white); // Model -> View
        model.addModelDataChangeListener(black); // Model -> View
        model.addModelDataChangeListener((BoardModelChangeListener) displayer); // Model -> View

        add(displayer,BorderLayout.CENTER);

        JLabel lTitle=new JLabel("Osero "+version);
            lTitle.setHorizontalAlignment(SwingConstants.CENTER);
            lTitle.setFont(new Font("メイリオ",Font.PLAIN,22));
            add(lTitle, BorderLayout.NORTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private BoardModel model;

    @Override
    public void announce(String announce) {
        StringBuilder sb=new StringBuilder();
        sb.append(announce);
        sb.append(" | ");
        sb.append(announcer.getText());
        announcer.setText(sb.toString());
        getContentPane().repaint();
        update(getGraphics());
    }

    @Override
    public void turnChanged(Turn turn) {
        switch (turn){
            case WHITE:
                white.setPushable(true);
                black.setPushable(false);
                break;
            case BLACK:
                black.setPushable(true);
                white.setPushable(false);
                break;
        }
    }
}
