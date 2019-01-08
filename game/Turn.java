package game;

import board.RockKind;

public enum Turn {
    WHITE,
    BLACK,
    FINISHED;

    @Override
    public String toString() {
        switch (this){
            case BLACK:return"黒";
            case WHITE:return"白";
            case FINISHED:return "終了";
        }
        return "何の番なんでしょうね";
    }

    static public Turn getOpponent(Turn now) {
        switch (now) {
            case BLACK:
                return WHITE;
            case WHITE:
                return BLACK;
            case FINISHED:
                return FINISHED;
        }
        return null;
    }

    public Turn getOpponent() {
        return getOpponent(this);
    }

    public RockKind toRockKind(){
        switch (this){
            case WHITE:
                return (RockKind.WHITE);
            case BLACK:
                return (RockKind.BLACK);
            case FINISHED:
                return null;
        }
        return null;
    }
}
