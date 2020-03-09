package com.example.json.bean.service;

import com.example.json.ioc.annotation.Service;

@Service
public class DoodleServiceImpl implements DoodleService {
	
    @Override
    public String helloWord() {
        return "hello word";
    }
    
}