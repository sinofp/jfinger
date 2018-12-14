package top.emptystack.jfinger.lib;

import javafx.scene.control.ProgressBar;
import org.rauschig.jarchivelib.*;

import java.io.File;
import java.io.IOException;



public class UnZip {
    /**
     * 在解压的同时设置进度条，利用ArchiveStream类实现
     * @param arcPath 要解压的地址
     * @param destPath 要解压到的地址
     * @param name 解压后要改名的名字，里面只有单个文件的话不改名
     * @param pb javaFX初始化的进度条
     * @param progress progressbar的进度
     * @throws IOException 为了可以继续运行，扔掉
     */
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

    /**
     * 专门为junit写的克隆函数，因为junit好像没法正确初始化Progress Bar，且需要有返回值
     * @param arcPath 要解压的地址
     * @param destPath 要解压到的地址
     * @param name 解压后要改名的名字，里面只有单个文件的话不改名
     * @param progress progressbar的进度
     * @return 进度条大于1的话返回1代表错误
     * @throws IOException 为了可以继续运行，扔掉
     */
    public static int doit(String arcPath, String destPath, String name, double progress) throws IOException {
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

        boolean junitWrong = false;

        if (stillTar) {
            stream = archiver.stream(archive);
            while ((entry = stream.getNextEntry()) != null) {
                entry.extract(destination);
            }
            stream.close();
            //递归解压tar
            destPath = destPath.endsWith(File.separator) ? destPath : destPath + File.separator;
            archive = new File(destPath + oldName);
            doit(archive.getAbsolutePath(), destPath, name,0.5);
            archive.delete();
        } else {
            stream = archiver.stream(archive);
            while ((entry = stream.getNextEntry()) != null) {
                entry.extract(destination);
                //todo 连接progressBar
//                System.out.println(doneSize / totalSize);
                progress += entry.getSize()/totalSize;
                if (progress > 1) junitWrong = true;

            }
            stream.close();
            if (!oneCommonFile) {
                destPath = destPath.endsWith(File.separator) ? destPath : destPath + File.separator;
                File file_oldName = new File(destPath + oldName);
                File file_newName = new File(destPath + name);
                file_oldName.renameTo(file_newName);
            }
        }
        if (junitWrong) return 1;
        else return 0;
    }


//    public static void main(String[] args) throws IOException {
//        doit("/home/unv/Desktop/echo2.til.tar.7z", "/home/unv/Desktop", "suckname");
//    }
}
