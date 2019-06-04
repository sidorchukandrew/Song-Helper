import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;

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
	private static long totalLines;
	private static int currentLine;
	static Drive driveService;
	static String query = "";
	
	static String [] tabs = {"", "\t", "\t\t", "\t\t\t", "\t\t\t\t"};
	static int tabCount = 0;
	
	static List<GoogleFolderNode> driveFolderHierarchy;
	
	public static void main(String [] args)
	{
//		driveFolderHierarchy = new LinkedList<GoogleFolderNode>();
//		
//		try 
//		{
//			driveService = GoogleDriveUtil.getDriveService();
//			getSubFolder(null, null);
//			String pageToken = "";
//			List<com.google.api.services.drive.model.File> list = new ArrayList<com.google.api.services.drive.model.File>();
//
//			String query = " mimeType = 'application/vnd.google-apps.folder' "
//                    		+ " and 'root' in parents";
//			
//			while(pageToken != null)
//			{
//				 FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
//		                    // Fields will be assigned values: id, name, createdTime
//		                    .setFields("nextPageToken, files(id, name, createdTime)")//
//		                    .setPageToken(pageToken).execute();
//		            
//				 for (com.google.api.services.drive.model.File file : result.getFiles()) 
//		                list.add(file);
//		            
//		            pageToken = result.getNextPageToken();
//			}
//			
//			for (com.google.api.services.drive.model.File folder : list)
//			{
//	            System.out.println("Folder ID: " + folder.getId() + " --- \t\tName: " + folder.getName());
//	            
//	            query = " mimeType = 'application/vnd.google-apps.folder' " //
//	                    + " and '" + folder.getId() + "' in parents";
//	            
//	            pageToken = "";
//	            while(pageToken != null)
//				{
//					 FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
//			                    // Fields will be assigned values: id, name, createdTime
//			                    .setFields("nextPageToken, files(id, name, createdTime)")//
//			                    .setPageToken(pageToken).execute();
//			            
//					 for (com.google.api.services.drive.model.File file : result.getFiles()) 
//			                list2.add(file);
//			            
//			            pageToken = result.getNextPageToken();
//				}
//	            
//	            for(com.google.api.services.drive.model.File folder2 : list2)
//	            	System.out.println("\t\tFolder ID: " + folder2.getId() + " --- \t\tName: " + folder2.getName());
//	            
//	            list2.clear();
//			System.out.println();
//		} 
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public static void getSubFolder(com.google.api.services.drive.model.File folder, String parentID) throws IOException
//	{
//		if(folder != null)
//		{
//			driveFolderHierarchy.add(new GoogleFolderNode(folder.getName(), folder.getId(), parentID));
//			System.out.print(tabs[tabCount] + folder.getName() + " - ");
//		}
//
//		String pageToken = "";
//		List<com.google.api.services.drive.model.File> list = new ArrayList<com.google.api.services.drive.model.File>();
//
//		if(query.compareTo("") == 0 || folder == null)
//			query = " mimeType = 'application/vnd.google-apps.folder' "
//            		+ " and 'root' in parents";
//		else
//			query = " mimeType = 'application/vnd.google-apps.folder' " 
//						+ " and '" + folder.getId() + "' in parents";
//		
//		while(pageToken != null)
//		{
//			 FileList result = driveService.files().list().setQ(query).setSpaces("drive") //
//	                    // Fields will be assigned values: id, name, createdTime
//	                    .setFields("nextPageToken, files(id, name, createdTime)")//
//	                    .setPageToken(pageToken).execute();
//	            
//			 for (com.google.api.services.drive.model.File file : result.getFiles()) 
//			 {
//				 	tabCount++;
//				 	if(tabCount == 1)
//				 		getSubFolder(file, "root");
//				 	else
//				 		getSubFolder(file, )
//				 	tabCount--;
//			 }
//	            
//	            pageToken = result.getNextPageToken();
//		}
//		
//		System.out.println();
//		
//		
//	}
	
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
//		GUI window = new GUI();
		Window window = new Window();
	}
		
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
	
	public static String convertTextToWord(String destinationToSavePrintableLyrics, File textFileToConvert, ProgressCircleWithInnerTracker progressCircle)
	{
		String currentSongName = textFileToConvert.getName();
		String fullSongPath = null;
		
		try 
		{
			//Buffer contains the OnSong version of the worship songs (Chords and Lyrics)
			BufferedReader buffer = new BufferedReader(new FileReader(textFileToConvert));
			
			Path path = Paths.get(textFileToConvert.getPath());
			totalLines = java.nio.file.Files.lines(path).count();
			
			currentLine = 1;
			
			System.out.println("This song has this many lines : " + totalLines);
			
			String lineIn = "";
			
			//Create the new Word document to write into
			document = new WordDocumentLyrics();
			
			while((lineIn = buffer.readLine()) != null)
			{

				progressCircle.updateProgressOfIndividualConversion((int)((currentLine  / (double)totalLines) * 100));
				progressCircle.repaint();

				//If this is a separating line, write it to the file (else)
				if(!lineIn.isEmpty())
				{
					lineIn = formatStringFromText(lineIn, document);
					
					//If the result is an empty line, do nothing. 
					if(!lineIn.isEmpty())
					{
						if(currentLine == 1)
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
				
				currentLine++;
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
	
	public long getTotalLines()
	{
		return totalLines;
	}

	public int getCurrentLineNumber()
	{
		return currentLine;
	}
}
