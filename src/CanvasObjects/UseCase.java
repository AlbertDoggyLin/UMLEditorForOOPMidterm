package CanvasObjects;

import app.Canvas;

import java.awt.*;

public class UseCase extends ConnectableCanvasObj{
    public static int counter=0;
    public UseCase(Canvas canvas) {
        super(canvas);
        counter++;
        setName("Use Case "+counter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(123, 123, 123, 255));
        g.fillOval(10, 10, getWidth()-20, getHeight()-20);
        g.setColor(new Color(57, 57, 57, 255));
        g.drawOval(10, 10, getWidth()-20, getHeight()-20);
        Font font = new Font("Serif", Font.BOLD, 24);
        String text = getName();
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }
}
