package elte.zhang.community.community.services;

import elte.zhang.community.community.dto.TaskDTO;
import elte.zhang.community.community.mapper.QuestionMapper;
import elte.zhang.community.community.mapper.TaskMapper;
import elte.zhang.community.community.model.Question;
import elte.zhang.community.community.model.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServices {
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    QuestionMapper questionMapper;

    public TaskDTO getById(Integer id) {
        Task task = taskMapper.getById(id);
        TaskDTO taskDTO = new TaskDTO();
        BeanUtils.copyProperties(task,taskDTO);
        List<Question> question = questionMapper.findById(task.getId());
        taskDTO.setQuestion(question);
        return taskDTO;
    }
}
