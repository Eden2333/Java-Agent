package com.yupi.yuaiagent;


import com.alibaba.cloud.ai.autoconfigure.dashscope.*;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {
        DashScopeEmbeddingAutoConfiguration.class,
        DashScopeChatAutoConfiguration.class,
        DashScopeAgentAutoConfiguration.class,
        DashScopeAudioSpeechAutoConfiguration.class,
        DashScopeAudioTranscriptionAutoConfiguration.class,
        DashScopeImageAutoConfiguration.class,
        DashScopeRerankAutoConfiguration.class
})
public class YuAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuAiAgentApplication.class, args);
    }

    @Bean
    public ToolCallback[] allTools(ToolCallbackProvider toolCallbackProvider) {
        return toolCallbackProvider.getToolCallbacks();
    }
}
