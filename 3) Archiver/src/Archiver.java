import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class Archiver
{  
   public static void main(String[] args)
   {  
      ButtonFrame frame = new ButtonFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

class ButtonFrame extends JFrame
{
   public ButtonFrame()
   {
      setTitle("Java ���������");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      ButtonPanel panel = new ButtonPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 600;
   public static final int DEFAULT_HEIGHT = 400;  
}

class ButtonPanel extends JPanel implements ActionListener 
{  
	JButton chooseFileButton= new JButton("������� ����");
    JButton chooseDirButton = new JButton("������� �����");
    JButton saveArchive = new JButton("������������");
    JButton openArchive = new JButton("³������ �����");
    JButton deArchivate = new JButton("�������������");
    
    String from;
    
    public ButtonPanel()
    {  
      add(chooseFileButton);
      add(chooseDirButton);
      add(saveArchive);
      add(openArchive);
      add(deArchivate);

      chooseFileButton.addActionListener(this);
      chooseDirButton.addActionListener(this);
      saveArchive.addActionListener(this);
      openArchive.addActionListener(this);
      deArchivate.addActionListener(this);
   }
   
   public void actionPerformed(ActionEvent e)
   {
       if(e.getSource() == chooseFileButton)
       {
    	   FileDialog dialog = new FileDialog((Frame)null, "������� ����");
		   dialog.setMode(FileDialog.LOAD);
		   dialog.setVisible(true);
		   from = dialog.getDirectory()+dialog.getFile();
		   from = from.replace("\\", "\\\\");
		   System.out.println("From file:"+from);
       }
       else if(e.getSource() == chooseDirButton)
       {
    	   JFileChooser f = new JFileChooser();
	       f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
	       f.showOpenDialog(null);
	       from = f.getSelectedFile().toString();
	       System.out.println("From dir:"+from);
       }
       else if(e.getSource() == saveArchive)
       {
		   String to = "";
		   File f = new File(from);
		   boolean directory = f.isDirectory();
		   
		   JFileChooser fc = new JFileChooser();
	       if (directory)
		   {
	    	   to = from + ".zip";
		   }
		   else
		   {
			   to = from + ".gz";
		   }
	       try
	       {
	    	   File filen = new File(new File(to).getCanonicalPath());
	    	   fc.setSelectedFile(filen);
	       }
	       catch(IOException ex)
	       {
	    	   System.err.println(ex.getMessage());
	       }
	       fc.showSaveDialog(null);
		   

		   boolean allowToOwerrite = true;
		   // Make sure not to overwrite
		   if ((new File(to)).exists()) 
		   {
		      int dialogResult = JOptionPane.showConfirmDialog (null, to+"\n����� ���� ��� ����\n������������?","������������",JOptionPane.YES_NO_OPTION);
		      if(dialogResult == JOptionPane.NO_OPTION){
		    	  allowToOwerrite = false;
		      }
		   }

		   if(allowToOwerrite)
		   {
			   try
			   {
				   if (directory)
				   {
					   Compress.zipDirectory(from, to);
				   }
				   else
				   {
					   Compress.gzipFile(from, to);
				   }
				   JOptionPane.showMessageDialog(null, "���� "+to+" ������ ��������!", "������ ���������", JOptionPane.INFORMATION_MESSAGE);
			   }
			   catch(IOException ex)
			   {
			 	  System.err.print(ex.getMessage());
			   }
		   }
       }
       else if(e.getSource()==openArchive)
       {
    	   JFileChooser fc = new JFileChooser();
    	   fc.showOpenDialog(null);
    	   from = fc.getSelectedFile().toString();
    	   JOptionPane.showMessageDialog(null, "���� "+from+" ������!\n����� ������ ���� �������������", "������ ���������", JOptionPane.INFORMATION_MESSAGE);
       }
       else if(e.getSource()==deArchivate)
       {
    	   
       }
   } 
}