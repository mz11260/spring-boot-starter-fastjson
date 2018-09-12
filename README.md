# spring-boot-starter-fastjson

### 配置示例
```yaml
spring:
  fastjson:
    enabled: false
    config:
      charset: utf-8
      serializeConfig:
      parserConfig:
      serializerFeatures: #列表
        - disablecircularreferencedetect
        - prettyformat
        - writeclassname
        - WriteMapNullValue
        - WriteNullNumberAsZero
        - WriteNullListAsEmpty
        - WriteNullStringAsEmpty
        - WriteNullBooleanAsFalse
      serializeFilters: #列表
      features:
      classSerializeFilters: #列表
      dateFormat: yyyy-MM-dd HH:mm:ss
      writeContentLength: true
```