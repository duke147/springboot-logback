package com.example.springbootlogback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingConverter extends ClassicConverter {
    /*********** 匹配JSON格式字段："name":"张三" ************/
    // 银行卡脱敏
    private static final Pattern BANKNO_PATTERN = Pattern.compile("\"bankNo\":\"(\\d{4})\\d+(\\d{4})\"");
    // 姓名脱敏
    private static final Pattern NAME_PATTERN = Pattern.compile("\"(legalName|name|personName)\":\"([\\u4e00-\\u9fa5])([\\u4e00-\\u9fa5]*)\"");
    // 手机号脱敏
    private static final Pattern PHONE_PATTERN = Pattern.compile("\"(legalPhone|mobilePhone|adminPhone)\":\"(\\d{3})\\d+(\\d{4})\"");
    // 身份证脱敏
    private static final Pattern IDNUM_PATTERN = Pattern.compile("\"(idNum|adminIdentNo)\":\"(\\d{3})\\d{8}(\\d*)\"");

    /*********** 匹配等号格式字段：name=张三 ************/
    // 手机号脱敏
    private static final Pattern EQ_PHONE_PATTERN = Pattern.compile("(adminPhone|mobilePhone)=(\\d{3})\\d+(\\d{4})");
    // 姓名脱敏
    private static final Pattern EQ_NAME_PATTERN = Pattern.compile("(adminName|personName)=([\\u4e00-\\u9fa5])([\\u4e00-\\u9fa5]*)");
    // 身份证脱敏
    private static final Pattern EQ_IDNUM_PATTERN = Pattern.compile("(adminIdentNo)=(\\d{3})\\d{8}(\\d*)");

    @Override
    public String convert(ILoggingEvent event) {
        String message = event.getFormattedMessage();

        Matcher matcher = BANKNO_PATTERN.matcher(message);
        if (matcher.find()) {
            String maskedBankNo = "\"bankNo\":\"" + matcher.group(1) + "****" + matcher.group(2) + "\"";
            message = matcher.replaceFirst(maskedBankNo);
        }

        matcher = NAME_PATTERN.matcher(message);
        while (matcher.find()) {
            String maskedName = "\"" + matcher.group(1) + "\":\"*" + matcher.group(3) + "\"";
            message = matcher.replaceFirst(maskedName);
            matcher = NAME_PATTERN.matcher(message);
        }

        matcher = PHONE_PATTERN.matcher(message);
        while (matcher.find()) {
            String maskedPhone = "\"" + matcher.group(1) + "\":\"" + matcher.group(2) + "****" + matcher.group(3) + "\"";
            message = matcher.replaceFirst(maskedPhone);
            matcher = PHONE_PATTERN.matcher(message);
        }

        matcher = IDNUM_PATTERN.matcher(message);
        if (matcher.find()) {
            String maskedIdNum = "\"" + matcher.group(1) + "\":\"" + matcher.group(2) + "********" + matcher.group(3) + "\"";
            message = matcher.replaceFirst(maskedIdNum);
        }

        matcher = EQ_PHONE_PATTERN.matcher(message);
        while (matcher.find()) {
            String maskedPhone = matcher.group(1) + "=" + matcher.group(2) + "****" + matcher.group(3);
            message = matcher.replaceFirst(maskedPhone);
            matcher = EQ_PHONE_PATTERN.matcher(message);
        }

        matcher = EQ_NAME_PATTERN.matcher(message);
        while (matcher.find()) {
            String maskedName = matcher.group(1) + "=*" + matcher.group(3);
            message = matcher.replaceFirst(maskedName);
            matcher = EQ_NAME_PATTERN.matcher(message);
        }

        matcher = EQ_IDNUM_PATTERN.matcher(message);
        if (matcher.find()) {
            String maskedIdNum = matcher.group(1) + "=" + matcher.group(2) + "********" + matcher.group(3);
            message = matcher.replaceFirst(maskedIdNum);
        }

        return message;
    }
}

