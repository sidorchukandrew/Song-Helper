import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.google.api.services.drive.Drive.Files;


import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

import static java.nio.file.StandardCopyOption.*;

import java.awt.Color;

import static java.nio.file.Files.*;

//import org.apache.poi.xwpf.converter.pdf.PdfConverter;
//import org.apache.poi.xwpf.converter.pdf.PdfOptions;

public class Main 
{
	static int songNumber;
	static WordDocumentLyrics document;
	
	
	// - - - - - - - PATHS - - - - - - - 
	static final String PATH_FOR_NEW_TEXT_FILES_DIRECTORY = "C:\\Users\\Andrew Sidorchuk\\Desktop\\Worship Team\\Z Binder\\New Songs";
	static final String PATH_TO_DIRECTORY_TO_SAVE_ONSONG_FILES = "C:\\Users\\Andrew Sidorchuk\\Desktop\\Worship Team\\Z Binder\\Chords and Lyrics for OnSong";
	static final String PATH_TO_SAVE_PRINTABLE_LYRICS = "C:\\Users\\Andrew Sidorchuk\\Desktop\\Worship Team\\Z Binder\\Printable";
	
	//  - - - - - - - FILE TYPES  - - - - - - - 
	static final String GOOGLE_FILE_TYPE_TEXT = "text/plain";
	static final String GOOGLE_FILE_TYPE_DOC = "application/vnd.google-apps.document";
	static final String UPLOAD_FILE_TYPE_WORD = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	static final String UPLOAD_FILE_TYPE_TEXT = "text/plain";
	
	private static final String CHORDS_AND_LYRICS_FOR_ONSONG_FOLDER_ID = "1uDiJqI8aGFycAKKCEzOhMD8s8YYLMeK0";
	private static final String PRINTABLE_LYRICS_FOLDER_ID 			   = "1r9YLq-udT62g_W_zej14DNfP8EQ-E0wq";
	
	//static final String PATH_TO_SAVE_PDF = "C:\\Users\\Andrew Sidorchuk\\Desktop\\Worship Team\\Z Binder\\PDFs";
	
	public static void main(String [] args)
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GUI window = new GUI();
		
//	   
//		Queue<Path> paths = new LinkedList<Path>();
//		
//		for(int i = 0; i < files.length; i++)
//		{
//			
//				uploadSuccessful = GoogleUploader.upload(songNameWithoutExtension, songPath, GOOGLE_FILE_TYPE_DOC, UPLOAD_FILE_TYPE_WORD,
//						PRINTABLE_LYRICS_FOLDER_ID);
//
//				//				File outFile = new File(PATH_TO_SAVE_PDF + songNameWithoutExtension + ".pdf");
////				OutputStream out = new FileOutputStream(outFile);
////				
//////				PdfOptions options = PdfOptions.create();
////				PdfConverter.getInstance().convert(document.getDocument(), out, null);
////			
//				
//			} 
//			catch (FileNotFoundException e)
//			{
//				e.printStackTrace();
//			} 
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			} catch (GeneralSecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 	
//		}
		
		
		System.out.println("Done");
	}
	
	public static void uploadToGoogle(String fullSongPath)
	{
		File f = new File(fullSongPath);
		String currentSongName = f.getName();
		
		try 
		{
			try {
				GoogleUploader.upload(currentSongName.replaceAll("[.][^.]+$", ""), fullSongPath, GOOGLE_FILE_TYPE_DOC, UPLOAD_FILE_TYPE_WORD,
						PRINTABLE_LYRICS_FOLDER_ID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String convertTextToWord(String destinationToSavePrintableLyrics, File textFileToConvert)
	{
		String currentSongName = textFileToConvert.getName();
		String fullSongPath = null;
		
		try 
		{
			//Buffer contains the OnSong version of the worship songs (Chords and Lyrics)
			BufferedReader buffer = new BufferedReader(new FileReader(textFileToConvert));
			
			String lineIn = "";
			
			//Create the new Word document to write into
			document = new WordDocumentLyrics();
			
			int lineNumber = 1;
			
			while((lineIn = buffer.readLine()) != null)
			{
				//If this is a separating line, write it to the file (else)
				if(!lineIn.isEmpty())
				{
					lineIn = formatStringFromText(lineIn, document);
					//System.out.println(lineIn);
					
					//If the result is an empty line, do nothing. 
					if(!lineIn.isEmpty())
					{
						if(lineNumber == 1)
						{
							lineIn = lineIn.toUpperCase();
							document.generateTitle(lineIn);
						}
						else
							document.generateRunFromText(lineIn);
					}
				}
				
				else
					document.generateBreak();
				
				lineNumber++;
			}

			System.out.println(destinationToSavePrintableLyrics + "\\" + songNumber + ". " + currentSongName.replaceAll("[.][^.]+$", "") + ".docx");

			fullSongPath = document.writeDocument(destinationToSavePrintableLyrics, currentSongName.replaceAll("[.][^.]+$", ""));
		
			buffer.close();
			document = null;
			
			
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return fullSongPath;
	}
	    	  
	public static String formatStringFromText(String line, WordDocumentLyrics document)
	{
		line = line.replaceAll("[^\\x00-\\x7F]+", "");
		line = stripChords(line);
		line = checkForSectionLabels(line, document);
			
		return line;
	}
		
	public static String checkForSectionLabels(String line, WordDocumentLyrics document)
	{
	
		if(line.contains(":"))
		{
			if(line.contains("Chorus"))
				document.setIsChorus(true);
			else
				document.setIsChorus(false);
			
			if(line.contains("Number"))
				extractSongNumber(line);
			
			if(line.contains("Bridge"))
			{
				document.setIsBridge(true);
				return "BRIDGE";
			}
			else
				document.setIsBridge(false);
				
			return "";
		}
		
		else
			return line;
	}
		
	public static String stripChords(String line)
	{
		line = line.replaceAll("\\[.*?\\]", "");
		
		if(line.matches("[^ABCDEFGm0-9 ]"))
			System.out.println("Non chord line : " + line);
		return line;
	}
	
	public static void extractSongNumber(String line)
	{
		line = line.replaceAll("[^0-9]", "");
		songNumber = Integer.parseInt(line);
		System.out.println(songNumber);
		document.generateHeader(songNumber);
	}

}
