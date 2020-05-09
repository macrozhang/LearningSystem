package elte.zhang.community.community.mapper;

import elte.zhang.community.community.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {
    @Select("select * from task order by gmt_modified desc")
    List<Task> findAll();

    @Select("select * from task where id = #{id}")
    Task getById(@Param(value="id") Integer id);
}
