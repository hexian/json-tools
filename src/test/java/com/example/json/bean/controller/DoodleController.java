package com.example.json.bean.controller;

import java.util.List;

import com.example.json.bean.service.DoodleService;
import com.example.json.ioc.annotation.Autowired;
import com.example.json.ioc.annotation.Controller;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DoodleController {
	
    @Autowired
    private DoodleService doodleService;
    
    @Autowired
    private List<DoodleService> doodleServices;

    public void hello() {
        log.info(doodleService.helloWord());
    }
    
}
