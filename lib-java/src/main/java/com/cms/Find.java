package com.cms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by WD on 2017/10/22.
 */

public class Find {


    public static void main(String[] args) {
        String path = "E:\\PalmHall\\CMCC_CODE\\CMCC\\res\\layout";
        File file = new File(path);
        String[] list = file.list();
        int length = list.length;
        System.out.println("file.size=" + length);
        for (int i = 0; i < length; i++) {
            String onePath = list[i];
            File oneFile = new File(path, onePath);
            FileInputStream fis = null;
            FileReader fr = null;
            char[] b = new char[1];
            try {
                //                System.out.print("寻找当前文件="+onePath);
                fr = new FileReader(oneFile);
                fr.read(b, 0, 1);
                //                System.out.println("the first c of the file = "+b[0]);
                if (b[0] == 0xfeff) {
                    System.out.println("=========================当前文件出现异常字符， file=" + onePath);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //            System.out.println("the first c of the file = "+b[0]);
            if (b[0] == 0xfeff) {

            }
        }
    }
}
