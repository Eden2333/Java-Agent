package com.yupi.yuaiagent.config;

import com.yupi.yuaiagent.aiservice.LoveApp;
import com.yupi.yuaiagent.annotation.AgentTool;
import com.yupi.yuaiagent.store.RedisChatMemoryStore;
import com.yupi.yuaiagent.tools.*;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class LangChain4jConfig {

    private final RedisChatMemoryStore redisChatMemoryStore;
    private final ApplicationContext applicationContext;

    @Value("${langchain4j.dashscope.api-key:placeholder}")
    private String dashscopeApiKey;

    @Value("${langchain4j.ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    @Value("${langchain4j.ollama.chat-model:qwen2.5:3b}")
    private String ollamaChatModelName;

    @Value("${langchain4j.ollama.embedding-model:bge-large}")
    private String ollamaEmbeddingModelName;

    @Bean
    public ChatModel ollamaChatLanguageModel() {
        return OllamaChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(ollamaChatModelName)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    public StreamingChatModel streamingChatModel() {
        return OllamaStreamingChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(ollamaChatModelName)
                .timeout(Duration.ofMinutes(5))
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    public EmbeddingModel ollamaEmbeddingModel() {
        return OllamaEmbeddingModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(ollamaEmbeddingModelName)
                .build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .chatMemoryStore(redisChatMemoryStore)
                .build();
    }

    @Bean
    public LoveApp loveApp(ContentRetriever contentRetriever) {
        // 自动扫描所有带 @AgentTool 注解的 Bean
        List<Object> tools = applicationContext.getBeansWithAnnotation(AgentTool.class)
                .values()
                .stream()
                .toList();
        
        return AiServices.builder(LoveApp.class)
                .chatModel(ollamaChatLanguageModel())
                .streamingChatModel(streamingChatModel())
                .chatMemoryProvider(chatMemoryProvider())
                // 关闭RAG便于调试
//                .contentRetriever(contentRetriever)
                .tools(tools.toArray())
                .build();
    }
}
