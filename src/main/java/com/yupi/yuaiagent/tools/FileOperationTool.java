package com.yupi.yuaiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.yupi.yuaiagent.annotation.AgentTool;
import com.yupi.yuaiagent.constant.FileConstant;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 文件操作工具
 */
@AgentTool
@Slf4j
@Component
public class FileOperationTool {

    private final String FILE_DIR = FileConstant.FILE_SAVE_DIR + "/file";

    @Tool("Read content from a file")
    public String readFile(@P("Name of the file to read") String fileName) {
        log.info("[Tool] readFile called with fileName: {}", fileName);
        String filePath = FILE_DIR + "/" + fileName;
        try {
            return FileUtil.readUtf8String(filePath);
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    @Tool("Write content to a file")
    public String writeFile(@P("Name of the file to write") String fileName,
                            @P("Content to write to the file") String content) {
        log.info("[Tool] writeFile called with fileName: {}", fileName);
        String filePath = FILE_DIR + "/" + fileName;
        try {
            FileUtil.mkdir(FILE_DIR);
            FileUtil.writeUtf8String(content, filePath);
            return "File written successfully to: " + filePath;
        } catch (Exception e) {
            return "Error writing to file: " + e.getMessage();
        }
    }
}
