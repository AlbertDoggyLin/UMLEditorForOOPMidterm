package CanvasObjects;

import app.Canvas;

import java.awt.*;

public class ClassCanvasObj extends ConnectableCanvasObj{
    private static int counter = 0;
    public ClassCanvasObj(Canvas canvas) {
        super(canvas);
        counter++;
        setName("Class"+counter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(123, 123, 123, 255));
        g.fillRect(10, 10, getWidth()-20, getHeight()-20);
        g.setColor(new Color(57, 57, 57, 255));
        g.drawRect(10, 10, getWidth()-20, getHeight()-20);
        g.drawLine(10, 50, getWidth()-10, 50);
        g.drawLine(10, 90, getWidth()-10, 90);
        Font font = new Font("Serif", Font.BOLD, 24);
        String text = getName();
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (getWidth() - metrics.stringWidth(text)) / 2;
        int y = ((40 - metrics.getHeight()) / 2) + metrics.getAscent() + 10;
        g.setFont(font);
        g.drawString(text, x, y);
    }
}
