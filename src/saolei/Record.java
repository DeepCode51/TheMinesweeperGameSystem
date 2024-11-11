package saolei;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Record {

    BufferedReader br;
    FileReader in;

    public Record() {

    }

    // 读取扫雷级别
    public String readLevel() {
        String s = "";
        int c;
        try {
            in = new FileReader("/record/level.txt");
            br = new BufferedReader(in);
            while ((c = br.read()) != -1) {
                s = s + (char) c;
            }
            br.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    // 读取自定义方格数及雷数
    public String readLevel4() {
        String s = "";
        int c;
        try {
            in = new FileReader("/record/level4.txt");
            br = new BufferedReader(in);
            while ((c = br.read()) != -1) {
                s = s + (char) c;
            }
            br.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
