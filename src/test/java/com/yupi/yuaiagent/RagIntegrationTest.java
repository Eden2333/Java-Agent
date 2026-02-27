//package com.yupi.yuaiagent;
//
//import com.yupi.yuaiagent.app.LoveApp;
//import jakarta.annotation.Resource;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class RagIntegrationTest {
//
//    @Resource
//    private LoveApp loveApp;
//
//    @Test
//    void testRagKnowledgeBase() {
//        String chatId = UUID.randomUUID().toString();
//        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
//
//        String answer = loveApp.doChatWithRag(message, chatId);
//
//        assertNotNull(answer, "AI 应该返回结果");
//        assertTrue(answer.length() > 0, "返回内容不应为空");
//        System.out.println("=== RAG 测试结果 ===");
//        System.out.println(answer);
//
//        // 验证是否使用了知识库
//        assertTrue(
//            answer.contains("婚后") || answer.contains("亲密") || answer.contains("夫妻"),
//            "返回结果应包含知识库相关内容"
//        );
//    }
//}
