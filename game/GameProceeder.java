package game;

import board.BoardModel;
import board.RockKind;

public class GameProceeder implements RockPushListener {
    BoardModel model;
    ProceederAnnounceGettable announceTo;
    PutAndFlip brain;
    Turn nowTurn=Turn.WHITE;
    public GameProceeder(BoardModel model, ProceederAnnounceGettable proceederAnnounceGettable){
        this.model=model;
        this.announceTo=proceederAnnounceGettable;
        this.brain=new PutAndFlip();

        setTurn(nowTurn);
    }
    public Turn getNowTurn(){
        return nowTurn;
    }


    private void switchTurn(){
        setTurn(getNowTurn().getOpponent());
    }

    public void setTurn(Turn newTurn){
        this.nowTurn=newTurn;
        if(getNowTurn()==Turn.FINISHED){
            StringBuilder sb=new StringBuilder();
            sb.append("試合は終了しました。");
            sb.append("白");
            int white=model.numberOf(RockKind.WHITE);
            sb.append(white);
            sb.append("個、黒");
            int black=model.numberOf(RockKind.BLACK);
            sb.append(black);
            sb.append("個で");
            if(white>black)sb.append("白の勝ち！");
            if(black>white)sb.append("黒の勝ち！");
            if(white==black)sb.append("引き分け！");
            announceTo.announce(sb.toString());
            return;
        }

        if(model.isFull())setTurn(Turn.FINISHED);

        if(!isAvailableToPutAndFlip(getNowTurn())){
            if(!isAvailableToPutAndFlip(getNowTurn().getOpponent()) ){
                setTurn(Turn.FINISHED);
                return;
            }
            announceTo.announce(getNowTurn().toString()+"の子、申し訳ないが君はできないそうなので先に相手の子にやってもらうよ");
            switchTurn();
        }

        announceTo.announce(getNowTurn().toString()+"の番です");
        announceTo.turnChanged(getNowTurn());
    }

    private boolean isAvailableToPutAndFlip(Turn turn){
        for(int i=0;i<BoardModel.rowNum();i++){
            for(int j=0;j<BoardModel.columnNum();j++){
                if(brain.canPutAndFlipWhenPutTo(model,turn,i,j))return true;
            }
        }
        return false;
    }

    @Override
    public void rockPushed(Turn turn,int i, int j) {
        if(getNowTurn()==Turn.FINISHED){
            announceTo.announce("試合は終了しました");
            return;
        }
        if(turn==getNowTurn()) {
            if (brain.canPutAndFlipWhenPutTo(model, getNowTurn(), i, j)) {
                brain.putAndFlip(model, getNowTurn(), i, j);
                switchTurn();
            } else announceTo.announce(getNowTurn().toString() + "の人よ、そこには置けないよ");
        }else announceTo.announce("君は今はターンじゃないような");
    }
}
