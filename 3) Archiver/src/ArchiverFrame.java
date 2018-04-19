import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
 
public class ArchiverFrame extends JFrame implements ActionListener
{
    private JButton chooseFileButton= new JButton("Вибрати файл");
    private JButton chooseDirButton = new JButton("Вибрати папку");
    private JButton saveArchive = new JButton("Заархівувати");
    private JButton openArchive = new JButton("Відкрити архів");
    private JButton deArchivate = new JButton("Розархівувати");
    private JButton appendFileToArchive = new JButton("Додати файл в архів");

    
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMI = new JMenu("Файл");
    private JMenuItem openFileMenu = new JMenuItem("Вибрати файл");
    private JMenuItem openDirMenu =new JMenuItem("Вибрати папку");
    private JSeparator separator = new JSeparator();
    private JMenuItem exitMenu = new JMenuItem("Вийти");
    
    private JMenu archiveActionsMI = new JMenu("Дії з архівом");
    private JMenuItem saveArchiveMenu = new JMenuItem("Заархівувати");
    private JMenuItem openArchiveMenu = new JMenuItem("Відкрити архів");
    private JMenuItem deArchivateMenu = new JMenuItem("Розархівувати");
    private JMenuItem appendFileToArchiveMenu = new JMenuItem("Додати файл в архів");
    
    private JMenu helpMI = new JMenu("Інформація");
    private JMenuItem aboutMenu = new JMenuItem("Про програму");
    
    
    DefaultTableModel model = new DefaultTableModel(); 
    JTable table = new JTable(model);
	
    
    private JLabel statusLine = new JLabel("Рядок стану");
    
    private JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    private JPanel panel2 = new JPanel(new BorderLayout());
    private JPanel panel3 = new JPanel(new GridLayout(1, 2, 10, 10));
    private JPanel panel1 = new JPanel(new GridLayout(2, 3, 10, 10));

    //FILE LOCATION!!!!
    private String from;
    
