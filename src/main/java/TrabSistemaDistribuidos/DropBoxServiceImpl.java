package TrabSistemaDistribuidos;

import com.dropbox.core.*;

import java.io.*;
import java.util.Locale;

/**
 * Created by Antenor on 05/06/2015.
 */
public class DropBoxServiceImpl implements DropBoxService {
    static{
        System.out.println("Insert app_key and app_secret");
    }
    final String APP_KEY = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
    final String APP_SECRET = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
    final DbxRequestConfig config = new DbxRequestConfig(
            "JavaTutorial/1.0", Locale.getDefault().toString());
    String accessToken;
    DbxClient client;

    public DropBoxServiceImpl() throws IOException {
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        DbxWebAuthNoRedirect webAuthNoRedirect = new DbxWebAuthNoRedirect(config, appInfo);
        String authorizeUrl = webAuthNoRedirect.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        try {
            String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
            DbxAuthFinish authFinish = webAuthNoRedirect.finish(code);
            accessToken = authFinish.accessToken;
            client = new DbxClient(config, accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

    public void uploadFile(File file) throws IOException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            System.out.println("Linked account: " + client.getAccountInfo().displayName);
            DbxEntry.File uploadedFile = client.uploadFile("/"+file.getName(),
                    DbxWriteMode.add(), file.length(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public void listFolders() {
        DbxEntry.WithChildren listing = null;
        try {
            listing = client.getMetadataWithChildren("/");
        } catch (DbxException e) {
            e.printStackTrace();
        }
        System.out.println("Files in the root path:");
        if (listing != null) {
            for (DbxEntry child : listing.children) {
                System.out.println("	" + child.name + ": " + child.toString());
            }
        }
    }

    public void downloadFile(String fileName, String path) throws IOException {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path + File.separator + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            DbxEntry.File downloadedFile = client.getFile("/"+fileName, null,
                    outputStream);
            System.out.println("Metadata: " + downloadedFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public void deleteFile(File file) {

    }
}
