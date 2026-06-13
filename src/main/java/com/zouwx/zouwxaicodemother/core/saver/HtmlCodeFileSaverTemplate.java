package com.zouwx.zouwxaicodemother.core.saver;

import cn.hutool.core.util.StrUtil;
import com.zouwx.zouwxaicodemother.ai.model.HtmlCodeResult;
import com.zouwx.zouwxaicodemother.exception.BusinessException;
import com.zouwx.zouwxaicodemother.exception.ErrorCode;
import com.zouwx.zouwxaicodemother.model.enums.CodeGenTypeEnum;

/**
 * HTML代码文件保存器模版
 *
 * @author Tretar
 */
public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult> {
    /**
     * 获取代码类型（由子类实现）
     *
     * @return 代码生成类型
     */
    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }

    /**
     * 保存文件的具体实现（由子类实现）
     *
     * @param result      代码结果对象
     * @param baseDirPath 基础目录路径
     */
    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        // 保存 Html 文件
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
    }

    /**
     * 验证输入参数（可由子类覆盖）
     *
     * @param result 代码结果对象
     */
    @Override
    protected void validateInput(HtmlCodeResult result) {
        super.validateInput(result);
        // Html 代码不能为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Html代码不能为空");
        }
    }
}
