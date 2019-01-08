package game;

import board.BoardModel;
import board.RockKind;

public class PutAndFlip {
    public PutAndFlip(){
    }
    public boolean canPutAndFlipWhenPutTo(BoardModel model, Turn turn, int i, int j){

        RockKind theRock=model.get(i,j);
        if(theRock!=RockKind.NONE)return false;

        RockKind opponent=RockKind.NONE;
        RockKind me=RockKind.NONE;
        switch (turn){
            case BLACK:
                me=RockKind.BLACK;
                opponent=RockKind.WHITE;
                break ;
            case WHITE:
                me=RockKind.WHITE;
                opponent=RockKind.BLACK;
                break;
            case FINISHED:
                return false;
        }

        int searchingDirections[][]={{-1,0},{-1,+1},{0,+1},{+1,+1},{+1,0},{+1,-1},{0,-1},{-1,-1}};
        for(int d=0;d<8;d++){
            if(canFlipOnLine(model,me,opponent,i,j,searchingDirections[d][0],searchingDirections[d][1]))
                return true;
        }
        return false;
    }

    private boolean canFlipOnLine(BoardModel model, RockKind me, RockKind opponent, int i, int j, int searchI, int searchJ){
        boolean opponentLasting=false;
        while(true){
            i+=searchI;
            j+=searchJ;

            if(i<0||i>=BoardModel.rowNum()
                    ||j<0||j>=BoardModel.columnNum())return false;
            RockKind theRock=model.get(i,j);

            if (theRock==opponent) opponentLasting=true;
            if(theRock==me){
                if(opponentLasting)return true;
                else return false;
            }
            if(theRock!=opponent&&theRock!=me)return false;
        }
    }

    public void putAndFlip(BoardModel model, Turn turn, int i, int j) {

        RockKind opponent = RockKind.NONE;
        RockKind me = RockKind.NONE;
        switch (turn) {
            case BLACK:
                me = RockKind.BLACK;
                opponent = RockKind.WHITE;
                break;
            case WHITE:
                me = RockKind.WHITE;
                opponent = RockKind.BLACK;
                break;
        }

        model.set(me, i, j);

        int searchingDirections[][] = {{-1, 0}, {-1, +1}, {0, +1}, {+1, +1}, {+1, 0}, {+1, -1}, {0, -1}, {-1, -1}};
        for (int d = 0; d < 8; d++) {
            if (canFlipOnLine(model, me, opponent, i, j, searchingDirections[d][0], searchingDirections[d][1]))
                flipOnLine(model, opponent, i, j, searchingDirections[d][0], searchingDirections[d][1]);
        }
    }

    private void flipOnLine(BoardModel model, RockKind opponent, int i, int j, int searchI, int searchJ){
        while(true){
            i+=searchI;
            j+=searchJ;
            if (model.get(i,j)!=opponent) {
                break;
            }
            model.reverse(i,j);

        }
    }
}
