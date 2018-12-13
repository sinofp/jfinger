package top.emptystack.jfinger.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCb();
    }

    @FXML
    ComboBox<String> cb;

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
        cb.setValue("ZIP");
        cb.setTooltip(new Tooltip("选择你期望的压缩格式"));
    }

    private FileChooser fileChooser = new FileChooser();
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
        File file = fileChooser.showOpenDialog(new Stage());
        if (null != file) {
            //todo 检查是否可以解压
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

    public void goZip() {
        String format = cb.getValue();

        String archiveName = null;
        if (!txt_zip.getText().isEmpty()) {
            // 直接用getText赋值的话，如果什么都没写也不返回空，会导致下面的检测失效
            archiveName = txt_zip.getText();
        }

        File destination = new File(destPath);
        File source = new File(srcPath);

        if (null == archiveName) {
            archiveName = source.getName();
        }

        System.out.println(archiveName);
        System.out.println(destPath);
        System.out.println(srcPath);
        if (format.startsWith("TAR")) {
            if (!format.endsWith("TAR")) {
                Archiver archiver = ArchiverFactory.createArchiver("tar", format.substring(4).toLowerCase());
//                System.out.println(format.substring(3).toLowerCase());
                try {
                    File archive = archiver.create(archiveName, destination, source);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Archiver archiver = ArchiverFactory.createArchiver(format.toLowerCase());
            try {
                File archive = archiver.create(archiveName, destination, source);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void goUnZip() {
        File archive = new File(arcPath);
        File destination = new File(destPath);

        Archiver archiver = ArchiverFactory.createArchiver(archive);
        try {
            archiver.extract(archive, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
