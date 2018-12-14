package top.emptystack.jfinger.lib;

import javafx.scene.control.ProgressBar;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UnZipTest {

    @Test
    public void doit() throws IOException {
        assertEquals("进度条比1大了",
                0,
                UnZip.doit("/home/unv/Desktop/LICENSE.zip",
                        "/home/unv/Desktop",
                        "license",
                        0));
    }
}