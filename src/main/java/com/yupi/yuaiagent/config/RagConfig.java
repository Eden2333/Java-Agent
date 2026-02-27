package com.yupi.yuaiagent.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RagConfig {

    private final EmbeddingModel ollamaEmbeddingModel;
    private final ResourcePatternResolver resourcePatternResolver;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return PgVectorEmbeddingStore.builder()
                .host("localhost")
                .port(5432)
                .database("postgres")
                .user(datasourceUsername)
                .password(datasourcePassword)
                .table("love_embeddings")
                .dimension(1024)
                .createTable(true)
                .build();
    }

    @Bean
    public ContentRetriever contentRetriever(EmbeddingStore<TextSegment> embeddingStore) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(ollamaEmbeddingModel)
                .maxResults(3)
                .minScore(0.6)
                .build();
    }

//    @Bean  // 只在第一次导入数据时开启
    public EmbeddingStoreIngestor embeddingStoreIngestor(EmbeddingStore<TextSegment> embeddingStore) throws IOException {
        // 加载文档
        List<Document> documents = loadDocuments();
        
        // 文档切割
        DocumentSplitter splitter = DocumentSplitters.recursive(300, 50);
        
        // 创建 Ingestor
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(ollamaEmbeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        
        // 导入文档
        ingestor.ingest(documents);
        
        return ingestor;
    }

    private List<Document> loadDocuments() throws IOException {
        List<Document> documents = new ArrayList<>();
        Resource[] resources = resourcePatternResolver.getResources("classpath:document/*.md");
        
        TextDocumentParser parser = new TextDocumentParser();
        for (Resource resource : resources) {
            Document document = parser.parse(resource.getInputStream());
            documents.add(document);
        }
        
        return documents;
    }
}
