package top.emptystack.jfinger.lib;

import javafx.scene.control.ProgressBar;
import org.rauschig.jarchivelib.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnZip {
    public static void doit(String arcPath, String destPath, String name, ProgressBar pb, double progress) throws IOException {
        File archive = new File(arcPath);
        File destination = new File(destPath);
        Archiver archiver = ArchiverFactory.createArchiver(archive);

        ArchiveStream stream;
        ArchiveEntry entry;

        String oldName = null;

        double totalSize = 0;

//        List<ArchiveEntry> entryList = new ArrayList<>();
        int entryCnt = 0;
        stream = archiver.stream(archive);
        while ((entry = stream.getNextEntry()) != null) {
            totalSize += entry.getSize();
            System.out.println(entry.getName());
//            entryList.add(entry);
            entryCnt ++;
        }

        boolean stillTar = false;
        boolean oneCommonFile = false;
        if (1 == entryCnt) {
            entry = archiver.stream(archive).getNextEntry();
            if (".tar".equals(FileType.get(entry.getName()).toString())) {
                oldName = entry.getName();
                stillTar = true;
            } else {
                oneCommonFile = true;
            }

        } else {
            //有n多文件，一解压乱死人！
            oldName = archive.getName();
            oldName = oldName.substring(0, oldName.lastIndexOf(FileType.get(oldName).toString()));
            destPath = destPath.endsWith(File.separator) ? destPath : destPath + File.separator;
            destination = new File(destPath + oldName);
            destination.mkdir();
        }


        if (stillTar) {
            stream = archiver.stream(archive);
            while ((entry = stream.getNextEntry()) != null) {
                entry.extract(destination);
            }
            stream.close();
            //递归解压tar
            destPath = destPath.endsWith(File.separator) ? destPath : destPath + File.separator;
            archive = new File(destPath + oldName);
            doit(archive.getAbsolutePath(), destPath, name, pb, 0.5);
            archive.delete();
        } else {
            stream = archiver.stream(archive);
            while ((entry = stream.getNextEntry()) != null) {
                entry.extract(destination);
                //todo 连接progressBar
//                System.out.println(doneSize / totalSize);
                progress += entry.getSize()/totalSize;
                pb.setProgress(progress);
            }
            stream.close();
            if (!oneCommonFile) {
                destPath = destPath.endsWith(File.separator) ? destPath : destPath + File.separator;
                File file_oldName = new File(destPath + oldName);
                File file_newName = new File(destPath + name);
                file_oldName.renameTo(file_newName);
            }
        }
    }


//    public static void main(String[] args) throws IOException {
//        doit("/home/unv/Desktop/echo2.til.tar.7z", "/home/unv/Desktop", "suckname");
//    }
}
