import java.awt.Color;

public class ColorTheme 
{
	private Color backgroundColor, tileButtonColor, textColor;
	private Color gradientColor1, gradientColor2;
	private String themeName;
	
	public ColorTheme(Color backgroundColor, Color tileButtonColor, Color textColor, String themeName)
	{
		this.backgroundColor = backgroundColor;
		this.tileButtonColor = tileButtonColor;
		this.textColor = textColor;
		this.themeName = themeName;
	}
	
	public ColorTheme(Color backgroundColor, Color gradientColor1, Color gradientColor2, Color textColor, String themeName)
	{
		this.backgroundColor 	= backgroundColor;
		this.gradientColor1 	= gradientColor1;
		this.gradientColor2 	= gradientColor2;
		this.textColor 			= textColor;
		this.themeName 			= themeName;
	}
	
	public Color getbackgroundColor()
	{
		return backgroundColor;
	}
	
	public Color getTileButtonColor()
	{
		return tileButtonColor;
	}
	
	public Color getTextColor()
	{
		return textColor;
	}
	
	public String getThemeName()
	{
		return themeName;
	}
	
	public Color getGradientColor1()
	{
		return gradientColor1;
	}
	
	public Color getGradientColor2()
	{
		return gradientColor2;
	}
}
