import org.rauschig.jarchivelib.*;

import java.io.File;
import java.io.IOException;

public class JALTest {

    public static void main(String[] args) throws IOException {
        System.out.println("Fal?");

        //jie ya
//        File archive = new File("/home/unv/Desktop/test.old.tar.7z");
//        File destination = new File("/home/unv/Desktop");
//
//        System.out.println(FileType.get(archive));

//        Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR);
//        archiver.extract(archive, destination);

        //yasuo
        String archiveName = "archive";
        File destination = new File("/home/unv/Desktop");
        File source = new File("/home/unv/Desktop/libo.py");

        Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR, CompressionType.GZIP);
        File archive = archiver.create(archiveName, destination, source);


        //stream 解压
//        ArchiveStream stream = archiver.stream(archive);
//        ArchiveEntry entry;
//        System.out.println(destination + File.separator + archive.getName().substring(0, archive.getName().lastIndexOf(".tar")));
//        while((entry = stream.getNextEntry()) != null) {
//            // access each archive entry individually using the stream
//            // or extract it using entry.extract(destination)
//            // or fetch meta-data using entry.getName(), entry.isDirectory(), ...
//            System.out.println(entry.getName() + ":" + entry.getSize());
//            entry.extract(destination);
//        }
//        stream.close();
//        File oldFile = new File(destination + File.separator + archive.getName().substring(0, archive.getName().lastIndexOf(".tar")));
//        File newFile = new File(destination + File.separator + "newname");
//        oldFile.renameTo(newFile);
    }
}
