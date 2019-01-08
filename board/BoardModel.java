package board;

import game.Turn;

import java.util.LinkedList;
import java.util.List;

public class BoardModel {

    private RockKind[][] info=new RockKind[rowNum()][columnNum()];
    private List<BoardModelChangeListener> listeners=new LinkedList<BoardModelChangeListener>();

    static public int rowNum(){return 8;}
    static public int  columnNum(){return 8;}

    /** 黒い石と白い石がそれぞれ2つずつ中央に置かれた初期状態のボードモデルを作成する
     *
     */
    public BoardModel(){
        initAll();
        initRock();
    }

    /** modelと石の並びが同じボードモデルを作成する
     * ModelDataChangeListenerのリストは引き継がれない
     * @param model
     */
    public BoardModel(BoardModel model){
        initAll();
        for(int i=0;i<info.length;i++){
            for(int j=0;j<info[i].length;j++){
                set(model.get(i,j),i,j);
            }
        }
    }

    public void initRock(){
       setWhite(3,3);
       setWhite(4,4);
       setBlack(3,4);
       setBlack(4,3);
    }
    public void initAll(){
        for(int i=0;i<info.length;i++){
            for(int j=0;j<info[i].length;j++){
                setNone(i,j);
            }
        }
    }

    public void setWhite(int i,int j){
        set(RockKind.WHITE,i,j);
    }
    public void setBlack(int i,int j){
        set(RockKind.BLACK,i,j);
    }
    public void setNone(int i,int j){
        set(RockKind.NONE,i,j);
    }
    public void set(RockKind kind,int i,int j){
        info[i][j]=kind;
        fireModelDataChanged();
    }

    public RockKind get(int i,int j){
        return info[i][j];
    }

    public void reverse(int i,int j){
        set(get(i,j).reverse(),i,j);
    }

    public void addModelDataChangeListener(BoardModelChangeListener listener) {
        listeners.add(listener);
    }

    private void fireModelDataChanged(){
        listeners.forEach(BoardModelChangeListener::boardModelDataChanged);
    }

    public boolean isFull() {
        for(int i=0;i<BoardModel.rowNum();i++){
            for(int j=0;j<BoardModel.columnNum();j++){
                RockKind theRock=get(i,j);
                if(theRock!=RockKind.BLACK&&theRock!=RockKind.WHITE)return false;
            }
        }
        return true;
    }

    public int numberOf(RockKind kind){
        int count=0;
        for(int i=0;i<BoardModel.rowNum();i++){
            for(int j=0;j<BoardModel.columnNum();j++){
                if(get(i,j)==kind)count++;
            }
        }
        return count;
    }
}
