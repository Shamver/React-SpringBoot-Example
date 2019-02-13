package kr.co.upcoding.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.upcoding.mapper.TestMapper;
import kr.co.upcoding.vo.TestVO;
 
@Controller
public class MyController {
	
	@Autowired
	TestMapper testMapper;
 
    @GetMapping("/{name}.html")
    public String page(@PathVariable String name, Model model) {
        model.addAttribute("pageName", name);
        return "page";
    }
 
    @RequestMapping(value = "/testinsert", method=RequestMethod.POST)
    public String testInsert(TestVO vo) {
    	testMapper.testInsert(vo);
    	
    	return "sucsex";
    }
    
    @RequestMapping(value = "/testdelete", method=RequestMethod.POST)
    public String testDelete(TestVO vo) {
    	testMapper.testDelete(vo);
    	
    	return "asf";
    }
}