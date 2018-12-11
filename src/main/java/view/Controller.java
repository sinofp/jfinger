package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {

    FileChooser fileChooser = new FileChooser();

    @FXML
    Button btn_zf;
    @FXML
    Label mLabel;

    public void chooseFile() {
        File file = fileChooser.showOpenDialog(new Stage());
        if(file != null){
            mLabel.setText(file.getAbsolutePath());
        }else {
            mLabel.setText("没有打开任何文件");
        }
    }
}
