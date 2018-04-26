import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
 
public class ThreadsFrame extends JFrame implements ActionListener
{
	private JLabel label1 = new JLabel("Потік №1 (Малювання нефроїди):");
    private JButton start1 = new JButton("Старт 1");
    private JButton pause1 = new JButton("Пауза 1");
    
    private JLabel label2 = new JLabel("Потік №2:");
    private JButton start2 = new JButton("Старт 2");
    private JButton pause2 = new JButton("Пауза 2");
    private JLabel result2 = new JLabel("Результат 2-го потоку");
    
    private JLabel label3 = new JLabel("Потік №3:");
    private JButton start3 = new JButton("Старт 3");
    private JButton pause3 = new JButton("Пауза 3");
    
    private JPanel mainPanel = new JPanel(new GridLayout(3,1,10,10));
    private JPanel panel1 = new JPanel(new BorderLayout(10, 10));
    private JPanel panel2 = new JPanel(new BorderLayout(10, 10));
    private JPanel panel3 = new JPanel(new BorderLayout(10, 10));
    
    public ThreadsFrame() {

        super("JAVA Потоки");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(700, 800));
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel buttons1 = new JPanel(new GridLayout(1,3,10,10));
        label1.setForeground(Color.RED);
        buttons1.add(label1);
        buttons1.add(start1);
        buttons1.add(pause1);
        panel1.add(buttons1, BorderLayout.NORTH);
        panel1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        
        JPanel buttons2 = new JPanel(new GridLayout(1,3,10,10));
        label2.setForeground(Color.RED);
        buttons2.add(label2);
        buttons2.add(start2);
        buttons2.add(pause2);
        panel2.add(buttons2, BorderLayout.NORTH);
        panel2.add(result2, BorderLayout.CENTER);
        panel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        
        JPanel buttons3 = new JPanel(new GridLayout(1,3,10,10));
        label3.setForeground(Color.RED);
        buttons3.add(label3);
        buttons3.add(start3);
        buttons3.add(pause3);
        panel3.add(buttons3, BorderLayout.NORTH);

        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        
    } 
}