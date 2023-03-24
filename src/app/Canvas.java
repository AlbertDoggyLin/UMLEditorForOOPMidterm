package app;

import CanvasObjects.CanvasObject;
import CanvasObjects.ConnectableCanvasObj;
import CanvasObjects.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Canvas extends JLayeredPane implements MouseMotionListener, MouseListener {
    private final ArrayList<CanvasObject> canvasObjs = new ArrayList<>();
    private final ArrayList<ConnectableCanvasObj> connectableObjs = new ArrayList<>();
    public final ArrayList<CanvasObject> selectedObjs = new ArrayList<CanvasObject>(){
        public boolean remove(CanvasObject object) {
            super.remove(object);
            object.onUnSelected();
            return true;
        }

        public boolean add(CanvasObject object){
            super.add(object);
            object.onSelected();
            return true;
        }

        @Override
        public void clear() {
            for(CanvasObject object:selectedObjs)object.onUnSelected();
            super.clear();
        }
    };
    public Canvas(){
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    private ModeBehavior currentModeBehavior(){return AppController.getModeBehavior();}
    public void add(CanvasObject canvasObject, Integer depth) {
        super.add(canvasObject, depth);
        canvasObjs.add(canvasObject);
        refresh();
    }

    public void add(ConnectableCanvasObj connectableObj){
        connectableObjs.add(connectableObj);
    }

    public void remove(CanvasObject canvasObject){
        super.remove(canvasObject);
        canvasObjs.remove(canvasObject);
        refresh();
    }

    public void remove(ConnectableCanvasObj connectableObj){
        connectableObjs.remove(connectableObj);
    }

    public void refresh(){
        revalidate();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentModeBehavior().canvasDragged(this, e);
        refresh();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        currentModeBehavior().canvasClicked(this, e);
        refresh();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentModeBehavior().canvasPressed(this, e);
        refresh();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentModeBehavior().canvasReleased(this, e);
        refresh();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void refreshSelectedObjs(Point startLoc, Point range) {
        selectedObjs.clear();
        Point endLoc = new Point(startLoc.x+range.x, startLoc.y+range.y);
        for(CanvasObject obj:canvasObjs){
            if(obj.getX()<startLoc.x||obj.getY()<startLoc.y)continue;
            if(obj.getX()+obj.getWidth()>endLoc.x||obj.getY()+obj.getHeight()>endLoc.y)continue;
            selectedObjs.add(obj);
        }
    }

    public void clearSelect() {
        refreshSelectedObjs(new Point(-1, -1), new Point(0, 0));
    }

    public void group() {
        if(selectedObjs.size()>1)
            new Group(this, selectedObjs);
    }

    public void ungroup() {
        if(selectedObjs.size()==1&& selectedObjs.get(0) instanceof Group){
            Group removedGroup = (Group) selectedObjs.get(0);
            removedGroup.getGroupMember().forEach(member->member.setParent(null));
            canvasObjs.addAll(removedGroup.getGroupMember());
            canvasObjs.remove(removedGroup);
        }
    }

    public void select(CanvasObject source) {
        selectedObjs.clear();
        selectedObjs.add(source.getAncestor());
    }

    public ConnectableCanvasObj getConnectableObj(Point locationOnScreen) {
        ConnectableCanvasObj ans=null;
        for(ConnectableCanvasObj connectableCanvasObj:connectableObjs){
            Point startLocationOnScreen = connectableCanvasObj.getLocationOnScreen();
            Point endLocationOnScreen = new Point(
                    connectableCanvasObj.getWidth()+startLocationOnScreen.x,
                    connectableCanvasObj.getHeight()+startLocationOnScreen.y
            );
            if(locationOnScreen.x>endLocationOnScreen.x||locationOnScreen.x<startLocationOnScreen.x)continue;
            if(locationOnScreen.y>endLocationOnScreen.y||locationOnScreen.y<startLocationOnScreen.y)continue;
            if(ans==null||ans.getDepth()<connectableCanvasObj.getDepth())ans=connectableCanvasObj;
        }
        return ans;
    }

    public void removeGroupElement(Group group) {
        group.getGroupMember().forEach(canvasObjs::remove);
    }
}
