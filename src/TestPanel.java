import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class TestPanel extends JPanel
{
    private Timer timer;
    private int delay = 10;
    private int width = 700;
    private int height = 700;
    private int current_radius = 100;
    private int right_mouse_flag = 0;

    private ArrayList<Point> pointList;
    private ArrayList<Integer> moveListX;
    private ArrayList<Integer> moveListY;
    private ArrayList<Integer> radiusList;
    private ArrayList<Color> colorList;

    private JLabel radiusLabel;

    private Random rand;


    public TestPanel() {
        pointList = new ArrayList<Point>();
        moveListX = new ArrayList<Integer>();
        moveListY = new ArrayList<Integer>();
        radiusList = new ArrayList<Integer>();
        colorList = new ArrayList<Color>();

        radiusLabel = new JLabel();
        super.add(radiusLabel);

        rand = new Random();

        setBackground(Color.white);
        setPreferredSize(new Dimension(width, height));

        addMouseListener(new TestMouseListener());
        addMouseWheelListener(new TestMouseListener());
        addMouseMotionListener(new TestMouseListener());

        timer = new javax.swing.Timer(delay, new TestActionListener());

        timer.start();
    }

    //-----------------------------------------------------------------
    //  Draws all of the dots stored in the list.
    //-----------------------------------------------------------------
    public void paintComponent(Graphics page)
    {
        super.paintComponent(page);
        radiusLabel.setText("Radius: " + current_radius);

        for (int i = 0; i < pointList.size() - 1; i++) {
            Point spot = pointList.get(i);
            int move_x = moveListX.get(i);
            int move_y = moveListY.get(i);
            int radius = radiusList.get(i);
            Color color = colorList.get(i);

            page.setColor(color);

            page.fillOval(spot.x, spot.y, radius, radius);

            if (right_mouse_flag != 1) {
                spot.x = spot.x + move_x;
                spot.y = spot.y + move_y;
            }

            if (spot.x < 0 || spot.x > width - radius) {
                moveListX.set(i, move_x * -1);
            }
            if (spot.y < 0 || spot.y > height - radius) {
                moveListY.set(i, move_y * -1);
            }

            pointList.set(i, spot);
            System.out.println(right_mouse_flag);
        }
    }
    private class TestMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener

    {

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {
            if((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                right_mouse_flag = 1;
            }
            detectMouse3(e);
        }

        private void detectMouse3(MouseEvent e) {
            if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                for (int i = 0; i < pointList.size()-1; i++) {
                    Point spot = pointList.get(i);
                    int temp_radius = radiusList.get(i);
                    Point mouse_spot = e.getPoint();

                    spot.x = mouse_spot.x - temp_radius/2;
                    spot.y = mouse_spot.y - temp_radius/2;

                    pointList.set(i, spot);
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            detectMouse1(e);
            if((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                right_mouse_flag = 0;
            }
            repaint();
        }

        private void detectMouse1(MouseEvent e) {
            if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
                int move_x = rand.nextInt(20) - 10;
                int move_y = rand.nextInt(20) - 10;

                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                int t = rand.nextInt(255);

                Color color = new Color(r, g, b, t);
                colorList.add(color);

                moveListX.add(move_x);
                moveListY.add(move_y);

                pointList.add(e.getPoint());
                radiusList.add(current_radius);
            }
        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }

        public void mouseDragged(MouseEvent e) {
            detectMouse1(e);
            detectMouse3(e);
        }

        public void mouseMoved(MouseEvent e) {

        }

        public void mouseWheelMoved(MouseWheelEvent e) {
            int notches = e.getUnitsToScroll();
            if (notches < 0 && current_radius > 2) {
                current_radius--;
            } else if (notches > 0) {
                current_radius++;
            } else if (current_radius <= 2) {
                current_radius = 2;
            }
            System.out.println(current_radius);
        }
    }
    private class TestActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}
