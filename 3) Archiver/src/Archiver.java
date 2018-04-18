import java.awt.*;
import java.awt.event.*;
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
      setTitle("Java архіватор");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      ButtonPanel panel = new ButtonPanel();
      add(panel);
   }

   public static final int DEFAULT_WIDTH = 600;
   public static final int DEFAULT_HEIGHT = 400;  
}

class ButtonPanel extends JPanel
{  
   public ButtonPanel()
   {  

      JButton chooseFileButton= new JButton("Вибрати файл");
      JButton chooseDirButton = new JButton("Вибрати папку");
      JButton saveArchive = new JButton("Зберегти архів");
      JButton addFileToArchive = new JButton("Додати файл до архіву");


      add(chooseFileButton);
      add(chooseDirButton);
      add(saveArchive);
      add(addFileToArchive);

      ChooseAction chooseFileAction = new ChooseAction("file");
      ChooseAction chooseDirAction = new ChooseAction("dir");
      ColorAction redAction = new ColorAction(Color.RED);

      chooseFileButton.addActionListener(chooseFileAction);
      chooseDirButton.addActionListener(chooseDirAction);
      saveArchive.addActionListener(redAction);
   }

   private class ChooseAction implements ActionListener
   {
	   private String type;
	   public ChooseAction(String _type)
	   {
		   type = _type;
	   }
	   
	   public void actionPerformed(ActionEvent event)
	   {
		   if(type=="file")
		   {
			   FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
			   dialog.setMode(FileDialog.LOAD);
			   dialog.setVisible(true);
			   String file = dialog.getFile();
			   System.out.println(file + " chosen.");
		   }
		   else if(type=="dir")
		   {
			   JFileChooser f = new JFileChooser();
		        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		        f.showSaveDialog(null);

		        System.out.println(f.getCurrentDirectory());
		        System.out.println(f.getSelectedFile());
		   }
	   }
   }
   
   private class ColorAction implements ActionListener
   {  
      public ColorAction(Color c)
      {  
         backgroundColor = c;
      }

      public void actionPerformed(ActionEvent event)
      {  
         setBackground(backgroundColor);
      }

      private Color backgroundColor;
   }
}



