import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.fasterxml.jackson.core.util.TextBuffer;

public class Window extends JFrame
{
	// UI colors
	private Color backgroundColor, tileButtonColor, textColor;
	
	// Color themes
	private static final ColorTheme GREY 		= new ColorTheme(new Color(35, 35, 36), new Color(128, 128, 128), Color.BLACK, "Grey");
	private static final ColorTheme TEST_THEME 	= new ColorTheme(Color.RED, Color.GREEN, Color.BLUE, "Test theme");
	
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
		JMenuItem themeOne 	= new JMenuItem(GREY.getThemeName());
		JMenuItem themeTwo 	= new JMenuItem(TEST_THEME.getThemeName());
		
		themeOne.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				backgroundColor = GREY.getbackgroundColor();
				tileButtonColor = GREY.getTileButtonColor();
				textColor		= GREY.getTextColor();
				
				getContentPane().setBackground(backgroundColor);
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
				
				getContentPane().setBackground(backgroundColor);
			}
		});
		
		menu.add(themeOne);
		menu.add(themeTwo);
		
		menuBar.add(menu);
		
		setJMenuBar(menuBar);
	}
}
