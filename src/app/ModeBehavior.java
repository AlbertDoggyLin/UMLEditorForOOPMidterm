package app;

import CanvasObjects.CanvasObject;
import CanvasObjects.ConnectableCanvasObj;
import CanvasObjects.ConnectionCanvasObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.Callable;

public enum ModeBehavior {
    SELECT{
        private Point pressedPoint;
        private Point lastLocation;
        private JPanel hoverFrame = null;
        @Override
        void canvasDragged(Canvas canvas, MouseEvent me) {
            assert me.getSource() instanceof CanvasObject;
            if(me.getSource()!=canvas){
                if(me.getSource() instanceof ConnectionCanvasObj)return;
                CanvasObject draggedObj = ((CanvasObject) me.getSource()).getAncestor();
                Point relP = AlgorithmLib.getRelativeCoordinate(canvas, (Component) me.getSource());
                Point evenPoint = new Point(relP.x+me.getX(), relP.y+me.getY());
                draggedObj.move(new Point(evenPoint.x-lastLocation.x, evenPoint.y-lastLocation.y));
                lastLocation = evenPoint;
                return;
            }
            Point relP = AlgorithmLib.getRelativeCoordinate(canvas, (Component) me.getSource());
            Point meLocOnCanvas = new Point(relP.x+me.getX(), relP.y+me.getY());
            Point startLoc = new Point(Math.min(pressedPoint.x, meLocOnCanvas.x),
                    Math.min(pressedPoint.y, meLocOnCanvas.y));
            Point range = new Point(Math.abs(meLocOnCanvas.x-pressedPoint.x), Math.abs(meLocOnCanvas.y-pressedPoint.y));
            hoverFrame.setLocation(startLoc);
            hoverFrame.setSize(range.x, range.y);
            canvas.refreshSelectedObjs(startLoc, range);
        }

        @Override
        void canvasClicked(Canvas canvas, MouseEvent me) {
        }

        @Override
        void canvasPressed(Canvas canvas, MouseEvent me) {
            canvas.clearSelect();
            assert me.getSource() instanceof Component;
            Point relP = AlgorithmLib.getRelativeCoordinate(canvas, (Component) me.getSource());
            pressedPoint = new Point(relP.x+me.getX(), relP.y+me.getY());
            if(me.getSource()!=canvas){
                if(me.getSource() instanceof ConnectionCanvasObj)return;
                canvas.select((CanvasObject) me.getSource());
                lastLocation = pressedPoint;
                return;
            }
            lastLocation = null;
            hoverFrame = new JPanel();
            hoverFrame.setBackground(new Color(113, 156, 208, 70));
            hoverFrame.setLocation(pressedPoint);
            canvas.add(hoverFrame, JLayeredPane.DEFAULT_LAYER);
        }

        @Override
        void canvasReleased(Canvas canvas, MouseEvent me) {
            if(hoverFrame==null)return;
            canvas.remove(hoverFrame);
            hoverFrame = null;
            lastLocation = null;
        }

        @Override
        void modeChanged(Canvas canvas) {
            canvas.clearSelect();
        }
    }, CREATE {
        private Point pressedPoint;
        private CanvasObject createdObj = null;
        @Override
        void canvasDragged(Canvas canvas, MouseEvent me) {
            if(createdObj==null)return;
            assert me.getSource() instanceof Component;
            Point relP = AlgorithmLib.getRelativeCoordinate(canvas, (Component) me.getSource());
            Point meLocOnCanvas = new Point(relP.x+me.getX(), relP.y+me.getY());
            Point startLoc = new Point(Math.min(pressedPoint.x, meLocOnCanvas.x),
                    Math.min(pressedPoint.y, meLocOnCanvas.y));
            Point range = new Point(Math.abs(meLocOnCanvas.x-pressedPoint.x), Math.abs(meLocOnCanvas.y-pressedPoint.y));
            createdObj.setLocation(startLoc);
            createdObj.setSize(range.x, range.y);
        }

        @Override
        void canvasClicked(Canvas canvas, MouseEvent me) {

        }

        @Override
        void canvasPressed(Canvas canvas, MouseEvent me) {
            assert me.getSource() instanceof Component;
            Point relP = AlgorithmLib.getRelativeCoordinate(canvas, (Component) me.getSource());
            pressedPoint = new Point(relP.x+me.getX(), relP.y+me.getY());
            try {
                createdObj = targetObjCreator.call();
                createdObj.setLocation(pressedPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        void canvasReleased(Canvas canvas, MouseEvent me) {
            createdObj = null;
        }

        @Override
        void modeChanged(Canvas canvas) {

        }
    }, CONNECT {
        private ConnectionCanvasObj createdObj = null;
        private ConnectableCanvasObj startObj = null;
        private ConnectableCanvasObj endObj = null;
        @Override
        void canvasDragged(Canvas canvas, MouseEvent me) {
            if(createdObj==null||startObj==null)return;
            endObj = canvas.getConnectableObj(me.getLocationOnScreen());
            if(endObj==startObj)endObj = null;
            if(endObj!=null){
                createdObj.setTarget(endObj);
                try {
                    createdObj.updatePosition();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            Point tmp = AlgorithmLib.getRelativeCoordinate(canvas,(Component) me.getSource());
            Point endPoint = new Point(tmp.x, tmp.y);
            endPoint.x+=me.getX();endPoint.y+=me.getY();
            try {
                createdObj.updatePosition(endPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        void canvasClicked(Canvas canvas, MouseEvent me) {

        }

        @Override
        void canvasPressed(Canvas canvas, MouseEvent me) {
            if(!(me.getSource() instanceof ConnectableCanvasObj))return;
            assert me.getSource() instanceof ConnectableCanvasObj;
            startObj = (ConnectableCanvasObj) me.getSource();
            try {
                createdObj = (ConnectionCanvasObj) targetObjCreator.call();
                createdObj.setSource(startObj);
                Point endPoint = AlgorithmLib.getRelativeCoordinate(canvas, (Component) me.getSource());
                endPoint.x+=me.getX();endPoint.y+=me.getY();
                createdObj.updatePosition(endPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        void canvasReleased(Canvas canvas, MouseEvent me) {
            if(endObj==null && createdObj!=null)
                createdObj.removeFromCanvas();
            startObj = endObj = null;
            createdObj = null;
        }

        @Override
        void modeChanged(Canvas canvas) {

        }
    };

    protected Callable<CanvasObject> targetObjCreator;
    public void setTargetObjCreator(Callable<CanvasObject> targetObjCreator){this.targetObjCreator = targetObjCreator;}
    abstract void canvasDragged(Canvas canvas, MouseEvent me);
    abstract void canvasClicked(Canvas canvas, MouseEvent me);
    abstract void canvasPressed(Canvas canvas, MouseEvent me);
    abstract void canvasReleased(Canvas canvas, MouseEvent me);
    abstract void modeChanged(Canvas canvas);
}
