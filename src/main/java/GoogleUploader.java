import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.DocsScopes;
import com.google.api.services.docs.v1.model.BatchUpdateDocumentRequest;
import com.google.api.services.docs.v1.model.BatchUpdateDocumentResponse;
import com.google.api.services.docs.v1.model.Body;
import com.google.api.services.docs.v1.model.Document;
import com.google.api.services.docs.v1.model.Range;
import com.google.api.services.docs.v1.model.Request;
import com.google.api.services.docs.v1.model.StructuralElement;
import com.google.api.services.docs.v1.model.TextStyle;
import com.google.api.services.docs.v1.model.UpdateTextStyleRequest;
import com.google.api.services.docs.v1.model.WeightedFontFamily;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleUploader 
{
    private static final String APPLICATION_NAME = "Automatic Song Uploader";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "C:\\Users\\Andrew Sidorchuk\\eclipse-workspace\\Worship Song\\tokens";
    
    private static final String GOOGLE_FILE_TYPE_DOC = "application/vnd.google-apps.document";
    
    private static Drive service;

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException
    {
        // Load client secrets.
        InputStream in = Main.class.getResourceAsStream(CREDENTIALS_FILE_PATH);

        if (in == null)
        {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("online")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static boolean upload(String fileName, String pathOfFile, String googleFileType, String uploadFileType, String folderID) throws IOException, GeneralSecurityException
    {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        
        FileList list = service.files().list().setPageSize(600).setFields("nextPageToken, files(id, name)").execute();
        List<File> files = list.getFiles();
        
        for(File f : files)
        	if(f.getName().startsWith("Printable"))
        		System.out.println(f.getName() + "    " + f.getId());

    	File metaData = new File();
    	metaData.setName(fileName);
    	metaData.setMimeType(googleFileType);
    
    	metaData.setParents(Collections.singletonList(folderID));
    	java.io.File filePath = new java.io.File(pathOfFile);

    	FileContent mediaContent = new FileContent(uploadFileType, filePath);
    	File file;
    	
    	try
    	{
    		System.out.println("\n\n------------------ UPLOADING FILE NOW --------------------------\n\n");
			file = service.files().create(metaData, mediaContent)
				    .setFields("id, parents")
				    .execute();
			
			if(googleFileType.compareTo(GOOGLE_FILE_TYPE_DOC) == 0)
			{
				Docs dService = new Docs.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
		                .setApplicationName(APPLICATION_NAME)
		                .build();
	
		        Document response = dService.documents().get(file.getId()).execute() ;
		        List<StructuralElement> r1 = response.getBody().getContent();
		        int size =  r1.get(1).getEndIndex();
		        String title = response.getTitle();
		        
		        List<Request> requests = new ArrayList<>();
		        
		        requests.add(new Request()
		                .setUpdateTextStyle(new UpdateTextStyleRequest()
		                        .setRange(new Range().setStartIndex(0).setEndIndex(10))
		                        .setTextStyle(new TextStyle().setWeightedFontFamily(new WeightedFontFamily().setFontFamily("Calibri")))
		                        .setFields("weightedFontFamily")));       
		        
		        BatchUpdateDocumentRequest body = new BatchUpdateDocumentRequest().setRequests(requests);
		        BatchUpdateDocumentResponse response2 = dService.documents()
		                .batchUpdate(file.getId(), body).execute();

		        System.out.printf("The title of the doc is: %s\n", title);
			}
			
			return true;
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
			return false;
		}
   
    }
    

}