    public ArchiverFrame() {

        super("JAVA Архіватор");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 400));
        setResizable(false);

        setLayout(new BorderLayout(10, 10));

        openFileMenu.addActionListener(this);
        openDirMenu.addActionListener(this);
        exitMenu.addActionListener(this);
        
        fileMI.add(openFileMenu);
        fileMI.add(openDirMenu);
        fileMI.add(separator);
        fileMI.add(exitMenu);
        
        saveArchiveMenu.addActionListener(this);
        openArchiveMenu.addActionListener(this);
        deArchivateMenu.addActionListener(this);
        appendFileToArchiveMenu.addActionListener(this);

        archiveActionsMI.add(saveArchiveMenu);
        archiveActionsMI.add(separator);
        archiveActionsMI.add(openArchiveMenu);
        archiveActionsMI.add(deArchivateMenu);
        archiveActionsMI.add(appendFileToArchiveMenu);

        aboutMenu.addActionListener(this);
        helpMI.add(aboutMenu);

        menuBar.add(fileMI);
        menuBar.add(archiveActionsMI);
        menuBar.add(helpMI);

        setJMenuBar(menuBar);
        
        panel2.add(table, BorderLayout.CENTER);
        model.addColumn("Назва файлу"); 
        model.addColumn("Розмір файлу");
        model.addRow(new Object[]{"Назва файлу", "Розмір файлу"});
        
        panel3.add(statusLine);
        
        chooseFileButton.addActionListener(this);
        chooseDirButton.addActionListener(this);
        saveArchive.addActionListener(this);
        openArchive.addActionListener(this);
        deArchivate.addActionListener(this);
        appendFileToArchive.addActionListener(this);

        panel1.add(chooseFileButton);
        panel1.add(chooseDirButton);
        panel1.add(saveArchive);
        panel1.add(openArchive);
        panel1.add(deArchivate);
        panel1.add(appendFileToArchive);

        mainPanel.add(panel1, BorderLayout.NORTH);
        mainPanel.add(panel2, BorderLayout.CENTER);
        mainPanel.add(panel3, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == chooseFileButton || e.getSource()==openFileMenu)
        {
           removeAllRows();
           statusLine.setText("Відкривання файлу для архівації");
     	   FileDialog dialog = new FileDialog((Frame)null, "Виберіть файл");
 		   dialog.setMode(FileDialog.LOAD);
 		   dialog.setVisible(true);
 		   from = dialog.getDirectory()+dialog.getFile();
 		   from = from.replace("\\", "\\\\");
 		   System.out.println("From file:"+from);
 		   File toOpen = new File(from);
 		   model.addRow(new Object[]{from.substring(from.lastIndexOf("\\\\")+2), convertToFileSize(toOpen.length(),true)});
 		   
        }
        else if(e.getSource() == chooseDirButton || e.getSource()==openDirMenu)
        {
           removeAllRows();
           statusLine.setText("Відкривання папки для архівації");
     	   JFileChooser f = new JFileChooser();
 	       f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
 	       f.showOpenDialog(null);
 	       from = f.getSelectedFile().toString();
 	       File folder = new File(from);
 	       File[] listOfFiles = folder.listFiles();
 	         for (int i = 0; i < listOfFiles.length; i++) {
 	             model.addRow(new Object[]{listOfFiles[i].getName(), convertToFileSize(listOfFiles[i].length(),true)});
 	         }
 	       System.out.println("From dir:"+from);
        }
        else if(e.getSource() == saveArchive || e.getSource()==saveArchiveMenu)
        {
           statusLine.setText("Архівація");
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
 		   to = fc.getSelectedFile().toString();

 		   boolean allowToOwerrite = true;
 		   // Make sure not to overwrite
 		   if ((new File(to)).exists()) 
 		   {
 		      int dialogResult = JOptionPane.showConfirmDialog (null, to+"\nТакий файл уже існує\nПерезаписати?","Попередження",JOptionPane.YES_NO_OPTION);
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
 			   }
 			   catch(IOException ex)
 			   {
 			 	  System.err.print(ex.getMessage());
 			   }
 			   finally
 			   {
 				   JOptionPane.showMessageDialog(null, "Файл "+to+" успішно збережно!", "Статус зберігання", JOptionPane.INFORMATION_MESSAGE);
 				   statusLine.setText("Архівацію завершено!");
 			   }
 		   }
        }
        else if(e.getSource()==openArchive || e.getSource()==openArchiveMenu)
        {
           statusLine.setText("Відкривання архіву");
     	   JFileChooser fc = new JFileChooser();
     	   fc.showOpenDialog(null);
     	   from = fc.getSelectedFile().toString();
     	   JOptionPane.showMessageDialog(null, "Файл "+from+" відкрито!\nТепер можете його розархівувати", "Статус відривання", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource()==deArchivate || e.getSource()==deArchivateMenu)
        {
           statusLine.setText("Розархівація");
     	   String extension = from.substring(from.lastIndexOf(".")+1);
     	   String to = from.substring(0,from.lastIndexOf("."));
     	   System.out.println("From: "+from);
     	   JFileChooser fc = new JFileChooser();
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
 	       to = fc.getSelectedFile().toString();
 	       System.out.println("To: "+to);
 	       System.out.println("Extension: "+extension);
 	       try
 	       {
 	    	   if(extension.equals("gz"))
 	    	   {
 	    		   Compress.ungzipFile(from, to);
 	    	   }
 	    	   else if(extension.equals("zip"))
 	    	   {
 	    		   Compress.unzipDirectory(from, to);
 	    	   }
 	       }
 	       catch(IOException ex)
 	       {
 	    	   System.err.println(ex.getMessage());
 	       }
 	       finally
 	       {
 	    	   JOptionPane.showMessageDialog(null, to+" успішно збережено!", "Статус зберігання", JOptionPane.INFORMATION_MESSAGE);
 	    	   statusLine.setText("Розархівацію завершено!");
 	       }
        }
        else if(e.getSource()==appendFileToArchive || e.getSource()==appendFileToArchiveMenu)
        {
        	Map<String, String> env = new HashMap<>(); 
        	env.put("create", "true");
        	Path path = Paths.get(from);
        	URI uri = URI.create("jar:" + path.toUri());
        	try (FileSystem fs = FileSystems.newFileSystem(uri, env))
        	{
        	    Path nf = fs.getPath("new.txt");
        	    try (Writer writer = Files.newBufferedWriter(nf, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
        	        writer.write("hello");
        	    }
        	}
        	catch(IOException ex)
        	{
        		System.err.println(ex.getMessage());
        	}
        }
        else if(e.getSource()==aboutMenu)
        {
        	AboutFrame frame = new AboutFrame("Створив студент групи ПМі-33 Романюк Богдан!");
            frame.setVisible(true);
        }
        else if(e.getSource()==exitMenu)
        {
        	System.exit(0);
        }
    } 
    
    public void removeAllRows()
    {
    	int rowCount = model.getRowCount();
    	for (int i = rowCount - 1; i >= 0; i--) 
    	{
    	    model.removeRow(i);
    	}
    	model.addRow(new Object[]{"Назва файлу", "Розмір файлу"});
    }
    
    public String convertToFileSize(long bytes, boolean si) 
    {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}