package com.jiangjiang.readimgs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileTest {


    public static void main(String[] args) {

        String hello = "hello";

        String path = System.getProperty("user.dir") + File.separator + "CMB" + File.separator;
        System.out.println(path);
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream =
                    new FileOutputStream(path + "hello.txt");
            try {
                fileOutputStream.write(hello.getBytes());
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
