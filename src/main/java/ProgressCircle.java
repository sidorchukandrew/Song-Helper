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

public class ProgressCircle extends JPanel
{
	private Color darkBlue = new Color(0, 22, 40);
	private Color pinkText = new Color(245, 215, 224);
	
	public final static Color EMPTY_BLUE = new Color(54, 82, 119);
	public final static Color FULL_BLUE = new Color(134, 229, 225);
	public final static Color EMPTY_GREEN = new Color(58, 79, 80);
	public final Color fillingProgressCircleInner = new Color(158, 232, 85);
	
	public final static Color FULL_RED =  new Color(255, 99, 100);
	public final static Color EMPTY_RED = new Color(94, 52, 98);
	
	public final static Color FULL_YELLOW = new Color(255, 243, 59);
	public final static Color EMPTY_YELLOW = new Color(128, 126, 95);
	
	public final static Color FULL_PURPLE = new Color(200, 139, 223);
	public final static Color EMPTY_PURPLE = new Color(121, 103, 128);
	
	public Color progressColor = new Color(112, 221, 206);
	
	private Color emptyColor, fullColor;
	
	private int overallProgress, individualProgress;
	
	public ProgressCircle()
	{
		setOpaque(true);
		setSize(240, 240);
		setMinimumSize(new Dimension(240, 240));
		setMaximumSize(new Dimension(240, 240));
		setBackground(darkBlue);
		
		individualProgress = 0;
		overallProgress = 0;
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.translate(this.getWidth() /2, this.getHeight() / 2);
		g2d.rotate(Math.toRadians(270));
		
		Arc2D.Float overallArc = new Arc2D.Float(Arc2D.PIE);
		overallArc.setFrameFromCenter(new Point(0,0), new Point(100, 100));
		overallArc.setAngleStart(1);
		
//		Arc2D.Float individualArc = new Arc2D.Float(Arc2D.PIE);
//		individualArc.setFrameFromCenter(new Point(0, 0), new Point(79, 79));
//		individualArc.setAngleStart(1);
		
		Ellipse2D circle = new Ellipse2D.Float(0, 0, 70, 70);
		circle.setFrameFromCenter(new Point(0,0), new Point(70, 70));
		
		Ellipse2D circle2 = new Ellipse2D.Float(0, 0, 100, 100);
		circle2.setFrameFromCenter(new Point(0,0), new Point(100, 100));
		
		Ellipse2D circle3 = new Ellipse2D.Float(0, 0, 80, 80);
		circle3.setFrameFromCenter(new Point(0,0), new Point(80, 80));
		
		g2d.setColor(emptyColor);
		g2d.draw(circle2);
		g2d.fill(circle2);
		
		if(overallProgress != 0)
		{
			overallArc.setAngleExtent(-overallProgress* 3.6);
			g2d.setColor(fullColor);
			g2d.draw(overallArc);
			g2d.fill(overallArc);
		}
		
		
//		if(individualProgress != 0)
//		{
//			individualArc.setAngleExtent(-individualProgress * 3.6);
//			g2d.setColor(Color.GREEN);
//			g2d.draw(individualArc);
//			g2d.fill(individualArc);
//		}
		
		g2d.setColor(darkBlue);
		g2d.draw(circle);
		g2d.fill(circle);
		
		if(overallProgress != 0)
		{
			g2d.rotate(Math.toRadians(90));
			g2d.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", Font.PLAIN, 18));
			
			if(overallProgress > 9)
				g2d.drawString(overallProgress + "%", -10, 0);
			
			else if(overallProgress > 99)
				g2d.drawString((overallProgress * 1.5 / 100) + "%", -20, 0);
			
			else
				g2d.drawString(overallProgress + "%", 0, 0);
		}

	}
	
	public void setEmptyColor(Color newColor)
	{
		emptyColor = newColor;
	}
	
	public void setFullColor(Color newColor)
	{
		fullColor = newColor;
	}
	
	public void updateOverAllProgress(int progr)
	{
		overallProgress = progr;
	}
}
