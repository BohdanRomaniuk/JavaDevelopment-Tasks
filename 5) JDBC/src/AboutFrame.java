import javax.swing.JFrame;
import javax.swing.JLabel;

class AboutFrame extends JFrame
{
   JLabel creator;
   public AboutFrame(String message)
   {
      setTitle("Про програму!");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
      creator = new JLabel(message);
      add(creator);
   }

   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 100;  
}