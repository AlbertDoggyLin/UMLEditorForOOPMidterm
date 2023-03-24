package CanvasObjects;

import app.AlgorithmLib;
import app.Canvas;

import java.awt.*;

public abstract class ConnectionCanvasObj extends CanvasObject {
    private ConnectableCanvasObj source, target = null;
    protected Point delta = null;
    public ConnectionCanvasObj(Canvas canvas) {
        super(canvas);
        setBackground(new Color(0, 0, 0, 0));
    }

    public void setSource(ConnectableCanvasObj source){
        if(this.source!=null)this.source.remove(this);
        this.source = source;
        source.add(this);
    }
    public void setTarget(ConnectableCanvasObj target){
        if(this.target!=null)this.target.remove(this);
        this.target = target;
        target.add(this);
    }

    @Override
    public abstract void onSelected();

    public void setStartEndLocation(Point startPoint, Point endPoint) {
        setSize(Math.abs(startPoint.x-endPoint.x)+20, Math.abs(startPoint.y-endPoint.y)+20);
        setLocation(Math.min(startPoint.x-10, endPoint.x-10), Math.min(startPoint.y-10, endPoint.y-10));
        delta = new Point(endPoint.x-startPoint.x, endPoint.y-startPoint.y);
    }

    public void updatePosition() throws Exception {
        if(source==null)throw new Exception("source is null, you should provide source point");
        if(target==null)throw new Exception("target is null, you should provide target point");
        Point[] info = target.getInfoForConnection(source);
        setStartEndLocation(info[0], info[1]);
    }

    public void updatePosition(Point target) throws Exception {
        if(source==null)throw new Exception("source is null, you should provide source point");
        Point startPoint = source.getNearestConnectionPoint(target);
        setStartEndLocation(startPoint,target);
    }

    public void removeFromCanvas() {
        getCanvas().remove(this);
        if(source!=null)source.remove(this);
        if(target!=null)target.remove(this);
    }
}
