import java.awt.Color;

public class ColorTheme 
{
	private Color backgroundColor, tileButtonColor, textColor;
	private String themeName;
	
	public ColorTheme(Color backgroundColor, Color tileButtonColor, Color textColor, String themeName)
	{
		this.backgroundColor = backgroundColor;
		this.tileButtonColor = tileButtonColor;
		this.textColor = textColor;
		this.themeName = themeName;
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
}
