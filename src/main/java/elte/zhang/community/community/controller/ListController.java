package elte.zhang.community.community.controller;

import elte.zhang.community.community.dto.TaskDTO;
import elte.zhang.community.community.mapper.TaskMapper;
import elte.zhang.community.community.model.Task;
import elte.zhang.community.community.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ListController {

    @Autowired
    TaskMapper taskMapper;
    @Autowired
    TaskServices taskServices;
    @GetMapping("/")
    public String list(Model model){
        List<Task> taskList = taskMapper.findAll();
        model.addAttribute("taskList",taskList);
        return "list";
    }

    @GetMapping("/task/{id}")
    public String taskList(@PathVariable(name = "id") Integer id,
                           Model model){
        TaskDTO taskDTO = taskServices.getById(id);
        model.addAttribute("task",taskDTO);
        return "index";

    }


}
