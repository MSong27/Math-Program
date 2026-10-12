import javax.swing.*;
import java.awt.*;

public class GraphTest {
    public static void main(String[] args) {
        JFrame graph = new JFrame();
        graph.setTitle("Graphing Window");
        graph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graph.setSize(800, 800);
        graph.setResizable(true);

        JPanel xAxis = new JPanel();
        xAxis.setBounds(399, 0, 2, 800);
        xAxis.setBackground(Color.BLACK);

        JPanel yAxis = new JPanel();
        yAxis.setBounds(0, 400, 800, 2);
        yAxis.setBackground(Color.BLACK);

        JPanel gridLines = new Graph(new Point2D(1, -1), true);

        graph.add(gridLines);


        graph.setVisible(true);
    }
}
