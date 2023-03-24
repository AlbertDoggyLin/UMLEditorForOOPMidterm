package CanvasObjects;

import app.Canvas;

import java.awt.*;

public class CompositionLine  extends ConnectionCanvasObj{
    public CompositionLine(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onUnSelected() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        Point endPoint = new Point(10, 10);
        Point startPoint = new Point(10, 10);
        if(delta == null)return;
        if(delta.x*delta.y>=0){
            if(delta.x>=0){endPoint.x+=delta.x;endPoint.y+=delta.y;}
            else {startPoint.x-=delta.x;startPoint.y-=delta.y;}
        }
        else{
            if(delta.x>=0){endPoint.x+=delta.x;startPoint.y-=delta.y;}
            else {startPoint.x-=delta.x;endPoint.y+=delta.y;}
        }
        int arrowlength = 8;double deltaL = Math.sqrt(delta.x*delta.x+delta.y*delta.y);
        double refx = -delta.x/deltaL, refy = -delta.y/deltaL, cos45 = 1/1.4145;
        double x45 = (refx-refy)*cos45*arrowlength, y45 = (refx+refy)*cos45*arrowlength, ym45 = -x45;
        Point intersectionPoint = new Point(
                (int)(arrowlength*cos45*refx*2+endPoint.x),
                (int)(arrowlength*cos45*refy*2+endPoint.y)
        );
        g.drawLine(startPoint.x, startPoint.y, intersectionPoint.x, intersectionPoint.y);
        g.drawLine(endPoint.x, endPoint.y, endPoint.x+(int)x45, endPoint.y+(int)y45);
        g.drawLine(endPoint.x, endPoint.y, endPoint.x+(int)y45, endPoint.y+(int)ym45);
        g.drawLine(intersectionPoint.x, intersectionPoint.y, endPoint.x+(int)x45, endPoint.y+(int)y45);
        g.drawLine(intersectionPoint.x, intersectionPoint.y, endPoint.x+(int)y45, endPoint.y+(int)ym45);
    }
}
