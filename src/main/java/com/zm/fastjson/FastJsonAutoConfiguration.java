package com.zm.fastjson;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.feed.AtomFeedHttpMessageConverter;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.ClassUtils;

import javax.xml.transform.Source;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/12.
 */
@Configuration
@Slf4j
public class FastJsonAutoConfiguration {

    @Configuration
    @ConditionalOnClass({FastJsonHttpMessageConverter.class})
    @ConditionalOnProperty(prefix = "spring.fastjson", name = "enabled", havingValue = "true", matchIfMissing = true)
    @EnableConfigurationProperties(FastJsonProperties.class)
    public static class FastJson2HttpMessageConverterConfiguration {

        private FastJsonProperties properties;
        private ApplicationContext applicationContext;
        @Autowired
        private void setProperties(FastJsonProperties properties) {
            this.properties = properties;
        }
        @Autowired
        private void setApplicationContext(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        @Bean
        @ConditionalOnMissingBean({FastJsonHttpMessageConverter.class})
        public <T> HttpMessageConverters httpMessageConverters() {

            List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

            // fastjson message converter
            FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
            fastJsonConverter.setSupportedMediaTypes(properties.getSupportedMediaTypes());

            BeanCopier copier = BeanCopier.create(FastJsonProperties.Config.class, FastJsonConfig.class, false);
            FastJsonConfig config = new FastJsonConfig();
            copier.copy(properties.getConfig(), config, null);

            fastJsonConverter.setFastJsonConfig(config);

            // string message converter
            StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));
            stringConverter.setWriteAcceptCharset(false);

            // let stringConverter be topped
            messageConverters.add(stringConverter);
            messageConverters.add(fastJsonConverter);

            // add default converters
            this.addDefaultHttpMessageConverters(messageConverters);

            // default converters has been added, so set to false
            return new HttpMessageConverters(false, messageConverters);
        }

        private static boolean romePresent =
                ClassUtils.isPresent("com.rometools.rome.feed.WireFeed",
                        FastJsonAutoConfiguration.class.getClassLoader());

        private static final boolean jaxb2Present =
                ClassUtils.isPresent("javax.xml.bind.Binder",
                        FastJsonAutoConfiguration.class.getClassLoader());

        private static final boolean jackson2XmlPresent =
                ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper",
                        FastJsonAutoConfiguration.class.getClassLoader());
        /**
         * 添加一组默认的HttpMessageConverter
         * @param messageConverters 需要添加默认消息转换器的集合
         */
        private void addDefaultHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
            messageConverters.add(new ByteArrayHttpMessageConverter());
            messageConverters.add(new ResourceHttpMessageConverter());
            messageConverters.add(new SourceHttpMessageConverter<Source>());
            messageConverters.add(new AllEncompassingFormHttpMessageConverter());
            if (romePresent) {
                messageConverters.add(new AtomFeedHttpMessageConverter());
                messageConverters.add(new RssChannelHttpMessageConverter());
            }
            if (jackson2XmlPresent) {
                messageConverters.add(new MappingJackson2XmlHttpMessageConverter(
                        Jackson2ObjectMapperBuilder.xml().applicationContext(this.applicationContext).build()));
            } else if (jaxb2Present) {
                messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
            }
        }
    }
}
