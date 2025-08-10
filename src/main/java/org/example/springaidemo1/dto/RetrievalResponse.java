package org.example.springaidemo1.dto;

import lombok.Data;
import java.util.List;

@Data
public class RetrievalResponse {
    // 状态码：200成功，其他失败
    private int code;
    
    // 状态描述
    private String message;

    // 检索结果列表
    private List<Chunk> records;

    @Data
    public static class Chunk {
        // 检索到的内容
        private String content;
        
        // 相似度分数
        private Double score;
        
        // 文档标题
        private String title;
        
        // 文档ID（可选）
        private String documentId;
    }
}
