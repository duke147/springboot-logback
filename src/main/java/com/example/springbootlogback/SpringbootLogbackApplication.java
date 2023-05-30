package com.example.springbootlogback;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "sys-user")
@SpringBootApplication
public class SpringbootLogbackApplication {

    public static void main(String[] args) {
        log.debug("启动之前 - Springboot默认Debug级别");
        SpringApplication.run(SpringbootLogbackApplication.class, args);
        log.debug("启动之后 - 现在是Info级别，所以不输出");
        log.info("启动之后 - Logback配置生效，所以输出Info日志");

        log.info("脱敏测试：");
        MaskingVO maskingVO = new MaskingVO(1, "张三", "李四");
        List<MaskingVO> list = new ArrayList<>();
        list.add(maskingVO);
        log.info(JSON.toJSONString(list));
    }

}
