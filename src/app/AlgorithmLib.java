package app;

import java.awt.*;

public class AlgorithmLib {
    public static Point getRelativeCoordinate(Component ancestor, Component child){
        Component cur = child;
        Point ans = new Point(0, 0);
        while(cur!=null&&cur!=ancestor) {
            ans.x+=cur.getX();
            ans.y+=cur.getY();
            cur = cur.getParent();
        }
        return ans;
    }
}
