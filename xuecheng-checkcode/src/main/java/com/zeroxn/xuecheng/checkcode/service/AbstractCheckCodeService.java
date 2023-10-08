package com.zeroxn.xuecheng.checkcode.service;

import com.zeroxn.xuecheng.checkcode.dto.CheckCodeParamsDto;
import com.zeroxn.xuecheng.checkcode.dto.CheckCodeResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * @Author: lisang
 * @DateTime: 2023-10-08 18:57:16
 * @Description:
 */
public abstract class AbstractCheckCodeService implements CheckCodeService{
    private static final Logger logger = LoggerFactory.getLogger(AbstractCheckCodeService.class);
    protected CheckCodeGenerate checkCodeGenerate;
    protected KeyGenerate keyGenerate;
    protected CheckCodeStore checkCodeStore;

    public abstract void setCheckCodeGenerate(CheckCodeGenerate checkCodeGenerate);

    public abstract void setKeyGenerate(KeyGenerate keyGenerate);

    public abstract void setCheckCodeStore(CheckCodeStore checkCodeStore);
    public GenerateResult generate(CheckCodeParamsDto paramsDto, int length, String prefix, Duration expire) {
        String code = checkCodeGenerate.generate(length);
        logger.trace("生成验证码：{}", code);
        String key = keyGenerate.generate(prefix);
        checkCodeStore.set(key, code, expire);
        return new GenerateResult(key, code);
    }

    @Override
    public abstract CheckCodeResultDto generate(CheckCodeParamsDto paramsDto);

    @Override
    public boolean validation(String key, String code) {
        if ((key == null || key.isEmpty()) || (code == null || code.isEmpty())) {
            return false;
        }
        String findCode = checkCodeStore.get(key);
        if (findCode == null || findCode.isEmpty()) {
            return false;
        }
        boolean result = findCode.equalsIgnoreCase(code);
        if (result) {
            checkCodeStore.remove(key);
        }
        return result;
    }

    public record GenerateResult(String key, String code) {}
}
