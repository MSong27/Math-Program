import javax.swing.*;
import java.awt.*;

public class Graph extends JPanel {
    private Point2D point = new Point2D(0, 0);
    private boolean plotPoint = true;

    public Graph(Point2D point, boolean plotPoint) {
        this.point = point;
        this.plotPoint = plotPoint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);

        for (int i = 0 ; i <= 20; i++) {
            g.drawLine(i * 40, 0, i * 40, 800);
        }

        for (int i = 0 ; i <= 20; i++) {
            g.drawLine(0, i * 40, 800, i * 40);
        }

        g.setColor(Color.BLACK);

        g.fillRect(0, 399, 800, 3);

        g.fillRect(399, 0, 3, 800);

        if (plotPoint) {
            g.setColor(Color.RED);
            g.fillOval((int) point.getX() * 40 + 395, (int) point.getY() * -40 + 395, 10, 10);
        }
    }
}
