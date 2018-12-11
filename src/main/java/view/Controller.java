package view;

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

public class Controller implements Initializable {

    FileChooser fileChooser = new FileChooser();
    DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    Button btn_zf;

    @FXML
    Label mLabel;

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
                ZipUtil.pack(new File(inDir.getAbsolutePath()), new File(outDir.getAbsolutePath() + "/" + inDir.getName()+ ".zip"));
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
        mLabel.setText("点左边的按钮选择要压缩的文件夹、要压缩到的文件夹");
    }
}
