package CanvasObjects;

import app.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class CanvasObject extends JPanel implements MouseListener, MouseMotionListener {
    public CanvasObject(Canvas canvas){
        this.canvas = canvas;
        addMouseListener(this);
        addMouseMotionListener(this);
        canvas.add(this, JLayeredPane.DEFAULT_LAYER);
        selected = false;
    }
    private boolean selected;
    private int _depth;
    private Point bias;
    private Canvas canvas;
    public Canvas getCanvas(){return canvas;}
    private Group parent = null;
    public void setParent(Group group){
        parent = group;
    }
    public CanvasObject getAncestor(){
        CanvasObject ans = this;
        while(ans.parent!=null)
            ans = ans.parent;
        return ans;
    }
    private void setDepth(int depth){
        if(depth>=99)_depth = 99;
        else _depth = Math.max(depth, 0);
        canvas.setLayer(this, _depth);
    }

    public int getDepth(){return _depth;}

    public abstract void onSelected();

    public void move(Point delta){
        setLocation(getX()+delta.x, getY()+delta.y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        canvas.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        bias = new Point(e.getX(), e.getY());
        canvas.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        canvas.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public abstract void onUnSelected();
}
