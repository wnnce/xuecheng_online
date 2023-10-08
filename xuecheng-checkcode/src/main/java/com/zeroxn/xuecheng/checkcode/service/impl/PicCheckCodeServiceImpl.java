package com.zeroxn.xuecheng.checkcode.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zeroxn.xuecheng.checkcode.dto.CheckCodeParamsDto;
import com.zeroxn.xuecheng.checkcode.dto.CheckCodeResultDto;
import com.zeroxn.xuecheng.checkcode.service.AbstractCheckCodeService;
import com.zeroxn.xuecheng.checkcode.service.CheckCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Base64;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 19:48:45
 * @Description:
 */
@Service
public class PicCheckCodeServiceImpl extends AbstractCheckCodeService implements CheckCodeService {
    private static final Logger logger = LoggerFactory.getLogger(PicCheckCodeServiceImpl.class);
    private final DefaultKaptcha kaptcha;

    public PicCheckCodeServiceImpl(DefaultKaptcha kaptcha) {
        this.kaptcha = kaptcha;
    }

    @Autowired()
    @Override
    public void setCheckCodeGenerate(CheckCodeGenerate checkCodeGenerate) {
        this.checkCodeGenerate = checkCodeGenerate;
    }

    @Autowired
    @Override
    public void setKeyGenerate(KeyGenerate keyGenerate) {
        this.keyGenerate = keyGenerate;
    }

    @Autowired()
    @Override
    public void setCheckCodeStore(@Qualifier("RedisCheckCodeStore") CheckCodeStore checkCodeStore) {
        this.checkCodeStore = checkCodeStore;
    }

    @Override
    public CheckCodeResultDto generate(CheckCodeParamsDto paramsDto) {
        GenerateResult generateResult = generate(paramsDto, 4, "checkCode:", Duration.ofHours(2));
        String pic = createPic(generateResult.code());
        return new CheckCodeResultDto(generateResult.key(), pic);
    }
    private String createPic(String code) {
        BufferedImage image = kaptcha.createImage(code);
        String base64Image = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", outputStream);
            base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        }catch (IOException e) {
            logger.error("生成验证码图片错误，错误消息：{}", e.getMessage());
        }
        return base64Image;
    }
}