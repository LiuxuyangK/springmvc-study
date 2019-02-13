package com.springmvc.lxy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-08-30
 **/
@Data
public class TestSpringMvcParams {

    private List<Long> cityIdList;
}
