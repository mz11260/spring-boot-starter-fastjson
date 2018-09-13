package com.zm.fastjson;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/12.
 */
@ConfigurationProperties(prefix = "spring.fastjson")
@ConditionalOnClass({FastJsonHttpMessageConverter.class})
public class FastJsonProperties {

    private boolean enabled;

    private List<MediaType> supportedMediaTypes = MediaType.parseMediaTypes(MediaType.APPLICATION_JSON_UTF8_VALUE);

    private Config config = new Config();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<MediaType> getSupportedMediaTypes() {
        return supportedMediaTypes;
    }

    public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
        this.supportedMediaTypes = supportedMediaTypes;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public static class Config {
        /**
         * default charset
         */
        private Charset charset;

        /**
         * serializeConfig
         */
        private SerializeConfig serializeConfig;

        /**
         * parserConfig
         */
        private ParserConfig parserConfig;

        /**
         * serializerFeatures
         */
        private SerializerFeature[] serializerFeatures;

        /**
         * serializeFilters
         */
        private SerializeFilter[] serializeFilters;

        /**
         * features
         */
        private Feature[] features;

        /**
         * class level serializeFilter
         */
        private Map<Class<?>, SerializeFilter> classSerializeFilters;

        /**
         * format date type
         */
        private String dateFormat;

        protected boolean writeContentLength = true;

        public Charset getCharset() {
            if (this.charset == null) {
                this.charset = Charset.forName("UTF-8");
            }
            return charset;
        }

        public void setCharset(Charset charset) {
            this.charset = charset;
        }

        public SerializeConfig getSerializeConfig() {
            if (this.serializeConfig == null) {
                this.serializeConfig = SerializeConfig.getGlobalInstance();
            }
            return serializeConfig;
        }

        public void setSerializeConfig(SerializeConfig serializeConfig) {
            this.serializeConfig = serializeConfig;
        }

        public ParserConfig getParserConfig() {
            if (this.parserConfig == null) {
                this.parserConfig = new ParserConfig();
            }
            return parserConfig;
        }

        public void setParserConfig(ParserConfig parserConfig) {
            this.parserConfig = parserConfig;
        }

        public SerializerFeature[] getSerializerFeatures() {
            if (this.serializerFeatures == null) {
                this.serializerFeatures = new SerializerFeature[] {
                        SerializerFeature.BrowserSecure
                };
            }
            return serializerFeatures;
        }

        public void setSerializerFeatures(SerializerFeature[] serializerFeatures) {
            this.serializerFeatures = serializerFeatures;
        }

        public SerializeFilter[] getSerializeFilters() {
            if (this.serializeFilters == null) {
                this.serializeFilters = new SerializeFilter[0];
            }
            return serializeFilters;
        }

        public void setSerializeFilters(SerializeFilter[] serializeFilters) {
            this.serializeFilters = serializeFilters;
        }

        public Feature[] getFeatures() {
            if (this.features == null) {
                this.features = new Feature[0];
            }
            return features;
        }

        public void setFeatures(Feature[] features) {
            this.features = features;
        }

        public Map<Class<?>, SerializeFilter> getClassSerializeFilters() {
            return classSerializeFilters;
        }

        public void setClassSerializeFilters(Map<Class<?>, SerializeFilter> classSerializeFilters) {
            this.classSerializeFilters = classSerializeFilters;
        }

        public String getDateFormat() {
            return dateFormat;
        }

        public void setDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
        }

        public boolean isWriteContentLength() {
            return writeContentLength;
        }

        public void setWriteContentLength(boolean writeContentLength) {
            this.writeContentLength = writeContentLength;
        }
    }
}
