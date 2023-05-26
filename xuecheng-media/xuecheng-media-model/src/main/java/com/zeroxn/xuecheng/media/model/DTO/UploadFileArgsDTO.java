package com.zeroxn.xuecheng.media.model.DTO;

import lombok.Data;

/**
 * @Author: lisang
 * @DateTime: 2023/5/25 下午8:19
 * @Description:
 */
@Data
public class UploadFileArgsDTO {
    private String filename;
    private String fileType;
    private String args;
    private String remark;
    private String username;
    private Long fileSize;

}
