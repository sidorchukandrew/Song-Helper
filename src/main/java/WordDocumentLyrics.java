import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPageMargins;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.math.BigInteger;

public class WordDocumentLyrics 
{
	private boolean isChorus = false;
	private boolean isTitle = true;
	private boolean isBridge = false;
	
	private XWPFDocument document;
	private final int LYRICS_FONT_SIZE = 21;
	private final int HEADER_FONT_SIZE = 18;
	
	private XWPFParagraph para, titlePara;
	private CTSectPr sectPr;
	
	private int songNumber;
	
	public WordDocumentLyrics()
	{
		document = new XWPFDocument(); 
		 
		titlePara = document.createParagraph();
		titlePara.setAlignment(ParagraphAlignment.CENTER);
		para = document.createParagraph();
		para.setAlignment(ParagraphAlignment.LEFT);
		
		 
		sectPr = document.getDocument().getBody().addNewSectPr();
		CTPageMar pgMargins = sectPr.addNewPgMar();
		pgMargins.setTop(BigInteger.valueOf(720));
		pgMargins.setRight(BigInteger.valueOf(1440));
		pgMargins.setLeft(BigInteger.valueOf(1440));
		pgMargins.setBottom(BigInteger.valueOf(1440));
		 

	}
	
	public void generateHeader(int songNumber)
	{
		CTP ctp =  CTP.Factory.newInstance();
		CTText t = ctp.addNewR().addNewT();
		t.setStringValue(songNumber + "");
		 
		XWPFParagraph p1 = new XWPFParagraph(ctp, document);
		XWPFRun run = p1.getRun(ctp.getRArray(0));
		run.setFontSize(HEADER_FONT_SIZE);
		p1.setAlignment(ParagraphAlignment.RIGHT);
		XWPFParagraph [] ps = new XWPFParagraph[1];
		ps[0] = p1;
		 
		XWPFHeaderFooterPolicy hfPolicy = document.createHeaderFooterPolicy();
		hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, ps);
		
		this.songNumber = songNumber;
	}
	
	public void generateTitle(String line)
	{
		XWPFRun run = titlePara.createRun();
		run.setFontSize(LYRICS_FONT_SIZE);
		run.setBold(true);
		run.setText(line);	
	}
	
	public void generateRunFromText(String line)
	{
		XWPFRun run =  para.createRun();
		
		run.setFontSize(LYRICS_FONT_SIZE);
		
		if(isChorus)
		{
			run.addTab();
			run.setBold(true);
		}
		
		else if(isBridge)
		{
			run.addTab();
		}
		
		run.setText(line);
		run.addBreak();
	}
	

	public void generateBreak()
	{
		XWPFRun run = para.createRun();
		
		run.addBreak();
	}
	
	public void setIsChorus(boolean isChorus)
	{
		this.isChorus = isChorus;
	}
	
	public void setIsTitle(boolean isTitle)
	{
		this.isTitle = isTitle;
	}
	
	public void setIsBridge(boolean isBridge)
	{
		this.isBridge = isBridge;
	}
	
	public String writeDocument(String path, String fileName)
	{
	    FileOutputStream out;
	    String fullSongPath = null;
		
	    try 
		{
			out = new FileOutputStream(new File(path + "\\" + songNumber + ". " + fileName + ".docx"));
			fullSongPath = path + "\\" + songNumber + ". " + fileName + ".docx";
		    document.write(out);
		    document.close();
		    out.close();
		}
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	    return fullSongPath;
	}
	
	public XWPFDocument getDocument()
	{
		return document;
	}
}
