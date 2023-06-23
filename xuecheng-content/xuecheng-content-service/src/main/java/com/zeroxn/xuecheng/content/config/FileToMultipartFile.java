package com.zeroxn.xuecheng.content.config;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Author: lisang
 * @DateTime: 2023/6/23 下午3:09
 * @Description: File转MultipartFile的实现类
 */
public class FileToMultipartFile implements MultipartFile {
    private final File file;
    public FileToMultipartFile(File file){
        this.file = file;
    }
    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return file.getName();
    }

    @Override
    public String getContentType() {
        return "application/octet-stream";
    }

    @Override
    public boolean isEmpty() {
        return file.length() == 0;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public Resource getResource() {
        return MultipartFile.super.getResource();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        Files.copy(file.toPath(), dest.toPath());
    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {
        Files.copy(file.toPath(), dest.toAbsolutePath());
    }
}
