import javax.swing.JFrame;
import java.awt.Rectangle;

public class Test
{
    //-----------------------------------------------------------------
    //  Creates and displays the application frame.
    //-----------------------------------------------------------------


    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Test");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new TestPanel());

        frame.pack();
        frame.setVisible(true);
    }
}
