package player;

import board.BoardModel;
import game.PutAndFlip;
import game.Turn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static java.util.Calendar.getInstance;

/**
 * パスの概念がない
 * 限りある思考時間の中で、いかに毎回の思考で思考する部分をランダムに決められるか
 */
public class AI1 extends Human {
    PutAndFlip brain=new PutAndFlip();
    public AI1(BoardModel model, Turn myColor) {
        super(model, myColor);
    }


    @Override
    public void setPushable(boolean pushable) {
        super.setPushable(pushable);
        if(pushable){
            //考える処理は試合の後半になってから
            if(true){//model.numberOf(RockKind.BLACK)+model.numberOf(RockKind.WHITE)>50){
                mainPanel=new JPanel();
                mainPanel.add(new JLabel("AI1が考えています"));
                repaint();
                thinkWhichToPut();
            }
        }
    }

    private void thinkWhichToPut(){
        //現状で置くことのできる候補をリストアップ
        List<AI1Candidates> candidates=new ArrayList<AI1Candidates>();
        for(int i=0;i<BoardModel.rowNum();i++){
            for(int j=0;j<BoardModel.columnNum();j++){
                if(brain.canPutAndFlipWhenPutTo(model,myColor,i,j))
                candidates.add(new AI1Candidates(i,j));
            }
        }

        //バックトラック法を用いて勝つor負けるの統計を候補ごとにとる
        thinkingAvailable =true;
        new javax.swing.Timer(10 * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thinkingAvailable =false;
            }
        }).start();

        Random random=new Random(Calendar.getInstance().getTimeInMillis());
        candidates.sort(new Comparator<AI1Candidates>() {
            @Override
            public int compare(AI1Candidates o1, AI1Candidates o2) {
                return random.nextBoolean()?-1:1;
            }
        });

        for(AI1Candidates c:candidates){
            BoardModel exceptingModel=new BoardModel(model);
            exceptingModel.set(myColor.toRockKind(),c.i,c.j);
            searhing(exceptingModel,myColor.getOpponent(),c,0);
        }

        //勝率の最も高い候補を選ぶ
        AI1Candidates topWinningRate=candidates.get(0);
        for(AI1Candidates c:candidates){
            if(topWinningRate.getWinningRate()<c.getWinningRate())
                topWinningRate=c;
        }

        fireRockPushed(topWinningRate.i, topWinningRate.j);
    }
    private  boolean thinkingAvailable =true;
    private Random random;

    private void searhing(BoardModel model,Turn nowTurn,AI1Candidates candidate,int nest){
        if (nest<5&& thinkingAvailable) {

            for (int i = 0; i < BoardModel.rowNum(); i++) {
                for (int j = 0; j < BoardModel.columnNum(); j++) {
                    //このマスが打てる時
                    if (brain.canPutAndFlipWhenPutTo(model, nowTurn, i, j)) {
                        //そのマスを打った世界線を作成
                        System.out.println(Integer.toString(nest) + " " + nowTurn.toString() + " " + Integer.toString(i) + ", " + Integer.toString(j));
                        model.set(nowTurn.toRockKind(), i, j);
                        searhing(new BoardModel(model), nowTurn.getOpponent(), candidate, nest + 1);
                    }

                }
            }
        }

        //ここまで来れば打てる全てのマスが埋まったと同等

            if(model.numberOf(myColor.toRockKind())>model.numberOf(myColor.getOpponent().toRockKind())) {
                    candidate.win++;
            }
            if(model.numberOf(myColor.toRockKind())<model.numberOf(myColor.getOpponent().toRockKind())) {
                    candidate.lose++;
            }

    }


}

class AI1Candidates{
    public int i;
    public int j;
    public int lose;
    public int win;
    public  AI1Candidates(int i,int j){
        this.i=i;
        this.j=j;
        this.win=0;
        this.lose=0;
    }
    public double getWinningRate(){
        return (double)win/(double)(win+lose);
    }
}