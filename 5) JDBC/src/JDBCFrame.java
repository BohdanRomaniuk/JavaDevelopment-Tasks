import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
 
public class JDBCFrame extends JFrame implements ActionListener
{
	private JLabel connectionStringLabel = new JLabel("Connection string:"); 
	private JTextField connectionString = new JTextField("jdbc:postgresql://127.0.0.1:5432/shop");
	private JTree tree = new JTree();
	private JTextArea query = new JTextArea();
	
	private JButton connectButton = new JButton("Connect");
	private JButton evaluateButton = new JButton("Evaluate query to the database");

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMI = new JMenu("Database");
    private JMenuItem openFileMenu = new JMenuItem("Connect");
    private JMenuItem openDirMenu =new JMenuItem("Disconect");
    private JSeparator separator = new JSeparator();
    private JMenuItem exitMenu = new JMenuItem("Exit");
    
    private JMenu tableActionsMI = new JMenu("Table");
    private JMenuItem insertMenu = new JMenuItem("Insert");
    private JMenuItem editMenu = new JMenuItem("Edit");
    private JMenuItem deleteMenu = new JMenuItem("Delete");
    
    private JMenu helpMI = new JMenu("Help");
    private JMenuItem aboutMenu = new JMenuItem("About");
    
    
    //Structure tree
    DefaultTreeModel treeModel = (DefaultTreeModel)tree.getModel();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
    
    //Results table
    DefaultTableModel model = new DefaultTableModel(); 
    JTable table = new JTable(model);
    
    private JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    private JPanel topPanel = new JPanel(new BorderLayout(10, 10));
    private JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
    private JPanel centerLeftPanel = new JPanel(new BorderLayout(10, 10));
    private JPanel centerRightPanel = new JPanel(new GridLayout(2, 1, 10, 10));
    
    private JPanel queryPanel = new JPanel(new BorderLayout(10,10));
    private JPanel resultsPanel = new JPanel(new BorderLayout(10,10));
    
    public JDBCFrame() {

        super("Working with db");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 500));
        setResizable(false);

        setLayout(new BorderLayout(10, 10));

        openFileMenu.addActionListener(this);
        openDirMenu.addActionListener(this);
        exitMenu.addActionListener(this);
        
        fileMI.add(openFileMenu);
        fileMI.add(openDirMenu);
        fileMI.add(separator);
        fileMI.add(exitMenu);
        
        insertMenu.addActionListener(this);
        editMenu.addActionListener(this);
        deleteMenu.addActionListener(this);

        tableActionsMI.add(insertMenu);
        tableActionsMI.add(separator);
        tableActionsMI.add(editMenu);
        tableActionsMI.add(deleteMenu);

        aboutMenu.addActionListener(this);
        helpMI.add(aboutMenu);

        menuBar.add(fileMI);
        menuBar.add(tableActionsMI);
        menuBar.add(helpMI);

        setJMenuBar(menuBar);
        
        connectButton.addActionListener(this);
        evaluateButton.addActionListener(this);
                
        topPanel.add(connectionStringLabel, BorderLayout.WEST);
        topPanel.add(connectionString, BorderLayout.CENTER);
        topPanel.add(connectButton, BorderLayout.EAST);
        
        centerLeftPanel.setPreferredSize(new Dimension(160, 600));
        centerLeftPanel.add(new JLabel("Structure:"), BorderLayout.NORTH);
        
        root.setUserObject("No Database");
        root.removeAllChildren();
        treeModel.reload(root);
        
        JScrollPane scrollingTree = new JScrollPane();
        scrollingTree.setViewportView(tree);
        centerLeftPanel.add(scrollingTree, BorderLayout.CENTER);
        
        JPanel queryLinePanel = new JPanel(new BorderLayout(10,10));
        queryLinePanel.add(new JLabel("Query:"), BorderLayout.WEST);
        queryLinePanel.add(evaluateButton, BorderLayout.EAST);
        
        queryPanel.add(queryLinePanel, BorderLayout.NORTH);
        queryPanel.add(query, BorderLayout.CENTER);
        
        JPanel scrollingTable = new JPanel(new BorderLayout());
        scrollingTable.add(new JScrollPane(table));
        scrollingTable.add(table.getTableHeader(), BorderLayout.NORTH);
        scrollingTable.add(table, BorderLayout.CENTER);
        resultsPanel.add(new JLabel("Results:"), BorderLayout.NORTH);
        resultsPanel.add(scrollingTable, BorderLayout.CENTER);
        
        
        centerRightPanel.add(queryPanel);
        centerRightPanel.add(resultsPanel);
        
        centerPanel.add(centerLeftPanel, BorderLayout.WEST);
        centerPanel.add(centerRightPanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);
    }
    
    private Connection conn;
    private java.sql.Statement stat;
    private DatabaseMetaData meta;
    
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource()==connectButton)
    	{
    		System.setProperty("jdbc.drivers", "org.postgresql.Driver");
    		String url = connectionString.getText();
    		Properties props = new Properties();
    		props.setProperty("user","postgres");
    		props.setProperty("password","1234");
    		try {
				conn = DriverManager.getConnection(url, props);
				meta = conn.getMetaData();
				root.setUserObject(conn.getCatalog());
				root.removeAllChildren();
				ResultSet tables = meta.getTables(null, null, null, new String[] { "TABLE" });
				while (tables.next())
				{
					DefaultMutableTreeNode dbTable = new DefaultMutableTreeNode(tables.getString(3));
					ResultSet columns = meta.getColumns(null, null, tables.getString(3), null);
					while(columns.next())
					{
						String name = columns.getString("COLUMN_NAME");
						dbTable.add(new DefaultMutableTreeNode(name));
					}
					root.add(dbTable);
				}
	            tables.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            treeModel.reload(root);
    	}
    	else if(e.getSource()==evaluateButton)
    	{
    		try {
				stat = conn.createStatement();
				ResultSet rs = stat.executeQuery(query.getText());
				ResultSetMetaData rsmd = rs.getMetaData();
				int columns = rsmd.getColumnCount();
				for(int i=0; i<columns; ++i)
				{
					model.addColumn(rsmd.getColumnName(i+1));
				}

				while(rs.next())
				{
					Object[] values = new Object[columns];
					for(int i=0; i<columns; ++i)
					{
						values[i] = rs.getString(i+1);
					}
					model.addRow(values);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
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
}