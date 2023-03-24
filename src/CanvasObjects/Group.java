package CanvasObjects;

import app.Canvas;

import java.awt.*;
import java.util.ArrayList;

public class Group extends CanvasObject{
    private final ArrayList<CanvasObject> groupMember = new ArrayList<>();
    public Group(Canvas canvas, ArrayList<CanvasObject> groupMember) {
        super(canvas);
        this.groupMember.addAll(groupMember);
        groupMember.forEach(member->member.setParent(this));
        canvas.removeGroupElement(this);
        assert groupMember.size()!=0;
        Point startPosition = new Point(groupMember.get(0).getLocation()), endPosition = new Point(groupMember.get(0).getLocation());
        for(CanvasObject member:groupMember){
            if(member.getX()<startPosition.x)startPosition.x = member.getX();
            if(member.getY()<startPosition.y)startPosition.y = member.getY();
            if(member.getX()+member.getWidth()>endPosition.x)endPosition.x = member.getX()+member.getWidth();
            if(member.getY()+member.getHeight()>endPosition.y)endPosition.y = member.getY()+member.getHeight();
        }
        setLocation(startPosition);
        setSize(new Dimension(endPosition.x-startPosition.x, endPosition.y-startPosition.y));
        setVisible(false);
        setBackground(Color.BLUE);
    }

    public ArrayList<CanvasObject> getGroupMember(){
        return new ArrayList<>(groupMember);
    }
    @Override
    public void onSelected() {
        groupMember.forEach(CanvasObject::onSelected);
    }

    @Override
    public void onUnSelected() {
        groupMember.forEach(CanvasObject::onUnSelected);
    }

    @Override
    public void move(Point delta) {
        super.move(delta);
        groupMember.forEach(member->member.move(delta));
    }
}
