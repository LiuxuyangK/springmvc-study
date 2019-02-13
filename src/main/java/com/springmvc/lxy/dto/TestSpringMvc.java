package com.springmvc.lxy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-08-30
 **/
@Data
public class TestSpringMvc {
    @JsonFormat(pattern = "yyyy-MM-dd HH", timezone = "GMT+8")
    private Date testJson;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date testDateFormat;

    private LocalDate localDate;

    private Date date;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate testJsonLocalDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate testDateFormatLocalDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH~~mm", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime extendJsonLocalDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:::ss")
    private LocalDateTime extendDateFormatLocalDate;

    private LocalDateTime extendLocalDate;

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
    }
}
