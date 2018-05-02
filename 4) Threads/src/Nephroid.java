import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

public class Nephroid extends JPanel implements Runnable {
	private Paint paint;
	private Stroke stroke;
	private float step = (float)0.01;
    private Vector<Point2D> points = new Vector<Point2D>();
    private int delay;
    
	public Nephroid(int _delay)
	{
		paint = Color.red;
		stroke = new BasicStroke(1);
		delay = _delay;
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
	      
	    //Set random values
	    g2.setPaint(paint);
	    g2.setStroke(stroke);
	     
	    if(points.size()!=0)
	    {
	    	GeneralPath s = new GeneralPath();
	    	s.moveTo((float) getWidth()/2 + points.firstElement().getX(), (float) getHeight()/2 - points.firstElement().getY());
	    	for(Point2D p : points)
	    	{
	    		s.lineTo((float) getWidth()/2 + p.getX(), (float) getHeight()/2 - p.getY());
	    	}
	    	s.closePath();
	    	g2.draw(s);
	    }
	   }
	
	public void run()
	{
		Lock nephroidLock = new ReentrantLock();
		nephroidLock.lock();
		try
		{
			int smallest = getHeight()<getWidth()?getHeight():getWidth();
	    	int r = smallest/10;
	    	int alpha = 0;
	    	for(float phi=0; phi<=2*Math.PI; phi+=step)
	    	{
	    		float x = (float) (3*r*Math.cos(phi) - r*Math.cos(alpha+3*phi));
	    		float y = (float) (3*r*Math.sin(phi) - r*Math.sin(alpha+3*phi));
	    		points.add(new Point2D.Double(x,y));
	    		repaint();
				Thread.sleep(delay);
	    	}
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			nephroidLock.unlock();
		}
	}
}
