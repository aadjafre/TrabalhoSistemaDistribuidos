package TrabSistemaDistribuidos;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        // Have the user sign in and authorize your app.
        DropBoxService dropBoxService = new DropBoxServiceImpl();
        File f = new File("D:\\Documentos\\Estudo\\working-draft.txt");
        dropBoxService.uploadFile(f);
        dropBoxService.listFolders();
        dropBoxService.downloadFile("magnum-opus.txt", "C:\\Users\\Antenor\\Desktop\\");
    }
}
