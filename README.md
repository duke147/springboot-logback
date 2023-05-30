[toc]
### 一、概述
- [SpringBoot官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.logging)
- Springboot 默认读取的是项目内的 resources 中 logback 配置文件。
- 如果 classpath（resources） 下有logback-test.xml会优先生效，并且会和其他logback文件同时生效。
- Springboot 默认日志级别是DEBUG，所以在logback初始化之前，会有DEBUG日志输出。
- Springboot 使用JUnit时，只有配置SpringBootTest注解，日志系统才会得到初始化。

#### 项目内外配置
- 项目内：
```bash
logging:
   config: classpath:logback/logback-dev.xml
```
- 项目外（**去掉classpath**）：
```bash
  config: /Users/wangfugui/Downloads/logback/logback-dev.xml
```

#### 使用自定义appender
- 使用Lombok
Lombok 内置 @Slf4j 、@Log4j2 两种日志注解。
```bash
@Log4j2(topic = "sys-user")
@Slf4j(topic = "sys-user")
```
- 不使用Lombok
```bash
Logger logger = LoggerFactory.getLogger("sys-user");
```

#### logback查找配置
org.springframework.boot.logging.logback.LogbackLoggingSystem
```bash
    protected String[] getStandardConfigLocations() {
        return new String[]{"logback-test.groovy", "logback-test.xml", "logback.groovy", "logback.xml"};
    }
```

### 日志脱敏
#### 方式一：
这种方式一次只能配置一个脱敏字段。
因为 `replace` 的规则在找到第一个匹配后就会立即停止，这就导致只有第一个匹配到的字段被替换。
```bash
        <encoder>
            <pattern>
                %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{20} - [%method,%line] - %replace(%msg){
                'bankNo":"(\d{4})\d+(\d{4})', 'bankNo":"$1****$2'}%n
            </pattern>
        </encoder>
```

#### 方式二：
自定义转换类：
```bash
<!-- 自定义日志转换规则 -->
    <conversionRule conversionWord="mask" converterClass="com.example.springbootlogback.MaskingConverter" />
    <!-- 不同的appender，统一日志输出格式 -->
    <property name="log.pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{20} - [%method,%line] - %mask%n" />
```







