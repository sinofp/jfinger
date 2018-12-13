package top.emptystack.jfinger.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import top.emptystack.jfinger.lib.MyZip;


public class Controller implements Initializable {

    private FileChooser fileChooser = new FileChooser();
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    @FXML
    Button btn_zf;

    @FXML
    Button btn_uz;

    @FXML
    Label mLabel;

    public void unZip() throws Exception {
        File zipFile = fileChooser.showOpenDialog(new Stage());
        if (null != zipFile) {
            mLabel.setText(zipFile.getAbsolutePath());
            File destDir = directoryChooser.showDialog(new Stage());
            if (null != destDir) {
                mLabel.setText(destDir.getAbsolutePath());
                //todo 链接到Myzip类解压
                MyZip.unZip(zipFile.getAbsoluteFile(), destDir.getAbsolutePath());
                mLabel.setText("压缩到了" + destDir.getAbsolutePath());
            } else {
                mLabel.setText("没有选择要解压到的文件夹");
            }
        } else {
            mLabel.setText("没有选择要解压的文件");
        }
    }

    public void zipFiles() {
        File inDir = directoryChooser.showDialog(new Stage());
        if(inDir!= null){
            mLabel.setText(inDir.getAbsolutePath());
            File outDir = directoryChooser.showDialog(new Stage());
            if (outDir != null) {
                mLabel.setText(outDir.getAbsolutePath());
                System.out.println(inDir.getAbsolutePath());
                System.out.println(inDir.getName());
                System.out.println(outDir.getAbsolutePath());
                System.out.println(outDir.getAbsolutePath() + inDir.getName()+ ".zip");
                ZipUtil.pack(new File(inDir.getAbsolutePath()), new File(outDir.getAbsolutePath() + File.separator + inDir.getName()+ ".zip"));
                mLabel.setText("压缩到了"+outDir.getAbsolutePath());
            } else {
                mLabel.setText("没有选择要压缩到的文件夹");
            }
        }else {
            mLabel.setText("没有选择要压缩的文件夹");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_zf.setText("ZIP");
        mLabel.setText("点击ZIP压缩，点击UNZIP解压缩");
    }
}
