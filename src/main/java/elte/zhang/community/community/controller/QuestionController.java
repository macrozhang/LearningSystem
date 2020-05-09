package elte.zhang.community.community.controller;

import elte.zhang.community.community.dto.QuestionDTO;
import elte.zhang.community.community.services.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionServices questionServices;
    @GetMapping("/question/{id}")
    public String question( @PathVariable(name = "id") Integer id,
                            Model model){
        QuestionDTO questionDTO = questionServices.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name="id") Integer id,
                         Model model){
        QuestionDTO questionDTO = questionServices.getById(id);
         model.addAttribute("question",questionDTO);
        questionServices.deleteById(id);

        return "redirect:/";
        //将获取到的元素填充到页面上
    }

}
