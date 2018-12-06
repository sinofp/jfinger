import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.zeroturnaround.zip.ZipInfoCallback;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;

import static javafx.application.Application.launch;

public class Test extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        List<String> dirs = new ArrayList<>();

//        // 列出zip内文件而不解压
        ZipUtil.iterate(new File("/home/unv/Desktop/test.zip"), zipEntry -> {
            System.out.println("Found " + zipEntry.getName());
            dirs.add(zipEntry.getName());
        });

        primaryStage.setTitle("treetest");
        TreeItem<String> rootItem = new TreeItem<>("ZIP");
        rootItem.setExpanded(true);
//        List<String> dirs = Arrays.<String>asList("test/", "test/中文/", "test/中文/test.txt", "test/中文/新建文件夹/", "test/中文/新建文件夹/新建Word文档.doc");

        for (String dir: dirs) {
            TreeItem<String> dirLeaf = new TreeItem<>(dir);
            boolean found = false;
            for (TreeItem<String> depItem: rootItem.getChildren()) {
                if (dir.startsWith(depItem.getValue())) {
                    // dir是以depitem开头的
                    int slashPos = dir.lastIndexOf('/', dir.length()-2); //todo windows 平台'\'
                    if (depItem.getValue().length()-1 == slashPos) {
                        depItem.getChildren().add(dirLeaf);
                        found = true;
                    }
                }
            }

            if (!found) {
                rootItem.getChildren().add(dirLeaf);
            }
        }

        TreeView<String> tree = new TreeView<String>(rootItem);
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
