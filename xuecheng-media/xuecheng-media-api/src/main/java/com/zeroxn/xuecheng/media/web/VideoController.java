package com.zeroxn.xuecheng.media.web;

import com.zeroxn.xuecheng.media.model.DTO.RestResponse;
import com.zeroxn.xuecheng.media.service.MediaFilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: lisang
 * @DateTime: 2023/5/26 下午6:06
 * @Description:
 */
@RestController
@Tag(name = "视频上传管理接口")
public class VideoController {
    private final MediaFilesService mediaFilesService;
    public VideoController(MediaFilesService mediaFilesService){
        this.mediaFilesService = mediaFilesService;
    }
    @PostMapping("/upload/checkfile")
    @Operation(summary = "检查文件是否已经上传")
    @Parameter(name = "fileMd5", description = "文件的MD5码", required = true)
    public RestResponse<Boolean> checkFile(@RequestParam("fileMd5") String fileMd5) throws IOException {
        return mediaFilesService.checkFile(fileMd5);
    }
    @PostMapping("/upload/checkchunk")
    @Operation(summary = "检查文件分段是否上传")
    @Parameter(name = "fileMd5", description = "文件的MD5码", required = true)
    @Parameter(name = "chunk", description = "分片文件的分片索引", required = true)
    public RestResponse<Boolean> checkChunk(@RequestParam("fileMd5") String fileMd5,
                                            @RequestParam("chunk") int chunk) throws IOException{
        return mediaFilesService.checkChunk(fileMd5, chunk);
    }
    @PostMapping("/upload/uploadchunk")
    @Operation(summary = "上传分块文件")
    @Parameter(name = "file", description = "文件的二进制流", required = true)
    @Parameter(name = "fileMd5", description = "文件的MD5码", required = true)
    @Parameter(name = "chunk", description = "文件分段的索引", required = true)
    public RestResponse<Boolean> uploadChunk(@RequestPart("file") MultipartFile file,
                                            @RequestParam("fileMd5") String fileMd5,
                                            @RequestParam("chunk") int chunk) throws IOException{
        File tempFile = File.createTempFile("upload", ".temp");
        file.transferTo(tempFile);
        String filePath = tempFile.getAbsolutePath();
        return mediaFilesService.uploadChunk(fileMd5, chunk, filePath);
    }
    @PostMapping("/upload/mergechunks")
    @Operation(summary = "合并文件分段请求")
    public RestResponse<Boolean> chunkMerge(@RequestParam("fileMd5") String fileMd5, @RequestParam("fileName") String fileName,
                                           @RequestParam("chunkTotal") Integer chunkTotal) throws IOException {
        Long companyId = 1232141425L;
        return mediaFilesService.chunkMerge(companyId, fileMd5, chunkTotal, fileName);
    }
}
