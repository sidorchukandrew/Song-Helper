import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.border.Border;

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
	
	private Color tealGradient = new Color(59, 221, 200, 1);
	private Color blueGradient = new Color(47, 155, 230, 1);
	
	private Color tealGradient2 = new Color(59, 221, 200, 255);
	private Color blueGradient2 = new Color(47, 155, 230, 255);
	
	private Color gradientColor1;
	private Color gradientColor2;
	
	private Color gradientOutline1;
	private Color gradientOutline2;
	
	private Color temp;
	
	private File directory = null;
	
	public TileButton(String iconName)
	{
		animating = false;
		transparencyValue = 1;
		overlay = new Color(255, 255, 255, transparencyValue);
		
//		setSize(new Dimension(100, 100));
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
	
	public void setBackgroundColor(Color gradientColor1, Color gradientColor2)
	{
		this.gradientColor1 = gradientColor1;
		this.gradientColor2 = gradientColor2;
		
		gradientOutline1 = new Color(gradientColor1.getRed(), gradientColor1.getGreen(), gradientColor1.getBlue(), 255);
		gradientOutline2 = new Color(gradientColor2.getRed(), gradientColor2.getGreen(), gradientColor2.getBlue(), 255);

	}
	
	protected void paintComponent(Graphics g) 
	{
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D)g;
	    
	    GradientPaint gradientPaint = new GradientPaint(0, 0, gradientColor1, 100, 100, gradientColor2);
	    
	    g2d.setPaint(gradientPaint);
	    g2d.fill(new Rectangle2D.Float(0, 0, 100, 100));
	    
	    gradientPaint = new GradientPaint(50, 0, gradientOutline1, 50, 100, gradientOutline2);
	    g2d.setPaint(gradientPaint);
	    g2d.draw(new Rectangle(0, 0, 100, 100));
	   
    }
	
	public void animateHoverOn()
	{		
		x2 = this.getWidth() - 1;
		y2 = 0;		
		xstart = 0;
		ystart = 0;
		
		timer = new Timer(3, new ActionListener()
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
					temp = gradientColor1;
					gradientColor1 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), transparencyValue);
					
					temp = gradientColor2;
					gradientColor2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), transparencyValue);
					
				}
				else if(transparencyValue != 255)
				{
					transparencyValue = 255;
					temp = gradientColor1;
					gradientColor1 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), transparencyValue);
					
					temp = gradientColor2;
					gradientColor2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), transparencyValue);
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
		
		timer = new Timer(2, new ActionListener()
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
					temp = gradientColor1;
					gradientColor1 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), transparencyValue);
					
					temp = gradientColor2;
					gradientColor2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), transparencyValue);
				}
				
				else if(transparencyValue != 1)
				{
					transparencyValue = 1;
					temp = gradientColor1;
					gradientColor1 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), transparencyValue);
					
					temp = gradientColor2;
					gradientColor2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), transparencyValue);
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
			raiseBorder();
			
			if(returnValue == JFileChooser.APPROVE_OPTION)
			{
				directory = fileExplorer.getSelectedFile();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			
		}
	}
	
	public File getDirectory()
	{
		return directory;
	}
}
