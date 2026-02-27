//package com.yupi.yuaiagent.app;
//
//import dev.langchain4j.data.message.AiMessage;
//import dev.langchain4j.data.message.SystemMessage;
//import dev.langchain4j.data.message.UserMessage;
//import dev.langchain4j.memory.ChatMemory;
//import dev.langchain4j.memory.chat.MessageWindowChatMemory;
//import dev.langchain4j.model.chat.ChatLanguageModel;
//import dev.langchain4j.model.chat.response.ChatResponse;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Flux;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@Slf4j
//public class LoveApp {
//
//    @Resource
//    private ChatLanguageModel ollamaChatLanguageModel;
//
//    private static final String SYSTEM_PROMPT = "扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。" +
//            "围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；" +
//            "恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。" +
//            "引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。";
//
//    // 存储每个会话的 ChatMemory
//    private final Map<String, ChatMemory> chatMemoryMap = new HashMap<>();
//
//    /**
//     * 获取或创建 ChatMemory
//     */
//    private ChatMemory getChatMemory(String chatId) {
//        return chatMemoryMap.computeIfAbsent(chatId, id ->
//                MessageWindowChatMemory.withMaxMessages(20)
//        );
//    }
//
//    /**
//     * AI 基础对话（支持多轮对话记忆）
//     *
//     * @param message
//     * @param chatId
//     * @return
//     */
//    public String doChat(String message, String chatId) {
//        ChatMemory chatMemory = getChatMemory(chatId);
//
//        // 添加系统消息（仅首次）
//        if (chatMemory.messages().isEmpty()) {
//            chatMemory.add(SystemMessage.from(SYSTEM_PROMPT));
//        }
//
//        // 添加用户消息
//        chatMemory.add(UserMessage.from(message));
//
//        // 调用 AI
//        ChatResponse response = ollamaChatLanguageModel.chat(chatMemory.messages());
//        String content = response.toString();
//
//        // 保存 AI 回复
//        chatMemory.add(AiMessage.aiMessage(content));
//
//        log.info("content: {}", content);
//        return content;
//    }
//
//    /**
//     * AI 基础对话（支持多轮对话记忆，SSE 流式传输）
//     *
//     * @param message
//     * @param chatId
//     * @return
//     */
//    public Flux<String> doChatByStream(String message, String chatId) {
//        // LangChain4j 流式调用需要使用 StreamingChatLanguageModel
//        // 这里先返回同步结果的 Flux 包装
//        return Flux.just(doChat(message, chatId));
//    }
//
//    record LoveReport(String title, List<String> suggestions) {
//    }
//
//    /**
//     * AI 恋爱报告功能（实战结构化输出）
//     *
//     * @param message
//     * @param chatId
//     * @return
//     */
//    public LoveReport doChatWithReport(String message, String chatId) {
//        String prompt = SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表。" +
//                "请以 JSON 格式返回，格式为：{\"title\": \"标题\", \"suggestions\": [\"建议1\", \"建议2\"]}";
//
//        ChatMemory chatMemory = getChatMemory(chatId);
//        chatMemory.add(SystemMessage.from(prompt));
//        chatMemory.add(UserMessage.from(message));
//
//        ChatResponse response = ollamaChatLanguageModel.chat(chatMemory.messages());
//        String content = response.toString();
//
//        // 简单解析（实际应使用 LangChain4j 的结构化输出）
//        log.info("loveReport: {}", content);
//        return new LoveReport("恋爱报告", List.of("建议1", "建议2"));
//    }
//
//    /**
//     * 和 RAG 知识库进行对话
//     *
//     * @param message
//     * @param chatId
//     * @return
//     */
//    public String doChatWithRag(String message, String chatId) {
//        // RAG 功能需要配置 EmbeddingStore 和 ContentRetriever
//        // 暂时返回基础对话
//        return doChat(message, chatId);
//    }
//
//    /**
//     * AI 恋爱报告功能（调用 MCP 服务）
//     *
//     * @param message
//     * @param chatId
//     * @return
//     */
//    public String doChatWithMcp(String message, String chatId) {
//        // MCP 功能需要单独实现
//        return doChat(message, chatId);
//    }
//}
