import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
	private Color backgroundColor, tileButtonColor, textColor, g1, g2;
	private JRadioButtonMenuItem themeOne, themeTwo, themeThree, themeFour, themeFive;
	
	// Color themes
	private static final ColorTheme GREY 		= new ColorTheme(new Color(35, 35, 36), new Color(128, 128, 128, 1), Color.BLACK, "Grey");
	private static final ColorTheme TEST_THEME 	= new ColorTheme(Color.RED, Color.GREEN, Color.BLUE, "Test theme");
	private static final ColorTheme NAVY		= new ColorTheme(new Color(20, 21, 39), 
														new Color(59, 221, 200, 1), new Color(47, 155, 230, 1), 
																Color.WHITE, "Navy");
	private static final ColorTheme CREAMSICLE	= new ColorTheme(Color.WHITE, new Color(252, 227, 138, 1), 
														new Color(243, 129, 129, 1), Color.BLACK, "Creamsicle");
	
	private static final ColorTheme DEEP_SUNSET = new ColorTheme(new Color(20, 21, 39), new Color(98, 39, 116, 1), 
													new Color(197, 51, 100, 1), Color.WHITE, "Deep sunset");
	
	private TileButton pdfTile, wordTile, uploadTile, randomTile;
	
	
	public Window()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		this.setPreferredSize(new Dimension(width/2, height/2));
		this.setMinimumSize(new Dimension(width/2, height/2));
		this.setMaximumSize(new Dimension(width/2, height/2));
		
		setupColorTheme();
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setBackground(backgroundColor);
		
		setupMenuBar();
		
		// Layout will be Gridbag layout
		setLayout(new GridLayout(3, 0));
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx 	= (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		c.ipady		= 70;
		
		add(setupTilePanel());
			
		setVisible(true);
	}
	
	private void setupColorTheme()
	{
		backgroundColor = GREY.getbackgroundColor();
		g1				= GREY.getTileButtonColor();
		g2				= GREY.getTileButtonColor();
		textColor		= GREY.getTextColor();
	}
	
	private void setupMenuBar()
	{
		JMenuBar menuBar 	= new JMenuBar();
		JMenu menu 			= new JMenu("Color themes");
		
		// Themes
		themeOne 	= new JRadioButtonMenuItem(GREY.getThemeName());
		themeTwo 	= new JRadioButtonMenuItem(TEST_THEME.getThemeName());
		themeThree 	= new JRadioButtonMenuItem(NAVY.getThemeName());
		themeFour 	= new JRadioButtonMenuItem(CREAMSICLE.getThemeName());
		themeFive 	= new JRadioButtonMenuItem(DEEP_SUNSET.getThemeName());
		
		themeOne.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				backgroundColor = GREY.getbackgroundColor();
				g1				= GREY.getTileButtonColor();
				g2				= GREY.getTileButtonColor();		
				textColor		= GREY.getTextColor();
				
				themeTwo.setSelected(false);
				themeThree.setSelected(false);
				themeFour.setSelected(false);
				themeFive.setSelected(false);
				
				getContentPane().setBackground(backgroundColor);
				
				pdfTile.setBackgroundColor(g1, g2);
				wordTile.setBackgroundColor(g1, g2);
				uploadTile.setBackgroundColor(g1, g2);
				randomTile.setBackgroundColor(g1, g2);
			}
		});
		
		
		themeTwo.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				backgroundColor = TEST_THEME.getbackgroundColor();
				g1				= TEST_THEME.getTileButtonColor();
				g2				= TEST_THEME.getTileButtonColor();
				textColor		= TEST_THEME.getTextColor();
				
				themeOne.setSelected(false);
				themeThree.setSelected(false);
				themeFour.setSelected(false);
				themeFive.setSelected(false);
				getContentPane().setBackground(backgroundColor);
				
				pdfTile.setBackgroundColor(g1, g2);
				wordTile.setBackgroundColor(g1, g2);
				uploadTile.setBackgroundColor(g1, g2);
				randomTile.setBackgroundColor(g1, g2);
			}
		});
		
		themeThree.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				backgroundColor = NAVY.getbackgroundColor();
				g1				= NAVY.getGradientColor1();
				g2				= NAVY.getGradientColor2();
				textColor		= NAVY.getTextColor();
				
				themeOne.setSelected(false);
				themeTwo.setSelected(false);
				themeFour.setSelected(false);
				themeFive.setSelected(false);
				getContentPane().setBackground(backgroundColor);
				
				pdfTile.setBackgroundColor(g1, g2);
				wordTile.setBackgroundColor(g1, g2);
				uploadTile.setBackgroundColor(g1, g2);
				randomTile.setBackgroundColor(g1, g2);
			}
		});
		
		themeFour.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				backgroundColor = CREAMSICLE.getbackgroundColor();
				g1				= CREAMSICLE.getGradientColor1();
				g2				= CREAMSICLE.getGradientColor2();
				textColor		= CREAMSICLE.getTextColor();
				
				themeOne.setSelected(false);
				themeTwo.setSelected(false);
				themeThree.setSelected(false);
				themeFive.setSelected(false);
				getContentPane().setBackground(backgroundColor);
				
				pdfTile.setBackgroundColor(g1, g2);
				wordTile.setBackgroundColor(g1, g2);
				uploadTile.setBackgroundColor(g1, g2);
				randomTile.setBackgroundColor(g1, g2);
			}
		});
		
		themeFive.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				backgroundColor = DEEP_SUNSET.getbackgroundColor();
				g1				= DEEP_SUNSET.getGradientColor1();
				g2				= DEEP_SUNSET.getGradientColor2();
				textColor		= DEEP_SUNSET.getTextColor();
				
				themeOne.setSelected(false);
				themeTwo.setSelected(false);
				themeFour.setSelected(false);
				themeThree.setSelected(false);
				getContentPane().setBackground(backgroundColor);
				
				pdfTile.setBackgroundColor(g1, g2);
				wordTile.setBackgroundColor(g1, g2);
				uploadTile.setBackgroundColor(g1, g2);
				randomTile.setBackgroundColor(g1, g2);
			}
		});
		
		menu.add(themeOne);
		menu.add(themeTwo);
		menu.add(themeThree);
		menu.add(themeFour);
		menu.add(themeFive);
		
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
		pdfTile.setBackgroundColor(g1, g2);
		
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 100;
		c.ipady = 100;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(0, 0, 0, 40);
		panel.add(pdfTile, c);
		
		wordTile = new TileButton("docx2.png");
		wordTile.setBackgroundColor(g1, g2);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 100;
		c.ipady = 100;
		c.insets = new Insets(0, 40, 0, 40);
		panel.add(wordTile, c);
		
		uploadTile = new TileButton("docx2.png");
		uploadTile.setBackgroundColor(g1, g2);
		
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.ipadx = 100;
		c.ipady = 100;
		c.insets = new Insets(0, 40, 0, 40);
		panel.add(uploadTile, c);
		
		randomTile = new TileButton("docx2.png");
		randomTile.setBackgroundColor(g1, g2);
		
		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 0;
		c.ipadx = 100;
		c.ipady = 100;
		c.insets = new Insets(0, 40, 0, 40);
		panel.add(randomTile, c);
			
		panel.setOpaque(false);
		
		return panel;
	}
}
