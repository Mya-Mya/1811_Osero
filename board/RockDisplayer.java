package board;

import javax.swing.*;
import java.awt.*;

public class RockDisplayer extends JPanel {
    public RockDisplayer(){
        super();
        this.setBorder(null);
    }
    private RockKind kind=RockKind.NONE;
    public void setKind(RockKind kind){
        this.kind=kind;
        updateUI();
    }
    private final int circumMargin =8;
    private final int contentMargin=circumMargin+2;

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        switch (this.kind){
            case WHITE:
                g.setColor(Color.BLACK);
                g.drawOval(circumMargin, circumMargin,getWidth()- circumMargin*2,getHeight()- circumMargin*2);
                g.setColor(Color.WHITE);
                g.fillOval(contentMargin,contentMargin,getWidth()-contentMargin*2,getHeight()-contentMargin*2);
                break;
            case BLACK:
                g.setColor(Color.BLACK);
                g.drawOval(circumMargin, circumMargin,getWidth()- circumMargin*2,getHeight()- circumMargin*2);
                g.setColor(Color.BLACK);
                g.fillOval(contentMargin,contentMargin,getWidth()-contentMargin*2,getHeight()-contentMargin*2);
                break;
        }
    }
}
