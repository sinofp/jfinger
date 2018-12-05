import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        System.out.println("hello, input 'z' to zip and 'u' to unzip:");
        Scanner scan = new Scanner(System.in);
        if (scan.hasNext()) {
            String zOrU = scan.next();
            System.out.println("ok, then input the absolute path please:");
            if (scan.hasNext()) {
                String dir = scan.next();
                if (zOrU.equals("z")) {
                    ZipUtil.pack(new File(dir), new File(dir + ".zip"));
                } else if (zOrU.equals("u")) {
                    ZipUtil.unpack(new File(dir), new File(dir.substring(0, dir.length() - 4)));
                }
            }
            System.out.println("done");
        }
        scan.close();
    }
}
