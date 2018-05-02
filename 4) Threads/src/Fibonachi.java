import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;

public class Fibonachi implements Runnable
{
	private JLabel label;
	private int delay;
	
	public Fibonachi(JLabel _label, int _delay) 
	{ 
	    label = _label;
	    delay = _delay;
	}
	
	public void run()
	{
	   Lock fibonachiLook = new ReentrantLock();
	   fibonachiLook.lock();
	   try
	   {
		    long prev1=0;
   			long prev2=1;
   			long next = 0;
   			while(next<Long.MAX_VALUE)
   			{
   				next = prev1+prev2;
   				prev1 = prev2;
   				prev2 = next;
   				label.setText(Long.toString(next));
   				Thread.sleep(delay);
   			}
	   }
	   catch (InterruptedException e)
	   {       
		   System.err.println(e.getMessage());
	   }
	   finally
	   {
		   fibonachiLook.unlock();
	   }
	}
}
