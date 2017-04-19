import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.nio.file.attribute.FileTime;
import java.nio.file.LinkOption;


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
        // response.write("Method handle not implemented.".getBytes());
        // response.write(NEW_LINE.getBytes());

        Path path_1 = Paths.get(request.split(" ")[1]);

        String[] parts = request.split("\\ ");
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
        else if (!Files.exists(path_1)){
            response.write("HTTP/1.1 404 Not Found".getBytes());
            response.write(NEW_LINE.getBytes());
        }
        else {
            response.write("HTTP/1.1 200 OK".getBytes());
            response.write(NEW_LINE.getBytes());

            DateFormat dateFormat_1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
            response.write("Date: ".getBytes());
            response.write(dateFormat_1.format(new Date()).getBytes());
            response.write(NEW_LINE.getBytes());

            DateFormat dateFormat_2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
            FileTime fileTime = Files.getLastModifiedTime(path_1);
            response.write("Last-Modified: ".getBytes());
            response.write(dateFormat_1.format(fileTime.toMillis()).getBytes());
            response.write(NEW_LINE.getBytes());
        }

        // GET ./www-root/humans.txt HTTP/1.1

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
