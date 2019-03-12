package com.springmvc.lxy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.lxy.dto.TestSpringMvc4Get;
import com.springmvc.lxy.dto.TestSpringMvc4Post;
import com.springmvc.lxy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 描述: 控制器
 * <p>
 *
 * @author: harry
 * @date: 2018-08-30
 **/
@RestController
@RequestMapping("/test1")
public class Controller1 {

    private static final Logger LOG = LoggerFactory.getLogger(Controller1.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(HttpServletRequest request, TestSpringMvc4Get req) {
        LOG.info("getUser starts.");
        String user1 = userService.getUser1();
        LOG.info("getUser ends.");
        return new ResponseEntity<>(user1,HttpStatus.OK);
    }

    /**
     * 测试 get方法，list类型
     */
    @RequestMapping(value = "/get4list", method = RequestMethod.GET)
    public ResponseEntity<?> TestSpringMvcGETList(HttpServletRequest request, TestSpringMvc4Get req) {
        LOG.info("测试 get方法 cityIdList starts.");

        String cityIdList = request.getParameter("cityIdList");

        LOG.info("测试 get方法 cityIdList ends.{}",cityIdList);
        return new ResponseEntity<>(req,HttpStatus.OK);
    }

    /**
     * 测试 get方法 序列化时间类型
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<?> TestSpringMvcGET(HttpServletRequest request, TestSpringMvc4Get req) {
        LOG.info("测试 get方法 序列化时间类型 starts.");

        req.setExtendDateFormatLocalDate(LocalDateTime.now());
        req.setExtendJsonLocalDate(LocalDateTime.now());
        req.setExtendLocalDate(LocalDateTime.now());
        LOG.info("测试 get方法 序列化时间类型 ends.");
        return new ResponseEntity<>(req,HttpStatus.OK);
    }

    /**
     * 测试 post方法 序列化时间类型
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<?> TestSpringMvcPOST(HttpServletRequest request, @RequestBody TestSpringMvc4Post req) {
        LOG.info("测试 post方法 序列化时间类型 starts.");

        req.setExtendDateFormatLocalDate(LocalDateTime.now());
        req.setExtendJsonLocalDate(LocalDateTime.now());
        req.setExtendLocalDate(LocalDateTime.now());
        req.setExtendDate(new Date());
        req.setExtendDateWithDateFormat(new Date());
        req.setExtendDateWithJsonFormat(new Date());

        LOG.info("测试 post方法 序列化时间类型 ends.");
        return new ResponseEntity<>(req,HttpStatus.OK);
    }

    public static void main(String[] args) throws JsonProcessingException {
        LocalDate date = LocalDate.now();

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(date));
    }
}
