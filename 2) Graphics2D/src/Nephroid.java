import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;


public class Nephroid
{  
   public static void main(String[] args)
   {  
      JFrame frame = new NephroidFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

class NephroidFrame extends JFrame
{  
   private NephroidShape canvas;
   private static final int DEFAULT_WIDTH = 500;
   private static final int DEFAULT_HEIGHT = 500;
   
   public NephroidFrame()
   {  
	  Toolkit kit = Toolkit.getDefaultToolkit();
	  Dimension screenSize = kit.getScreenSize();
	  int screenHeight = screenSize.height;
	  int screenWidth = screenSize.width;

	  setSize(screenWidth / 2, screenHeight / 2);
	  setLocation(screenWidth / 4, screenHeight / 4);
	  
	  Image icon = kit.getImage("icon.jpg");
      setIconImage(icon);
      setTitle("Nephroid");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      canvas = new NephroidShape();
      add(canvas, BorderLayout.CENTER);
   }
}

class NephroidShape extends JPanel
{  
   private Paint paint;
   private Stroke stroke;
   
   public NephroidShape()
   {  
	  paint = Color.red;
	  stroke = new BasicStroke(1);
      addMouseListener(new
        MouseAdapter()
      {
         public void mousePressed(MouseEvent event)
         {
        	 Random randomizer = new Random();
        	 float r = randomizer.nextFloat();
        	 float g = randomizer.nextFloat();
        	 float b = randomizer.nextFloat();
        	 paint = new Color(r,g,b);
        	 int cap = randomizer.nextInt(3);
        	 int join = randomizer.nextInt(3);
        	 System.out.println("RGB: (" + r + ", " + g + ", " + b + ") Cap: " + cap + " Join: " + join);
        	 stroke = new BasicStroke(randomizer.nextInt(15), cap , join);
        	 repaint();
         }
      });
   }

   public void paintComponent(Graphics g)
   {  
      super.paintComponent(g);
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, getWidth(), getHeight());
      Graphics2D g2 = (Graphics2D) g;
      g2.setPaint(Color.black);
      Shape xAxis = new Line2D.Double(0, getHeight()/2, getWidth(), getHeight()/2);
      Shape yAxis = new Line2D.Double(getWidth()/2, 0, getWidth()/2, getHeight());
      g2.draw(xAxis);
      g2.draw(yAxis);
      
      Font fnt = new Font("SansSerif", Font.PLAIN, 15);
      g.setColor(Color.black);
      g.setFont(fnt);
      g.drawString("Романюк Богдан Варіант №22",0,20);
      
      //Set random values
      g2.setPaint(paint);
      g2.setStroke(stroke);
      float step = (float)0.01;
      int size = (int)((2*Math.PI-0)/step)+1;
      Point2D[] p = new Point2D[size];
      
      int smallest = getHeight()<getWidth()?getHeight():getWidth();
      int r = smallest/10;
      int alpha = 0;
      int j=0;
      for(float phi=0; phi<=2*Math.PI; phi+=step)
      {
    	  float x = (float) (3*r*Math.cos(phi) - r*Math.cos(alpha+3*phi));
    	  float y = (float) (3*r*Math.sin(phi) - r*Math.sin(alpha+3*phi));
    	  p[j++] = new Point2D.Double(x,y);
      }
      
      GeneralPath s = new GeneralPath();
      s.moveTo((float) getWidth()/2 + p[0].getX(), (float) getHeight()/2 - p[0].getY());
      for (int i = 1; i < p.length; i++)
      {
         s.lineTo((float) getWidth()/2 + p[i].getX(), (float) getHeight()/2 - p[i].getY());
      }
      s.closePath();
      g2.draw(s);
   }
}
