import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import com.fasterxml.jackson.core.util.TextBuffer;

public class Window extends JFrame
{
	// UI colors
	private Color backgroundColor, tileButtonColor, textColor;
	private JRadioButtonMenuItem themeOne, themeTwo;
	
	// Color themes
	private static final ColorTheme GREY 		= new ColorTheme(new Color(35, 35, 36), new Color(128, 128, 128), Color.BLACK, "Grey");
	private static final ColorTheme TEST_THEME 	= new ColorTheme(Color.RED, Color.GREEN, Color.BLUE, "Test theme");
	
	TileButton pdfTile;
	TileButton wordTile;
	// Tile buttons
	private TileButton  uploadTile;
	
	
	public Window()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		setSize(width/2, height/2);
		setupColorTheme();
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setBackground(backgroundColor);
		
		setupMenuBar();
		
		// Layout will be Gridbag layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx 	= (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		c.ipady		= 70;
		
		add(setupTilePanel(), c);
			
		setVisible(true);
	}
	
	private void setupColorTheme()
	{
		backgroundColor = GREY.getbackgroundColor();
		tileButtonColor = GREY.getTileButtonColor();
		textColor		= GREY.getTextColor();
	}
	
	private void setupMenuBar()
	{
		JMenuBar menuBar 	= new JMenuBar();
		JMenu menu 			= new JMenu("Color themes");
		
		// Themes
		themeOne 	= new JRadioButtonMenuItem(GREY.getThemeName());
		themeTwo 	= new JRadioButtonMenuItem(TEST_THEME.getThemeName());
		
		themeOne.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				backgroundColor = GREY.getbackgroundColor();
				tileButtonColor = GREY.getTileButtonColor();
				textColor		= GREY.getTextColor();
				
				themeTwo.setSelected(false);
				getContentPane().setBackground(backgroundColor);
				
				pdfTile.setBackgroundColor(tileButtonColor);
				wordTile.setBackgroundColor(tileButtonColor);
			}
		});
		
		themeTwo.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				backgroundColor = TEST_THEME.getbackgroundColor();
				tileButtonColor = TEST_THEME.getTileButtonColor();
				textColor		= TEST_THEME.getTextColor();
				
				themeOne.setSelected(false);
				getContentPane().setBackground(backgroundColor);
				
				pdfTile.setBackgroundColor(tileButtonColor);
				wordTile.setBackgroundColor(tileButtonColor);
			}
		});
		
		menu.add(themeOne);
		menu.add(themeTwo);
		
		menuBar.add(menu);
		
		setJMenuBar(menuBar);
	}
	
	private JPanel setupTop()
	{
		JPanel panel = new JPanel();
		
		return panel;
	}
	
	private JPanel setupTilePanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		pdfTile = new TileButton("docx2.png");
		pdfTile.setBackgroundColor(tileButtonColor);
		
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 100;
		c.ipady = 100;
		c.insets = new Insets(0, 40, 0, 40);
		c.anchor = GridBagConstraints.PAGE_START;
		panel.add(pdfTile, c);
		
		wordTile = new TileButton("docx2.png");
		wordTile.setBackgroundColor(tileButtonColor);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 100;
		c.ipady = 100;
		panel.add(wordTile, c);
			
		return panel;
	}
}
