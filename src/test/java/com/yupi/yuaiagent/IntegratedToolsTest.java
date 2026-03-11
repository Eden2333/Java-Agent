package com.yupi.yuaiagent;

import com.yupi.yuaiagent.aiservice.LoveApp;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class IntegratedToolsTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void testSearchImage() {
        String chatId = UUID.randomUUID().toString();
        String message = "帮我搜索一些浪漫的图片";
        String answer = loveApp.doChat(message, chatId);
        System.out.println("=== 测试结果 ===");
        System.out.println(answer);
    }

    @Test
    void testFileOperation() {
        String chatId = UUID.randomUUID().toString();
        String message = "帮我写一个文件，文件名是 test.txt，内容是：这是一个测试文件";
        String answer = loveApp.doChat(message, chatId);
        System.out.println("=== 测试结果 ===");
        System.out.println(answer);
    }

    @Test
    void testGeneratePDF() {
        String chatId = UUID.randomUUID().toString();
        String message = "生成一份约会计划PDF，文件名是 date_plan.pdf，内容包含：1. 看电影 2. 吃晚餐 3. 散步";
        String answer = loveApp.doChat(message, chatId);
        System.out.println("=== 测试结果 ===");
        System.out.println(answer);
    }

    @Test
    void testWebSearch() {
        String chatId = UUID.randomUUID().toString();
        String message = "帮我搜索一下上海适合情侣约会的地方";
        String answer = loveApp.doChat(message, chatId);
        System.out.println("=== 测试结果 ===");
        System.out.println(answer);
    }

    @Test
    void testScrapeWebPage() {
        String chatId = UUID.randomUUID().toString();
        String message = "帮我抓取 codefather.cn 网站的内容";
        String answer = loveApp.doChat(message, chatId);
        System.out.println("=== 测试结果 ===");
        System.out.println(answer);
    }

    @Test
    void testDownloadResource() {
        String chatId = UUID.randomUUID().toString();
        String message = "下载一张图片到文件 romantic.jpg";
        String answer = loveApp.doChat(message, chatId);
        System.out.println("=== 测试结果 ===");
        System.out.println(answer);
    }
}
