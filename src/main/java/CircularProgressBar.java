import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class CircularProgressBar extends JPanel
{
	private int progressValue;
	private Color darkBlue = new Color(0, 22, 40);
	private Color pinkText = new Color(245, 215, 224);
	private Color progressColor;
	
	private long totalNumberOfLines;
	
	public CircularProgressBar()
	{
		setMinimumSize(new Dimension(200, 200));
		setOpaque(true);
		setBackground(darkBlue);
	}
	
	public void updateProgress(int progressValue)
	{
		this.progressValue = progressValue;
	}
	
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		if(progressValue != 0)
		{
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.translate(this.getWidth() /2, this.getHeight() / 2);
			g2d.rotate(Math.toRadians(270));
			
			Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
			arc.setFrameFromCenter(new Point(0,0), new Point(120, 120));
			arc.setAngleStart(1);
			
			Arc2D.Float arc2 = new Arc2D.Float(Arc2D.PIE);
			arc2.setFrameFromCenter(new Point(0, 0), new Point(110, 110));
			arc2.setAngleStart(1);
			
			Ellipse2D circle = new Ellipse2D.Float(0, 0, 100, 100);
			circle.setFrameFromCenter(new Point(0,0), new Point(100, 100));
			
			arc.setAngleExtent(-progressValue * 3.6);
			g2d.setColor(new Color(112, 221, 206));
			g2d.draw(arc);
			g2d.fill(arc);
			
			arc2.setAngleExtent(-(progressValue * 1.5) * 3.6);
			g2d.setColor(Color.GREEN);
			g2d.draw(arc2);
			g2d.fill(arc2);
			
			g2d.setColor(darkBlue);
			g2d.draw(circle);
			g2d.fill(circle);
			
			if(progressValue != 0)
			{
				g2d.rotate(Math.toRadians(90));
				g2d.setColor(Color.WHITE);
				g.setFont(new Font("Verdana", Font.PLAIN, 18));
				
				if(progressValue > 9)
					g2d.drawString(progressValue + "%", -10, 0);
				
				else if(progressValue > 99)
					g2d.drawString((progressValue * 1.5 / 100) + "%", -20, 0);
				
				else
					g2d.drawString(progressValue + "%", 0, 0);
			}
		}//end if
	}
}
