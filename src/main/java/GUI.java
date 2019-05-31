import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class GUI extends JFrame
{
	private final int DEFAULT_WIDTH = 1000;
	private final int DEFAULT_HEIGHT = DEFAULT_WIDTH / 12 * 9;
	
	private final String DEFAULT_PATH = "C:\\Users\\Andrew Sidorchuk\\Desktop\\Worship Team";
	private final String DEFAULT_PATH_TO_SAVE_LYRICS = "C:\\Users\\Andrew Sidorchuk\\Desktop\\Worship Team\\Z Binder\\Printable lyrics";
	private final String DEFAULT_PATH_TO_SAVE_PDFS = "C:\\Users\\Andrew Sidorchuk\\Desktop\\Worship Team\\Public Folder for Youth";
	private File directoryToReadFrom = null;
	private File directoryToSaveLyricsTo = null;
	
	private final JFileChooser fileChooser = new JFileChooser(DEFAULT_PATH);
	private JTextArea fileList, fieldForListing_SavedLyrics, fieldForListing_PDFs;
	private JTextPane progressInformation;
	private JTextField fieldForPath_Lyrics, fieldForPath_PDFs, progressStats;
	private JScrollPane scrollPaneForFileList;
	
	private String pathToSaveLyrics = DEFAULT_PATH;
	private String pathToSavePDFs = DEFAULT_PATH_TO_SAVE_PDFS;
	
	private boolean convertToPDFEnabled = true;
	private boolean uploadLyricsToGoogleEnabled = true;
	private boolean uploadPDFsToGoogleEnabled = true;
	
	private JButton convertButton;
	
	private Color pinkText = new Color(245, 215, 224);
	private Color darkBlueBackground = new Color(38, 41, 84);
	
	private Color panelBlue = new Color(93, 87, 107);
	private Color darkBlue = new Color(0, 22, 40);
	private Color lightBlue = new Color(166, 171, 255);
	private Color pink = new Color(247, 86, 124);
	private JProgressBar progressBar;
	
	Task task;
	
	class Task extends SwingWorker<Void, Void>
	{

		@Override
		protected Void doInBackground() throws Exception 
		{
			File [] directoryFiles = directoryToReadFrom.listFiles();
			String update = "";
			String songPath;
			
			setProgress(0);
			
			for(int i = 0; i < directoryFiles.length; i++)
			{
				
				update = directoryFiles[i].getName() + "\n";
				updateProgressField(" Converting to .docx ->\t", 0);
				updateProgressField(update, 1);

				songPath = Main.convertTextToWord(pathToSaveLyrics, directoryFiles[i]);
				
				if(uploadLyricsToGoogleEnabled)
				{
					updateProgressField(" Uploading to Google ->\t", 2);
					Main.uploadToGoogle(songPath);
					updateProgressField(update, 1);
				}
					
				setProgress((int)((double)((i + 1) / (double)directoryFiles.length) * 100));
				updateStats(i + 1, directoryFiles.length);
			}
			
			return null;
		}
		
		@Override
        public void done() 
		{
            Toolkit.getDefaultToolkit().beep();
            convertButton.setEnabled(true);
            progressBar.setStringPainted(false);
        }
		
	}
	public GUI()
	{
		setTitle("Worship Song Helper");
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setMaximumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setBackground(darkBlue);
		setLayout(new BorderLayout(8, 2));
		add(setUpButtonPanel(), BorderLayout.WEST);
		setJMenuBar(setUpMenuBar());
		
		JPanel saveOnLocalComputerPanel = new JPanel();
		saveOnLocalComputerPanel.setLayout(new GridLayout(2, 1, 3, 10));
		saveOnLocalComputerPanel.setBackground(darkBlue);
		
		saveOnLocalComputerPanel.add(setup_saveLyricsToFolderPanel());
		saveOnLocalComputerPanel.add(setup_savePDFsToFolderPanel());
		
		add(saveOnLocalComputerPanel, BorderLayout.CENTER);
		add(east(), BorderLayout.EAST);
		add(setup_progressBar(), BorderLayout.SOUTH);
		setVisible(true);
		
	}
	
	public JPanel setUpButtonPanel()
	{
		JPanel panel = new JPanel();
		JPanel titlePanel = new JPanel();
		ImageIcon logo = null;
		
		logo = new ImageIcon(getClass().getResource("logo4.png"));
		
		//Makes the panel see through
		titlePanel.setOpaque(false);
		
		//Create the app name
		JTextField appNameOne = new JTextField("Song");
		JTextField appNameTwo = new JTextField("Helper");
		
		//Make the text fields see through and without borders and uneditable
		appNameOne.setOpaque(false);
		appNameTwo.setOpaque(false);
		appNameOne.setBorder(null);
		appNameTwo.setBorder(null);
		appNameOne.setEditable(false);
		appNameTwo.setEditable(false);
		
		//Add the app name to the title panel
		titlePanel.add(appNameOne);
		titlePanel.add(appNameTwo);

		//Change the color and font of the app name
//		appNameOne.setForeground(lightBlue);
		appNameOne.setForeground(pink);
		appNameOne.setFont(new Font("Calibri", Font.BOLD, 35));
		appNameTwo.setForeground(Color.WHITE);
		appNameTwo.setFont(new Font("Calibri", Font.PLAIN, 30));
		
		//Add the title pane to the panel
		panel.add(titlePanel);
		panel.add(new JLabel(logo));
		
		//Set panel colors
		panel.setBackground(darkBlue);
		panel.setForeground(Color.WHITE);
		
		//Set panel layout to BoxLayout
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//Setup check boxes with fonts and colors
		JCheckBox useDefaults = new JCheckBox("Use all defaults");
		useDefaults.setBackground(darkBlue);
		useDefaults.setForeground(Color.WHITE);
		
		JCheckBox convertToPDF = new JCheckBox("Convert Word file to PDF");
		convertToPDF.setBackground(darkBlue);
		convertToPDF.setForeground(Color.WHITE);
		
		JCheckBox uploadLyric = new JCheckBox("Upload lyrics to Google Drive");
		uploadLyric.setBackground(darkBlue);
		uploadLyric.setForeground(Color.WHITE);
		
		JCheckBox uploadPDF = new JCheckBox("Upload PDFs to Google Drive");
		uploadPDF.setBackground(darkBlue);
		uploadPDF.setForeground(Color.WHITE);
		
		//Add listeners to check boxes
		useDefaults.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					System.out.println(e.getStateChange());
					setAllDefaults();
				}

			}
		});
		
		convertToPDF.setSelected(true);
		convertToPDF.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					convertToPDFEnabled = true;
					fieldForPath_PDFs.setEnabled(true);
					System.out.println("Conversion to PDF enabled");
				}
				
				else
				{
					convertToPDFEnabled = false;
					fieldForPath_PDFs.setEnabled(false);
					System.out.println("Conversion to PDF disabled");
				}
				
			}
		});
		
		uploadLyric.setSelected(true);
		uploadLyric.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					uploadLyricsToGoogleEnabled = true;
					System.out.println("Upload lyrics enabled");
				}
				
				else
				{
					uploadLyricsToGoogleEnabled = false;
					System.out.println("Upload lyrics disabled");
				}
				
			}
		});
		
		uploadPDF.setSelected(true);
		uploadPDF.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					uploadPDFsToGoogleEnabled = true;
					System.out.println("Upload PDFs enabled");
				}
				
				else
				{
					uploadPDFsToGoogleEnabled = false;
					System.out.println("Upload PDFs disabled");
				}
				
			}
		});
		
		//Add check boxes to panel
		panel.add(useDefaults);
		panel.add(convertToPDF);
		panel.add(uploadLyric);
		panel.add(uploadPDF);
		
		return panel;
	}
	
	public JMenuBar setUpMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Actions");
		JMenuItem firstItem = new JMenuItem("Choose folder to read songs from");
		
		fileList = new JTextArea();
		
		firstItem.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setApproveButtonText("Choose");
				
				int returnValue = fileChooser.showOpenDialog(GUI.this);		
				
				if(returnValue == 0)
				{
					directoryToReadFrom = fileChooser.getSelectedFile();
					System.out.println("Reading songs from : " + directoryToReadFrom.getPath());
					
					convertButton.setEnabled(true);

					File [] allFilesInDirectory = directoryToReadFrom.listFiles();
					
					fileList.setText("");
					for(File f : allFilesInDirectory)
						fileList.append(f.getName() + "\n");
					
				}
				
			}
		});
		
		menu.add(firstItem);
		
		menuBar.add(menu);
		
		return menuBar;
	}
	
	public JPanel buildSongsNotYetUploadedPanel()
	{
		JPanel panel = new JPanel();
		
		fileList = new JTextArea("Files not loaded yet", 5, 30);
		scrollPaneForFileList = new JScrollPane(fileList);
		panel.add(scrollPaneForFileList);
		
		return panel;
	}
	
	public JPanel setup_savePDFsToFolderPanel()
	{
		JPanel panel = new JPanel();
		JPanel northPanel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		northPanel.setLayout(new GridLayout(3, 1));
		
		fieldForPath_PDFs = new JTextField(DEFAULT_PATH_TO_SAVE_PDFS);
		fieldForPath_PDFs.setForeground(Color.WHITE);
		fieldForPath_PDFs.setBackground(panelBlue);
		fieldForPath_PDFs.setFont(new Font("Calibri", Font.ITALIC, 14));
		
		fieldForListing_PDFs = new JTextArea();
		
		TitledBorder border = BorderFactory.createTitledBorder("Folder preview");
		border.setTitleFont(new Font("Calibri", Font.PLAIN, 10));
		border.setTitlePosition(TitledBorder.BOTTOM);
		border.setTitleColor(Color.WHITE);
		
		fieldForListing_PDFs.setBorder(border);
		fieldForListing_PDFs.setOpaque(false);
		fieldForListing_PDFs.setForeground(Color.WHITE);
		
		fieldForPath_PDFs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("Enter pressed");
				
				fieldForListing_PDFs.setText("");
				if(new File(fieldForPath_PDFs.getText()).isDirectory())
				{
					fieldForPath_PDFs.setFont(new Font("Calibri", Font.PLAIN, 14));
				
					File directory = new File(fieldForPath_PDFs.getText());
					
					File [] allFilesInDirectory = directory.listFiles();

					fieldForListing_PDFs.setForeground(Color.WHITE);
					for(File f : allFilesInDirectory)
						fieldForListing_PDFs.append(f.getName() + "\n");
				}
				
				else
				{
					fieldForListing_PDFs.setText("Not a valid directory!");
					fieldForListing_PDFs.setForeground(Color.RED);
				}
				
			}
		});
		
		JTextField OR = new JTextField("OR");
		OR.setForeground(lightBlue);
		OR.setEditable(false);
		OR.setOpaque(false);
		OR.setBorder(null);
		
		JButton button = new JButton("Search");
		button.setBackground(darkBlue);
		button.setForeground(darkBlue);
		button.setBorder(BorderFactory.createLineBorder(lightBlue));
		button.setMaximumSize(new Dimension(40,40));
		button.setPreferredSize(new Dimension(40, 40));
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileExplorer = new JFileChooser(pathToSavePDFs);
				fileExplorer.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileExplorer.setApproveButtonText("Choose");
			
				int returnValue = fileExplorer.showOpenDialog(GUI.this);
				
				fieldForListing_PDFs.setText("");
				
				if(returnValue == 0)
				{
					directoryToSaveLyricsTo = fileExplorer.getSelectedFile();
					
					fieldForPath_PDFs.setText(directoryToSaveLyricsTo.getPath());
					fieldForPath_PDFs.setFont(new Font("Calibri", Font.PLAIN, 14));
					
					File [] allFilesInDirectory = directoryToSaveLyricsTo.listFiles();

					fieldForListing_SavedLyrics.setForeground(Color.WHITE);
					for(File f : allFilesInDirectory)
						fieldForListing_PDFs.append(f.getName() + "\n");
				}
				
			}
		});
		
		northPanel.add(button);
		northPanel.add(OR);
		northPanel.add(fieldForPath_PDFs);
		northPanel.setBackground(darkBlue);
		northPanel.setOpaque(true);
		
		panel.add(northPanel);
		panel.add(fieldForListing_PDFs);
		
		TitledBorder tb = new TitledBorder("PDFs destination on local computer");
		tb.setTitleColor(lightBlue);
		panel.setBorder(tb);
		panel.setBackground(darkBlue);
		panel.setOpaque(true);
		
		return panel;
	}
	
	public JPanel setup_saveLyricsToFolderPanel()
	{
		JPanel northPanel = new JPanel();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		northPanel.setLayout(new GridLayout(3, 1));
		northPanel.setBackground(darkBlue);
		
		fieldForPath_Lyrics = new JTextField(DEFAULT_PATH_TO_SAVE_LYRICS);
		fieldForPath_Lyrics.setFont(new Font("Calibri", Font.ITALIC, 14));
		
		fieldForPath_Lyrics.setForeground(Color.WHITE);
		fieldForPath_Lyrics.setBackground(panelBlue);
		
		fieldForListing_SavedLyrics = new JTextArea();
		
		TitledBorder border = BorderFactory.createTitledBorder("Folder preview");
		border.setTitleFont(new Font("Calibri", Font.PLAIN, 10));
		border.setTitlePosition(TitledBorder.BOTTOM);
		border.setTitleColor(Color.WHITE);
		
		fieldForListing_SavedLyrics.setBorder(border);
		fieldForListing_SavedLyrics.setOpaque(false);
		fieldForListing_SavedLyrics.setForeground(Color.WHITE);
		
		fieldForPath_Lyrics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Enter pressed");
				
				fieldForListing_SavedLyrics.setText("");
				if(new File(fieldForPath_Lyrics.getText()).isDirectory())
				{
					fieldForPath_Lyrics.setFont(new Font("Calibri", Font.PLAIN, 14));
					
					File directory = new File(fieldForPath_Lyrics.getText());
					
					pathToSaveLyrics = fieldForPath_Lyrics.getText();
					
					File [] allFilesInDirectory = directory.listFiles();

					fieldForListing_SavedLyrics.setForeground(Color.WHITE);
					
					for(File f : allFilesInDirectory)
						fieldForListing_SavedLyrics.append(f.getName() + "\n");
				}
				
				else
				{
					fieldForListing_SavedLyrics.setText("Not a valid directory!");
					fieldForListing_SavedLyrics.setForeground(Color.RED);
				}
			}
		});
		
		JTextField OR = new JTextField("OR");
		OR.setForeground(lightBlue);
		OR.setEditable(false);
		OR.setOpaque(false);
		OR.setBorder(null);
		
		JButton button = new JButton("Search");
		button.setForeground(darkBlue);
		button.setBackground(darkBlue);
		button.setBorder(BorderFactory.createLineBorder(lightBlue));
		button.setMaximumSize(new Dimension(20, 20));
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileExplorer = new JFileChooser(pathToSaveLyrics);
				fileExplorer.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileExplorer.setApproveButtonText("Choose");
			
				int returnValue = fileExplorer.showOpenDialog(GUI.this);
				
				fieldForListing_SavedLyrics.setText("");
				
				if(returnValue == 0)
				{
					directoryToSaveLyricsTo = fileExplorer.getSelectedFile();
					pathToSaveLyrics = directoryToSaveLyricsTo.getPath();
					
					fieldForPath_Lyrics.setText(directoryToSaveLyricsTo.getPath());
					fieldForPath_Lyrics.setFont(new Font("Calibri", Font.PLAIN, 14));
					
					File [] allFilesInDirectory = directoryToSaveLyricsTo.listFiles();

					fieldForListing_SavedLyrics.setForeground(Color.WHITE);
					for(File f : allFilesInDirectory)
						fieldForListing_SavedLyrics.append(f.getName() + "\n");
				}
				
			}
		});
		
		northPanel.add(button);
		northPanel.add(OR);
		northPanel.add(fieldForPath_Lyrics);
		northPanel.setOpaque(false);
		
		panel.add(northPanel);
		panel.add(fieldForListing_SavedLyrics);
		
		TitledBorder tb = new TitledBorder("Lyrics destination");
		tb.setTitleColor(lightBlue);
		panel.setBorder(tb);
		panel.setBackground(darkBlue);
		panel.setOpaque(false);
		
		return panel;
	}
	
	
	private void setAllDefaults()
	{
//		pathToSaveLyrics = DEFAULT_PATH_TO_SAVE_LYRICS;
//		directoryToReadFrom = new File();
	}
	
	public JPanel east()
	{
		JPanel panel = new JPanel();
		panel.setBackground(darkBlue);

		class converter extends JPanel implements ActionListener, PropertyChangeListener
		{

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				System.out.println("Progress : " + task.getProgress());
				progressBar.setValue(task.getProgress());
			}

			@Override
			public void actionPerformed(ActionEvent e) 
			{
		            task = new Task();
		            setEnabled(false);
					task.addPropertyChangeListener(this);	
					task.execute();
			}
			
		}
		
		convertButton = new JButton("Convert");
		convertButton.setActionCommand("start");
		convertButton.addActionListener(new converter());
		
		if(directoryToReadFrom == null)
			convertButton.setEnabled(false);
		
		panel.add(convertButton);
		return panel;
	}
	
	public JPanel setup_progressBar()
	{
		JPanel panel = new JPanel();
		JPanel panelForStats = new JPanel();
		panel.setBackground(darkBlue);
		panelForStats.setBackground(darkBlue);
		
		panel.setLayout(new BorderLayout());
		
		progressStats = new JTextField(20);
		progressStats.setEditable(false);
		progressStats.setBorder(null);
		progressStats.setOpaque(false);
		progressStats.setForeground(Color.WHITE);
		progressStats.setFont(new Font("Calibri", Font.PLAIN, 14));
		
		progressInformation = new JTextPane();
		progressInformation.setBorder(null);
	    
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setForeground(Color.GREEN);
		progressBar.setBackground(darkBlue);
		
		panelForStats.add(progressBar);
		panelForStats.add(progressStats);
		panelForStats.setOpaque(true);
		
		progressInformation.setEditable(false);
		progressInformation.setOpaque(true);
		progressInformation.setBackground(darkBlue);
		
		JScrollPane scroller = new JScrollPane(progressInformation);
		scroller.setOpaque(true);
		
		JScrollBar vertical = scroller.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(scroller, BorderLayout.CENTER);
		p1.setPreferredSize(new Dimension(70, 120));
		p1.setBackground(darkBlue);
		
		panel.add(p1, BorderLayout.CENTER);
		panel.add(panelForStats, BorderLayout.NORTH);
		
		panel.setBackground(Color.WHITE);
		panel.setOpaque(true);
		
		return panel;
	}
	
	public void updateStats(int currentNumber, int totalNumber)
	{
		progressStats.setText(currentNumber + " of " + totalNumber +" files converted");
	}
	
	public void updateProgressField(String update, int type)
	{
		//This is a conversion
		if(type == 0)
		{
			StyledDocument doc = progressInformation.getStyledDocument();
			Style style = progressInformation.addStyle("Colored style", null);
			StyleConstants.setForeground(style, new Color(131, 186, 82));
			StyleConstants.setBold(style, true);
			
			try 
			{
	            doc.insertString(doc.getLength(), update, style);
	        } 
			
	        catch (BadLocationException e) 
			{
	            e.printStackTrace();
	        } 
		}
		
		//This is a file path
		else if(type == 1)
		{
			StyledDocument doc = progressInformation.getStyledDocument();
			Style style = progressInformation.addStyle("Colored style", null);
			StyleConstants.setForeground(style, Color.WHITE);
			StyleConstants.setBold(style, true);
			
			try 
			{
	            doc.insertString(doc.getLength(), update, style);
	        } 
			
	        catch (BadLocationException e) 
			{
	            e.printStackTrace();
	        } 
		}
		
		//Upload type
		else if(type == 2)
		{
			StyledDocument doc = progressInformation.getStyledDocument();
			Style style = progressInformation.addStyle("Colored style", null);
			StyleConstants.setForeground(style, new Color(112, 221, 206));
			StyleConstants.setBold(style, true);
			
			try 
			{
	            doc.insertString(doc.getLength(), update, style);
	        } 
			
	        catch (BadLocationException e) 
			{
	            e.printStackTrace();
	        } 
		}
	}
}
