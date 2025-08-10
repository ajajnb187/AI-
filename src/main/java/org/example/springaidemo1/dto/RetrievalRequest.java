package org.example.springaidemo1.dto;

import lombok.Data;

@Data
public class RetrievalRequest {
    // 检索的问题（必填）
    private String query;

    // 可选：覆盖默认的topK（不填则使用配置文件中的值）
    private Integer topK;
    
    // 可选：覆盖默认的相似度阈值（不填则使用配置文件中的值）
    private Double similarityThreshold;
}
