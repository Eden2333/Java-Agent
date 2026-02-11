package com.yupi.yuimagesearchmcpserver;

import com.yupi.yuimagesearchmcpserver.tools.*;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YuImageSearchMcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(YuImageSearchMcpServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider imageSearchTools() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        // WebSearchTool webSearchTool = new WebSearchTool(searchApiKey);
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        TerminateTool terminateTool = new TerminateTool();
        ImageSearchTool imageSearchTool = new ImageSearchTool();
        return MethodToolCallbackProvider.builder()
                .toolObjects(fileOperationTool, webScrapingTool, resourceDownloadTool, terminalOperationTool, pdfGenerationTool, terminateTool, imageSearchTool)
                .build();
    }

}
