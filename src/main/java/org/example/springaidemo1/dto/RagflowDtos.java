package org.example.springaidemo1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * RagFlow API 相关的数据传输对象
 */
public class RagflowDtos {

    /**
     * RagFlow API 请求对象
     */
    @Data
    public static class RagflowChatRequest {
        /**
         * 用户问题
         */
        private String question;
        
        /**
         * 是否使用流式响应
         */
        private Boolean stream = true;
        
        /**
         * 会话ID（可选）
         */
        @JsonProperty("session_id")
        private String sessionId;
        
        /**
         * 用户ID（可选）
         */
        @JsonProperty("user_id")
        private String userId;
    }

    /**
     * RagFlow API 响应对象
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RagflowApiResponse {
        /**
         * 状态码：0表示成功
         */
        private Integer code;
        
        /**
         * 错误消息（如果有）
         */
        private String message;
        
        /**
         * 响应数据
         */
        private ResponseData data;
        
        /**
         * 响应数据内部类
         */
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ResponseData {
            /**
             * AI回答内容
             */
            private String answer;
            
            /**
             * 引用信息
             */
            private Map<String, Object> reference;
            
            /**
             * 音频二进制数据（如果有）
             */
            @JsonProperty("audio_binary")
            private String audioBinary;
            
            /**
             * 消息ID
             */
            private String id;
            
            /**
             * 会话ID
             */
            @JsonProperty("session_id")
            private String sessionId;
        }
    }
    
    /**
     * 流式响应的最后一条消息标记
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RagflowStreamEndMarker {
        private Integer code;
        private Boolean data;
    }
}