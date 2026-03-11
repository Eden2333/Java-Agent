package com.yupi.yuaiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.yupi.yuaiagent.constant.FileConstant;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 资源下载工具
 */
@Slf4j
@Component
public class ResourceDownloadTool {

    private final String FILE_DIR = FileConstant.FILE_SAVE_DIR + "/download";

    @Tool("Download resource from URL and save to file")
    public String downloadResource(@P("URL of the resource to download") String url,
                                    @P("Name of the file to save") String fileName) {
        log.info("[Tool] downloadResource called with url: {}, fileName: {}", url, fileName);
        String filePath = FILE_DIR + "/" + fileName;
        try {
            FileUtil.mkdir(FILE_DIR);
            HttpUtil.downloadFile(url, new File(filePath));
            return "Resource downloaded successfully to: " + filePath;
        } catch (Exception e) {
            return "Error downloading resource: " + e.getMessage();
        }
    }
}
