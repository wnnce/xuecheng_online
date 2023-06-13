package com.zeroxn.xuecheng.media.utils;

import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: lisang
 * @DateTime: 2023/5/26 下午6:53
 * @Description:
 */
@Component
@Slf4j
public class MinioUtils {
    private final MinioClient minioClient;
    public MinioUtils(MinioClient minioClient){
        this.minioClient = minioClient;
    }

    /**
     * 上传文件到Minio
     * @param bucket 存储桶
     * @param fileName 文件在本地的路径
     * @param object 文件在Minio的路径
     * @return 返回true/false
     */
    public boolean uploadFile(String bucket, String fileName, String object) {
        try {
            minioClient.uploadObject(UploadObjectArgs.builder()
                    .bucket(bucket)
                    .filename(fileName)
                    .object(object)
                    .build());
            return true;
        } catch (Exception ex) {
            log.error("minio上传文件报错，错误消息：{}", ex.getMessage());
        }
        return false;
    }

    /**
     * 下载Minio的文件到本地临时文件
     * @param bucket 存储桶
     * @param filePath 文件在minio的路径
     * @return 临时文件
     */
    public File cloneFile(String bucket, String filePath){
        try{
            InputStream inputStream  = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .build());
            File tempFile = File.createTempFile("minioDw", ".temp");
            OutputStream outputStream = new FileOutputStream(tempFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
            return tempFile;
        }catch (Exception ex){
            log.error("minio获取文件出错，错误消息：{}", ex.getMessage());
        }
        return null;
    }

    /**
     * 合并Minio中的文件
     * @param bucket 存储桶
     * @param sourceList 需要合并的分片文件
     * @param object 文件合并后的路径
     * @return 返回true/false
     */
    public boolean mergeFile(String bucket, List<ComposeSource> sourceList, String object){
        try {
            minioClient.composeObject(ComposeObjectArgs.builder().bucket(bucket).object(object).sources(sourceList).build());
            return true;
        }catch (Exception ex){
            log.error("minio合并文件出错，错误消息：{}", ex.getMessage());
        }
        return false;
    }

    /**
     * 删除Minio中的多个文件
     * @param bucket 存储桶
     * @param chunkFilePathList 需要删除的文件路径列表
     * @return 返回true/false
     */
    public boolean deleteFiles(String bucket, List<String> chunkFilePathList){
        try{
            for (String path : chunkFilePathList) {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(path).build());
            }
            return true;
        }catch (Exception ex){
            log.error("minio删除多个文件出错，错误消息：{}", ex.getMessage());
        }
        return false;
    }
    public String getMinioFileChunkPath(String fileMd5){
        return fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/" + "chunk" + "/";
    }
    public String getMinioFilePath(String fileMd5){
        return fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/";
    }
}
