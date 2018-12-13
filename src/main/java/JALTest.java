import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;

import java.io.File;
import java.io.IOException;

public class JALTest {

    public static void main(String[] args) throws IOException {
        System.out.println("Fal?");

        //jie ya
        File archive = new File("/home/unv/Desktop/echo.tar");
        File destination = new File("/home/unv/Desktop");

        Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR);
        archiver.extract(archive, destination);

        //yasuo
//        String archiveName = "archive";
//        File destination = new File("/home/unv/Desktop");
//        File source = new File("/home/unv/Desktop/echo2");
//
//        Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR, CompressionType.GZIP);
//        File archive = archiver.create(archiveName, destination, source);
    }
}
