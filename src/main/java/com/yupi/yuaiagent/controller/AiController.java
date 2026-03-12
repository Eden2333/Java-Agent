package com.yupi.yuaiagent.controller;

import com.yupi.yuaiagent.aiservice.LoveApp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final LoveApp loveApp;

    /**
     * SSE 流式调用 manus智能体
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/manus/chat", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<String> doChatWithManusSSE(String message, String chatId) {
        return loveApp.doChatWithManusByStream(message, chatId);
    }

    /**
     * 同步调用 AI 恋爱大师应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/love_app/chat/sync")
    public String doChatWithLoveAppSync(String message, String chatId) {
        return loveApp.doChat(message, chatId);
    }

    /**
     * SSE 流式调用 AI 恋爱大师应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<String> doChatWithLoveAppSSE(String message, String chatId) {
        return loveApp.doChatByStream(message, chatId);
    }

    /**
     * SSE 流式调用 AI 恋爱大师应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse_emitter")
    public SseEmitter doChatWithLoveAppServerSseEmitter(String message, String chatId) {
        SseEmitter sseEmitter = new SseEmitter(180000L);
        loveApp.doChatByStream(message, chatId)
                .subscribe(chunk -> {
                    try {
                        sseEmitter.send(chunk);
                    } catch (IOException e) {
                        sseEmitter.completeWithError(e);
                    }
                }, sseEmitter::completeWithError, sseEmitter::complete);
        return sseEmitter;
    }

    /**
     * 调用 MCP 工具
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/love_app/chat/mcp")
    public String doChatWithMcp(String message, String chatId) {
        return loveApp.doChat(message, chatId);
    }
}
