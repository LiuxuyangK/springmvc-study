package com.springmvc.lxy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 描述:
 * <p>
 * 1.   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ，这个东西就是 get方法，用来前->后。
 * 2.   @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")，就是post方法，前 -> 后。
 * 3.   @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")，还可以指定输出的格式。
 *
 * @author: harry
 * @date: 2018-08-30
 **/
@Data
public class TestSpringMvc4Get {

    //get方法：无论怎样的方式都不行
    @JsonFormat(pattern = "yyyy-MM-dd HH", timezone = "GMT+8")
    private Date testJson;

    //遵守格式可以
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date testDateFormat;

    //遵守格式可以，1999-11，不遵守不行
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date testDateFormat2;

    //不行的
    private LocalDate localDate;

    //无论用什么都不行，时间戳也不行
    private Date date;

    //不行的，get方法用 DateTimeFormat
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate testJsonLocalDate;

    //可以的
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate testDateFormatLocalDate;

    //用了JsonFormat 会有作用："2019-02-26 00~~20"
    @JsonFormat(pattern = "yyyy-MM-dd HH~~mm", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime extendJsonLocalDate;

    //用了datetimeFormat 没用，还是默认的："2019-02-26 00:20:59"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH")
    private LocalDateTime extendDateFormatLocalDate;


    //默认就是"2019-02-26 00:20:59"
    private LocalDateTime extendLocalDate;

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
    }
}
