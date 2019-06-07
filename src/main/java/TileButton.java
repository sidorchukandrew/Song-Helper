import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileButton extends JComponent
{
	private Color tileButtonColor;
	private int x, y, x2, y2;
	private int xstart, ystart;
	private Graphics g;
	private Color toPaint;
	private BufferedImage logo, scaledLogo, openFolder, scaledOpenFolder;
	private Border loweredBevel;
	private Timer timer;
	private Color overlay;
	private int transparencyValue;
	private boolean animating;
	
	private File directory = null;
	
	public TileButton(String iconName)
	{
		animating = false;
		transparencyValue = 1;
		overlay = new Color(255, 255, 255, transparencyValue);
		
		setSize(new Dimension(100, 100));
		x2 = this.getWidth();
		y2 = this.getHeight();
		
		xstart = 0;
		ystart = 0;
		
		try 
		{
			logo = ImageIO.read(getClass().getResource(iconName));
		    Image tmp = logo.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		    scaledLogo = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

		    Graphics2D g2d = scaledLogo.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();
		    
		    openFolder = ImageIO.read(getClass().getResource("opened-folder.png"));
		    tmp = openFolder.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		    scaledOpenFolder = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);

		    g2d = scaledOpenFolder.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();
		    
		} 
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		addMouseListener(new MouseListen());
	}
	
	public void setBackgroundColor(Color tileButtonColor)
	{
		this.tileButtonColor = tileButtonColor;
		toPaint = tileButtonColor;
	}
	
	protected void paintComponent(Graphics g) 
	{
	    super.paintComponent(g);
	    g.setColor(tileButtonColor);
	    g.drawRect(xstart, ystart, 100, 100);
	    g.fillRect(xstart, ystart, 100, 100);
	    
	    if(!animating)
	    	g.drawImage(scaledLogo, 25, 20, null);
	    
	    g.setColor(overlay);
	    g.drawRect(xstart, ystart, 100, 100);
	    g.fillRect(xstart, ystart, 100, 100);
	    
	    if(animating)
	    	g.drawImage(scaledOpenFolder, 35, 35, null);
	   
    }
	
	public void animateHoverOn()
	{		
		x2 = this.getWidth() - 1;
		y2 = 0;		
		xstart = 0;
		ystart = 0;
		
		timer = new Timer(1, new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				if(transparencyValue < 100)
					animating = true;
				
				if(transparencyValue < 241)
				{
					repaint();
					transparencyValue+= 20;
					overlay = new Color(255,  255, 255, transparencyValue);
					
				}
				else if(transparencyValue != 255)
				{
					transparencyValue = 255;
					overlay = new Color(255,  255, 255, transparencyValue);
					repaint();
				}
				else
					timer.stop();
			}
		});
		
		timer.start();
	}
	
	public void animateHoverOff()
	{		
		x2 = this.getWidth() - 1;
		y2 = 0;
		
		xstart = 0;
		ystart = 0;
		
		toPaint = tileButtonColor;
		
		timer = new Timer(1, new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(transparencyValue > 155)
					animating = false;
				
				if(transparencyValue >= 10)
				{
					repaint();
					transparencyValue-= 10;
					overlay = new Color(255,  255, 255, transparencyValue);
					
				}
				
				else if(transparencyValue != 1)
				{
					transparencyValue = 1;
					overlay = new Color(255, 255, 255, transparencyValue);
					repaint();
				}
				
				else
					timer.stop();
			}
		});
		
		timer.start();
	}
	
	private void lowerBorder()
	{
		loweredBevel = BorderFactory.createLoweredBevelBorder();
		this.setBorder(loweredBevel);
	}
	
	private void raiseBorder()
	{
		this.setBorder(BorderFactory.createEmptyBorder());
		
	}


	private class MouseListen implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{
			
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			if(timer != null)
				timer.stop();
			
			animateHoverOn();
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			timer.stop();
			animateHoverOff();
		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
			lowerBorder();
			JFileChooser fileExplorer = new JFileChooser("C:\\Users\\Andrew Sidorchuk\\Desktop\\Worship Team\\Z Binder");
			fileExplorer.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileExplorer.setApproveButtonText("Choose");
			
			int returnValue = fileExplorer.showOpenDialog(TileButton.this);
			
			if(returnValue == JFileChooser.APPROVE_OPTION)
			{
				directory = fileExplorer.getSelectedFile();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			raiseBorder();
		}
	}
	
	public File getDirectory()
	{
		return directory;
	}
}
