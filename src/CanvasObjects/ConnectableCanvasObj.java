package CanvasObjects;

import app.AlgorithmLib;
import app.Canvas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class ConnectableCanvasObj extends CanvasObject {
    public ConnectableCanvasObj(Canvas canvas) {
        super(canvas);
        canvas.add(this);
        connectionPorts = new ConnectionPort[]{
                new ConnectionPort(this),
                new ConnectionPort(this),
                new ConnectionPort(this),
                new ConnectionPort(this)
        };
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0));
        Box nPanel= Box.createHorizontalBox(), wPanel=Box.createVerticalBox(), sPanel = Box.createHorizontalBox(), ePanel = Box.createVerticalBox();
        nPanel.setBackground(new Color(0, 0, 0, 0));
        ePanel.setBackground(new Color(0, 0, 0, 0));
        wPanel.setBackground(new Color(0, 0, 0, 0));
        sPanel.setBackground(new Color(0, 0, 0, 0));
        nPanel.add(Box.createHorizontalGlue());
        nPanel.add(connectionPorts[0]);
        nPanel.add(Box.createHorizontalGlue());
        wPanel.add(Box.createVerticalGlue());
        wPanel.add(connectionPorts[1]);
        wPanel.add(Box.createVerticalGlue());
        sPanel.add(Box.createHorizontalGlue());
        sPanel.add(connectionPorts[2]);
        sPanel.add(Box.createHorizontalGlue());
        ePanel.add(Box.createVerticalGlue());
        ePanel.add(connectionPorts[3]);
        ePanel.add(Box.createVerticalGlue());
        add(nPanel, BorderLayout.NORTH);
        add(wPanel, BorderLayout.WEST);
        add(sPanel, BorderLayout.SOUTH);
        add(ePanel, BorderLayout.EAST);
        hideConnectionPorts();
    }
    protected ConnectionPort[] connectionPorts;
    private ArrayList<ConnectionCanvasObj> connectionObjs = new ArrayList<>();
    protected void showConnectionPorts(){
        for(ConnectionPort connectionPort:connectionPorts){
            connectionPort.setVisible(true);
        }
    }
    protected void hideConnectionPorts(){
        for(ConnectionPort connectionPort:connectionPorts){
            connectionPort.setVisible(false);
        }
    }

    public void add(ConnectionCanvasObj connection){
        connectionObjs.add(connection);
    }

    public void remove(ConnectionCanvasObj connection){
        connectionObjs.remove(connection);
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        for(ConnectionCanvasObj sourceCon:connectionObjs){
            try {
                sourceCon.updatePosition();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    @Override
    public void onSelected() {
        showConnectionPorts();
    }

    @Override
    public void onUnSelected(){
        hideConnectionPorts();
    }

    public Point[] getInfoForConnection(ConnectableCanvasObj source){
        ConnectionPort closestSourcePort = null, closestPort = null;
        int minDis = 1<<20;
        for(ConnectionPort sourcePort: source.connectionPorts){
            for(ConnectionPort port:connectionPorts){
                Point delta = new Point(
                        sourcePort.getLocationOnCanvas().x-port.getLocationOnCanvas().x,
                        sourcePort.getLocationOnCanvas().y-port.getLocationOnCanvas().y
                );
                int dis = delta.x*delta.x+delta.y*delta.y;
                if(dis<minDis){
                    minDis = dis;
                    closestPort = port;
                    closestSourcePort = sourcePort;
                }
            }
        }
        if(closestPort==null)return new Point[]{};
        return new Point[]{
                new Point(closestSourcePort.getLocationOnCanvas()),
                new Point(closestPort.getLocationOnCanvas())
        };
    }

    public Point getNearestConnectionPoint(Point target) {
        ConnectionPort closestPort = connectionPorts[0];
        int minDis = 1<<20;
        for(ConnectionPort port:connectionPorts){
            Point delta = new Point(
                    target.x-port.getLocationOnCanvas().x,
                    target.y-port.getLocationOnCanvas().y
            );
            int dis = delta.x*delta.x+delta.y*delta.y;
            if(dis<minDis){
                minDis = dis;
                closestPort = port;
            }
        }
        return new Point(closestPort.getLocationOnCanvas());
    }
}
