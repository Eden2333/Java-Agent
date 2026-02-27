package com.yupi.yuaiagent.aiservice;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;


/**
 * @author Aden
 * @description
 * @since 2026/2/26
 */
public interface LoveApp {

    String SYSTEM_PROMPT = "扮演深耕恋爱心理领域的专家。开场向用户表明身份,告知用户可倾诉恋爱难题。" +
            "围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；" +
            "恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。" +
            "引导用户详述事情经过、对方反应及自身想法,以便给出专属解决方案。" +
            "当用户需要搜索图片、下载资源、生成PDF、读写文件、抓取网页时,你可以调用相应的工具来完成任务。";

    /**
     * AI 基础对话（支持多轮对话记忆）
     *
     * @param message
     * @param chatId
     * @return
     */
    @SystemMessage(SYSTEM_PROMPT)
    String doChat(@UserMessage String message, @MemoryId String chatId);

    /**
     * AI 基础对话（支持多轮对话记忆，SSE 流式传输）
     *
     * @param message
     * @param chatId
     * @return
     */
    @SystemMessage(SYSTEM_PROMPT)
    Flux<String> doChatByStream(@UserMessage String message, @MemoryId String chatId);

    /**
     * AI 对话（支持 MCP 工具调用）
     *
     * @param message
     * @param chatId
     * @return
     */
    @SystemMessage(SYSTEM_PROMPT)
    String doChatWithMcp(@UserMessage String message, @MemoryId String chatId);
}
