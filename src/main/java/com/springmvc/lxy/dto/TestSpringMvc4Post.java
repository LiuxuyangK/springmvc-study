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
 * 1.   大概知道了，"1994-11-25"是Date默认的一种。
 * 2.   post方法里面，DateTimeFormat，不起作用的，所以testDateFormat2不行。
 * 3.   post里面一律用JsonFormat，但是序列化 LocalDate,LocalDateTime,LocalTime，有各自不同的限制。比如，LocalDate 不识别 HH，所以一加就会报错。
 * 4.   date默认是返回 时间戳。
 *
 * <p>
 *
 * @author: harry
 * @date: 2018-08-30
 **/
@Data
public class TestSpringMvc4Post {

    //遵守格式可以 "1994-11-25 11"、"1994-11-25 12"，但是  "1994-11-25"不行
    //输出的时候，还是按照这样的方式输出的："1994-11-25 11"
    @JsonFormat(pattern = "yyyy-MM-dd HH", timezone = "GMT+8")
    private Date testJson;


    //"1994-11-25" 可以成功，其他都不行。
    //输出：785721600000
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date testDateFormat;

    //"1994-11-25" 可以成功，其他都不行
    //输出：785462400000
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date testDateFormat2;

    //可以："1994-11-22"
    private LocalDate localDate;

    //可以："1994-11-22"
    private Date date;

    //不行 "1994-11-22 12"，进不来。
    @JsonFormat(pattern = "yyyy-MM-dd HH", timezone = "GMT+8")
    private LocalDate testJsonLocalDate;

    //可以的！"1994~11~22"，进不来。
    @JsonFormat(pattern = "yyyy~MM~dd", timezone = "GMT+8")
    private LocalDate testJsonLocalDate2;

    //"1994-11-22  12"，进不来。大概猜测了一下，是因为 LocalDate，不知道怎么去解析 HH，所以就失败了。
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH")
    private LocalDate testDateFormatLocalDate;

    //可以进来！"1994-11-22~~11:12:11"。
    @JsonFormat(pattern = "yyyy-MM-dd~~HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime testJsonLocalDateTime;


    // 还是这样的：2019-02-26 00~~33
    @JsonFormat(pattern = "yyyy-MM-dd HH~~mm", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime extendJsonLocalDate;

    //这个没用，还是相当于默认的：2019-02-26 00:33:15
    @DateTimeFormat(pattern = "yyyy-MM-dd HH")
    private LocalDateTime extendDateFormatLocalDate;

    //默认的：2019-02-26 00:33:15
    private LocalDateTime extendLocalDate;

    //输出时间戳 ：1551112395575
    private Date extendDate;

    //输出格式："2019-02-26 00~~36"
    @JsonFormat(pattern = "yyyy-MM-dd HH~~mm", timezone = "GMT+8")
    private Date extendDateWithJsonFormat;

    //输出时间戳 ：1551112395575
    @DateTimeFormat(pattern = "yyyy-MM-dd HH")
    private Date extendDateWithDateFormat;
}
