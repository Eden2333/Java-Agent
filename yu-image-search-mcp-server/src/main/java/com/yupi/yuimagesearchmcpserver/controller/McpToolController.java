package com.yupi.yuimagesearchmcpserver.controller;

import com.yupi.yuimagesearchmcpserver.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/mcp")
public class McpToolController {

    private final ImageSearchTool imageSearchTool = new ImageSearchTool();
    private final FileOperationTool fileOperationTool = new FileOperationTool();
    private final WebScrapingTool webScrapingTool = new WebScrapingTool();
    private final ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
    private final PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();

    @PostMapping("/tools/searchImage")
    public String searchImage(@RequestBody Map<String, String> params) {
        log.info("[MCP Server] searchImage called with params: {}", params);
        return imageSearchTool.searchImage(params.get("query"));
    }

    @PostMapping("/tools/readFile")
    public String readFile(@RequestBody Map<String, String> params) {
        log.info("[MCP Server] readFile called with params: {}", params);
        return fileOperationTool.readFile(params.get("fileName"));
    }

    @PostMapping("/tools/writeFile")
    public String writeFile(@RequestBody Map<String, String> params) {
        log.info("[MCP Server] writeFile called with params: {}", params);
        return fileOperationTool.writeFile(params.get("fileName"), params.get("content"));
    }

    @PostMapping("/tools/scrapeWebPage")
    public String scrapeWebPage(@RequestBody Map<String, String> params) {
        log.info("[MCP Server] scrapeWebPage called with params: {}", params);
        return webScrapingTool.scrapeWebPage(params.get("url"));
    }

    @PostMapping("/tools/downloadResource")
    public String downloadResource(@RequestBody Map<String, String> params) {
        log.info("[MCP Server] downloadResource called with params: {}", params);
        return resourceDownloadTool.downloadResource(params.get("url"), params.get("fileName"));
    }

    @PostMapping("/tools/generatePDF")
    public String generatePDF(@RequestBody Map<String, String> params) {
        log.info("[MCP Server] generatePDF called with params: {}", params);
        return pdfGenerationTool.generatePDF(params.get("fileName"), params.get("content"));
    }

    @GetMapping("/tools/list")
    public String[] listTools() {
        return new String[]{"searchImage", "readFile", "writeFile", "scrapeWebPage", "downloadResource", "generatePDF"};
    }

    @GetMapping("/health")
    public String health() {
        return "MCP Server is running";
    }
}
