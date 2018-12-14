package top.emptystack.jfinger.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.rauschig.jarchivelib.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        pb_zip.setProgress(0.33);
        Font font = Font.loadFont(getClass().getResourceAsStream("SourceHanSansCN-Normal.otf"), 16);
//        System.out.println(font);
        setZippedFileChooser();
        setCb();
        setLables();
        setTextFields();

    }

    private void setTextFields() {
        txt_zip.setText("这里压缩后的文件名，默认原文件名");
        txt_unzip.setText("这里写解压后的文件夹名，默认原文件名，单个文件不会改名");
    }

    private void setLables() {
//        lbl_src.setFont(Font.font(30));
        lbl_src.setText("点左边选要压缩的文件或文件夹");
        lbl_arc.setText("点左边选要解压的文件");
        lbl_dest.setText("点左边选要压缩到的文件夹");
        lbl_destE.setText("点左边选要解压到的文件夹");
    }


    //下面两个函数会Exception,但不影响运行
    @FXML
    ProgressBar pb_zip;

    public void clearPbZip() {
        setCb();
        setLables();
        setTextFields();
        pb_zip.setProgress(0);
    }

    @FXML
    ProgressBar pb_unzip;

    public void clearPbUnZip() {
        setCb();
        setLables();
        setTextFields();
        pb_unzip.setProgress(0);
    }

    @FXML
    ComboBox<String> cb;

    private void setZippedFileChooser() {
        zippedFileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Literally All Compressed Files", "*.*"),
                new FileChooser.ExtensionFilter("AR", "*.ar"),
                new FileChooser.ExtensionFilter("CPIO", "*.cpio"),
                new FileChooser.ExtensionFilter("DUMP", "*.dump"),
                new FileChooser.ExtensionFilter("JAR", "*.jar"),
                new FileChooser.ExtensionFilter("SEVEN_Z", "*.7z"),
                new FileChooser.ExtensionFilter("TAR", "*.tar"),
                new FileChooser.ExtensionFilter("TAR.BZ2", "*.tar.bz2"),
                new FileChooser.ExtensionFilter("TAR.GZ", "*.tar.gz"),
                new FileChooser.ExtensionFilter("TAR.XZ", "*.tar.xz"),
                new FileChooser.ExtensionFilter("TAR.PACK", "*.tar.pack"),
                new FileChooser.ExtensionFilter("ZIP", "*.zip")
        );
    }

    private void setCb() {
        ObservableList<String> options =
                FXCollections.observableArrayList();

        for (ArchiveFormat f :
                ArchiveFormat.values()) {
//            System.out.println(f.toString());
            options.add(f.toString());
            if ("TAR".equals(f.toString())) {
                for (CompressionType t :
                        CompressionType.values()) {
//                    System.out.println("TAR." + t.toString());
                    options.add("TAR." + t.toString());
                }
            }
        }

        cb.setItems(options);
        cb.setValue("Zip");
        cb.setTooltip(new Tooltip("选择你期望的压缩格式"));
    }

    private FileChooser fileChooser = new FileChooser();
    private FileChooser zippedFileChooser = new FileChooser();
    private DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    Label lbl_src;

    @FXML
    Label lbl_dest;

    private String srcPath;

    public void chooseScourceFile() {
        File file = fileChooser.showOpenDialog(new Stage());
        if (null != file) {
            srcPath = file.getAbsolutePath();
            lbl_src.setText(srcPath);
        } else {
            lbl_src.setText("请选择要压缩的文件/文件夹！");
        }
    }

    public void chooseScourceDir() {
        File file = directoryChooser.showDialog(new Stage());
        if (null != file) {
            srcPath = file.getAbsolutePath();
            lbl_src.setText(srcPath);
        } else {
            lbl_src.setText("请选择要压缩的文件/文件夹！");
        }
    }

    @FXML
    Label lbl_arc;

    private String arcPath;

    public void chooseArc() {
        File file = zippedFileChooser.showOpenDialog(new Stage());
        if (null != file) {
            //!ArchiveFormat.isValidArchiveFormat(archiveFormat);
            arcPath = file.getAbsolutePath();
            lbl_arc.setText(arcPath);
        } else {
            lbl_arc.setText("请选择要解压的文件！");
        }
    }


    private String destPath;

    public void chooseDest() {
        File file = directoryChooser.showDialog((new Stage()));
        if (null != file) {
            destPath = file.getAbsolutePath();
            lbl_dest.setText(destPath);
        } else {
            lbl_dest.setText("请选择要压缩到的文件夹！");
        }
    }

    @FXML
    Label lbl_destE;

    public void chooseDestE() {
        File file = directoryChooser.showDialog((new Stage()));
        if (null != file) {
            destPath = file.getAbsolutePath();
            lbl_destE.setText(destPath);
        } else {
            lbl_destE.setText("请选择要解压到的文件夹！");
        }
    }

    @FXML
    TextField txt_zip;

    public void goZip() throws IOException {
        String format = cb.getValue();

        String archiveName = null;
        if (!txt_zip.getText().isEmpty()) {
            // 直接用getText赋值的话，如果什么都没写也不返回空，会导致下面的检测失效
            archiveName = txt_zip.getText();
        }

        File destination = new File(destPath);
        File source = new File(srcPath);

        if (null == archiveName || archiveName.equals("这里压缩后的文件名，默认原文件名")) {
            archiveName = source.getName();
        }
        pb_zip.setProgress(0.3);
        System.out.println(archiveName);
        System.out.println(destPath);
        System.out.println(srcPath);
        if (format.startsWith("TAR")) {
            if (!format.endsWith("TAR") && "TAR".equals(format)) {
                String cformat = format.substring(4).toLowerCase();
                if ("seven_z".equals(cformat)) {
                    cformat = "7z";
                } else if ("gzip".equals(cformat)) {
                    cformat = "gz";
                }
                Archiver archiver = ArchiverFactory.createArchiver("tar", cformat);
//                System.out.println(format.substring(3).toLowerCase());

                File archive = archiver.create(archiveName, destination, source);
                pb_zip.setProgress(1);

            }
        } else {
            String cformat = format.toLowerCase();
            if ("seven_z".equals(cformat)) {
                cformat = "7z";
            }
            Archiver archiver = ArchiverFactory.createArchiver(cformat);

            File archive = archiver.create(archiveName, destination, source);
            pb_zip.setProgress(1);

        }
    }


    @FXML
    TextField txt_unzip;

    public void goUnZip() throws IOException {
        File archive = new File(arcPath);
//        File destination = new File(destPath);
        String name = null;
        if (!txt_unzip.getText().isEmpty() && !txt_unzip.getText().equals("这里写解压后的文件夹名，默认原文件名，单个文件不会改名")) {
            // 直接用getText赋值的话，如果什么都没写也不返回空，会导致下面的检测失效
            name = txt_unzip.getText();
        } else {
            name = archive.getName().substring(0, archive.getName().lastIndexOf(FileType.get(archive).toString()));
        }
        top.emptystack.jfinger.lib.UnZip.doit(arcPath, destPath, name, pb_unzip, 0);
    }
}


