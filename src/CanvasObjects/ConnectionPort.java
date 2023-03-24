package CanvasObjects;

import app.AlgorithmLib;

import javax.swing.*;
import java.awt.*;

public class ConnectionPort extends JPanel {
    private ConnectableCanvasObj parent;
    public ConnectionPort(ConnectableCanvasObj parent){
        this.parent = parent;
        setBackground(Color.black);
        setMaximumSize(new Dimension(10, 10));
        setSize(10, 10);
    }

    public Point getLocationOnCanvas() {
        Point ans = null;
        try {
            ans = AlgorithmLib.getRelativeCoordinate(parent.getCanvas(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
