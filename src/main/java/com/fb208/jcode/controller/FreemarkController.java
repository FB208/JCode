package com.fb208.jcode.controller;

import com.fb208.jcode.vm.test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;


@Controller
@RequestMapping(value = "/freemark")
public class FreemarkController {
    @RequestMapping("index")
    public String index(ModelMap map) {
        test t = new test();
        t.setAge(10);
        t.setAge(12);
        t.setName("张三");
        t.setChilds(new ArrayList<String>() {
            {
                add("李四");
                add("王五");
            }
        });
        map.addAttribute("resource","123");
        map.addAttribute("test",t);
        return "index";
    }
}
