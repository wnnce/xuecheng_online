package com.zeroxn.xuecheng.media;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/26 下午3:01
 * @Description:
 */
public class FileTest {
    @Test
    public void testFileChunk() throws IOException {
        File file = new File("/home/lisang/Videos/chunk/video.mp4");
        String chunkFilePath = "/home/lisang/Videos/chunk/temp/";
        int chunkSize = 1024 * 1024 * 5;
        int chunkNum = (int) Math.ceil(file.length() * 1.0 / chunkSize);
        RandomAccessFile sourceFile = new RandomAccessFile(file, "r");
        byte[] bytes = new byte[chunkSize / 1024];
        for (int i = 0; i < chunkNum; i++) {
            File tempFile = new File(chunkFilePath + i);
            RandomAccessFile accessFile = new RandomAccessFile(tempFile, "rw");
            int len = -1;
            while ((len = sourceFile.read(bytes) )!= -1){
                accessFile.write(bytes, 0, len);
                if (tempFile.length() >= chunkSize){
                    break;
                }
            }
            accessFile.close();
        }
        sourceFile.close();
    }
    @Test
    public void testFileMerge() throws IOException {
        String chunkFilePath = "/home/lisang/Videos/chunk/temp/";
        File file = new File(chunkFilePath);
        File[] files = file.listFiles();
        List<File> chunkFiles = Arrays.asList(files);
        Collections.sort(chunkFiles, new Comparator<File>() {
            @Override
            public int compare(File file, File t1) {
                int file1 = Integer.parseInt(file.getName());
                int file2 = Integer.parseInt(t1.getName());
                return file1 - file2;
            }
        });
        File targetFile = new File(chunkFilePath + "video-2.mp4");
        RandomAccessFile accessFile = new RandomAccessFile(targetFile, "rw");
        byte[] bytes = new byte[1024 * 5];
        for (File item : chunkFiles){
            RandomAccessFile fileaa = new RandomAccessFile(item, "r");
            int len = -1;
            while ((len = fileaa.read(bytes)) != -1){
                accessFile.write(bytes, 0 ,len);
            }
            fileaa.close();
        }
        accessFile.close();
    }
}
