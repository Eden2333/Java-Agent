//package com.yupi.yuaiagent.tools;
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.json.JSONUtil;
//import dev.langchain4j.agent.tool.P;
//import dev.langchain4j.agent.tool.Tool;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * MCP 工具适配器 - 通过 HTTP 调用 MCP Server
// */
//@Slf4j
//@Component
//public class McpTools {
//
//    @Value("${mcp.server.url:http://localhost:8127}")
//    private String mcpServerUrl;
//
//    @Tool("Search images from web based on query keyword")
//    public String searchImage(@P("Search query keyword") String query) {
//        log.info("[MCP Tool] searchImage called with query: {}", query);
//        Map<String, String> params = new HashMap<>();
//        params.put("query", query);
//        return callMcpTool("/mcp/tools/searchImage", params);
//    }
//
//    @Tool("Read content from a file")
//    public String readFile(@P("Name of the file to read") String fileName) {
//        log.info("[MCP Tool] readFile called with fileName: {}", fileName);
//        Map<String, String> params = new HashMap<>();
//        params.put("fileName", fileName);
//        return callMcpTool("/mcp/tools/readFile", params);
//    }
//
//    @Tool("Write content to a file")
//    public String writeFile(@P("Name of the file to write") String fileName,
//                           @P("Content to write to the file") String content) {
//        log.info("[MCP Tool] writeFile called with fileName: {}", fileName);
//        Map<String, String> params = new HashMap<>();
//        params.put("fileName", fileName);
//        params.put("content", content);
//        return callMcpTool("/mcp/tools/writeFile", params);
//    }
//
//    @Tool("Scrape and extract content from a web page")
//    public String scrapeWebPage(@P("URL of the web page to scrape") String url) {
//        log.info("[MCP Tool] scrapeWebPage called with url: {}", url);
//        Map<String, String> params = new HashMap<>();
//        params.put("url", url);
//        return callMcpTool("/mcp/tools/scrapeWebPage", params);
//    }
//
//    @Tool("Download resource from URL and save to file")
//    public String downloadResource(@P("URL of the resource to download") String url,
//                                   @P("Name of the file to save") String fileName) {
//        log.info("[MCP Tool] downloadResource called with url: {}, fileName: {}", url, fileName);
//        Map<String, String> params = new HashMap<>();
//        params.put("url", url);
//        params.put("fileName", fileName);
//        return callMcpTool("/mcp/tools/downloadResource", params);
//    }
//
//    @Tool("Generate PDF file with given content")
//    public String generatePDF(@P("Name of the PDF file") String fileName,
//                             @P("Content to write in PDF") String content) {
//        log.info("[MCP Tool] generatePDF called with fileName: {}", fileName);
//        Map<String, String> params = new HashMap<>();
//        params.put("fileName", fileName);
//        params.put("content", content);
//        return callMcpTool("/mcp/tools/generatePDF", params);
//    }
//
//    private String callMcpTool(String endpoint, Map<String, String> params) {
//        try {
//            String url = mcpServerUrl + endpoint;
//            log.info("[MCP Tool] Calling MCP server: {}", url);
//            String response = HttpRequest.post(url)
//                    .header("Content-Type", "application/json")
//                    .body(JSONUtil.toJsonStr(params))
//                    .timeout(60000)
//                    .execute()
//                    .body();
//            log.info("[MCP Tool] Response: {}", response);
//            return response;
//        } catch (Exception e) {
//            log.error("[MCP Tool] Error calling MCP tool: {}", e.getMessage());
//            return "Error calling MCP tool: " + e.getMessage();
//        }
//    }
//}
