import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.nio.file.attribute.FileTime;
import javax.activation.MimeType;
import javax.activation.MimetypesFileTypeMap;

/**
 * Request handler for HTTP/1.1 GET requests.
 */
public class FileRequestHandler {

    private final Path documentRoot;
    private static final String NEW_LINE = System.lineSeparator();

    public FileRequestHandler(Path documentRoot) {
        this.documentRoot = documentRoot;
    }

    /**
     * Called to handle an HTTP/1.1 GET request: first, the status code of the
     * request is determined and a corresponding response header is sent.
     * If the status code is <200>, the requested document root path is sent
     * back to the client. In case the path points to a file, the file is sent,
     * and in case the path points to a directory, a listing of the contained
     * files is sent.
     *
     * @param request Client request
     * @param response Server response
     */
    public void handle(String request, OutputStream response)
    throws IOException {

        // get path of the data
        Path path = Paths.get(request.split(" ")[1]);
        // get the three arguments
        String[] parts = request.split("\\ ");

        // start testing
        if (parts.length != 3) {
            response.write("HTTP/1.1 400 Bad Request".getBytes());
            response.write(NEW_LINE.getBytes());
        }
        else if (!parts[0].equals("GET")) {
            response.write("HTTP/1.1 501 Not Implemented".getBytes());
            response.write(NEW_LINE.getBytes());
        }
        else if (!parts[2].equals("HTTP/1.1")) {
            response.write("HTTP/1.1 505 HTTP Version Not Supported".getBytes());
            response.write(NEW_LINE.getBytes());
        }
        else if (!Files.exists(path)){
            response.write("HTTP/1.1 404 Not Found".getBytes());
            response.write(NEW_LINE.getBytes());
        }
        // cool, everything works!
        else {
            response.write("HTTP/1.1 200 OK".getBytes());
            response.write(NEW_LINE.getBytes());

            // date now
            DateFormat date_now = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
            response.write("Date: ".getBytes());
            response.write(date_now.format(new Date()).getBytes());
            response.write(NEW_LINE.getBytes());

            // content type
            response.write("Content-Type: ".getBytes());
            MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
            String fileType = mimeTypesMap.getContentType(parts[1]);
            response.write(fileType.getBytes());
            response.write(NEW_LINE.getBytes());

            // content length
            response.write("Content-Length: ".getBytes());
            long fileLength = Files.size(path);
            String fileSize = String.valueOf(fileLength);
            response.write(fileSize.getBytes());
            response.write(NEW_LINE.getBytes());

            // date of last modifing
            DateFormat last_mod = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
            FileTime fileTime = Files.getLastModifiedTime(path);
            response.write("Last-Modified: ".getBytes());
            response.write(last_mod.format(fileTime.toMillis()).getBytes());
            response.write(NEW_LINE.getBytes());
            response.write(NEW_LINE.getBytes());

            // lists the content of the folder, if it's a folder
            if (fileType == "application/octet-stream") {
                File folder = path.toFile();
                String[] folderContent = folder.list();
                for (int i = 0; i < folderContent.length; i++) {
                    response.write(folderContent[i].getBytes());
                    response.write(NEW_LINE.getBytes());
                }
            }
            // lists the content of the file, if it's not a folder
            else {
                File dataFile = path.toFile();
                if (dataFile.canRead()) {
                    response.write(Files.readAllBytes(path));
                }
            }
        }
        response.write(NEW_LINE.getBytes());

        // GET ./www-root/ HTTP/1.1
        // GET ./www-root/humans.txt HTTP/1.1
        // GET ./www-root/index.html HTTP/1.1

        /*
         * (a) Determine status code of the request and write proper status
         * line to the response output stream.
         *
         * Only continue if the request can be processed (status code 200).
         * In case the path points to a file (b) or a directory (c) write the
         * appropriate header fields and …
         *
         * (b) … the content of the file …
         * (c) … a listing of the directory contents …
         *
         * … to the response output stream.
         */
    }
}
