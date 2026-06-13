package com.zouwx.zouwxaicodemother.ai;

import com.zouwx.zouwxaicodemother.ai.model.HtmlCodeResult;
import com.zouwx.zouwxaicodemother.ai.model.MultiFileCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AiCodeGeneratorServiceTest {
    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    public void testGenerateHtmlCode() {
        String userMessage = "做一个记录生活的小工具，不超过20行";
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode(userMessage);
        Assertions.assertNotNull(result);
    }

    @Test
    public void testGenerateMultiFileCode() {
        String userMessage = "随机做一个程序员的留言板，不超过50行";
        MultiFileCodeResult result = aiCodeGeneratorService.generateMultiFileCode(userMessage);
        Assertions.assertNotNull(result);
    }
}
