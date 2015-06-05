package TrabSistemaDistribuidos;

import java.io.File;
import java.io.IOException;

/**
 * Created by Antenor on 05/06/2015.
 */
public interface DropBoxService {

    void uploadFile(File file) throws IOException;

    void listFolders();

    void downloadFile(String fileName, String path) throws IOException;

    void deleteFile(File file);

}
