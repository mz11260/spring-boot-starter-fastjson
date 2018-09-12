# spring-boot-starter-fastjson

### 配置示例
```yaml
spring:
  fastjson:
    enabled: false #false不启用,true启用,默认false
    config:
      charset: utf-8 #有默认值,可不配
      serializeConfig: #有默认值,可不配
      parserConfig: #有默认值,可不配
      serializerFeatures: #列表
        - disablecircularreferencedetect
        - prettyformat
        - writeclassname
        - WriteMapNullValue
        - WriteNullNumberAsZero
        - WriteNullListAsEmpty
        - WriteNullStringAsEmpty
        - WriteNullBooleanAsFalse
      serializeFilters: #列表 有默认值,可不配
      features: #有默认值,可不配
      classSerializeFilters: #列表 有默认值,可不配
      dateFormat: yyyy-MM-dd HH:mm:ss
      writeContentLength: true
```