package top.emptystack.jfinger.lib;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;

public class MyZip {
    private static final int BUFFER_SIZE = 1024;

    private static String fileName(String targetDir, ArchiveEntry entry) {
        return targetDir + entry.toString();
    }

    public static void unZip(File zipFile, String destDir) throws Exception {
        String targetDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        targetDir += zipFile.getName().substring(0, zipFile.getName().lastIndexOf(".zip")) + File.separator;
        System.out.println("targetDir:" + targetDir);


        try (ZipArchiveInputStream i = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE))) {
            ArchiveEntry entry = null;
            while ((entry = i.getNextEntry()) != null) {
                if (!i.canReadEntryData(entry)) {
                    // log something?
                    System.out.println("can't read entry" + entry);
                    continue;
                }
                System.out.println(targetDir + entry);
                String name = fileName(targetDir, entry);
                File f = new File(name);
                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new IOException("failed to create directory " + f);
                    }
                } else {
                    File parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("failed to create directory " + parent);
                    }
                    try (OutputStream o = Files.newOutputStream(f.toPath())) {
                        IOUtils.copy(i, o);
                    }
                }
            }
        }
    }

    public static void unZip(String zipfile, String destDir) throws Exception {
        File zipFile = new File(zipfile);
        unZip(zipFile, destDir);
    }

//    public static void zip(String oldDir, String newDir) throws IOException {
////        Collection<File> filesToArchive = ...
//        newDir = newDir.endsWith(File.separator)? newDir: newDir + File.separator;
//        newDir = newDir + new File(oldDir).getName()+ ".zip";
//        System.out.println(newDir);
//        try (ArchiveOutputStream o = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(newDir), BUFFER_SIZE))) {
//             File f = new File(oldDir);
//            System.out.println(f.getAbsolutePath());
////                // maybe skip directories for formats like AR that don't store directories
////                ArchiveEntry entry = o.createArchiveEntry(f, entryName(f));
////                // potentially add more flags to entry
////                o.putArchiveEntry(entry);
////                if (f.isFile()) {
////                    try (InputStream i = Files.newInputStream(f.toPath())) {
////                        IOUtils.copy(i, o);
////                    }
////                }
////                o.closeArchiveEntry();
//
////            out.finish();
//        }
//    }


    public static void main(String[] args) throws Exception {
        System.out.println("hi");
        unZip("/home/unv/Desktop/test.zip", "/home/unv/Desktop/");
//        zip("/home/unv/Desktop/echo","/home/unv/Desktop/");
    }
}
