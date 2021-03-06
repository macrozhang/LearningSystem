package elte.zhang.community.community.mapper;

import elte.zhang.community.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

//
//    @Select("select * from question limit #{offset},#{size}")
    @Select("select * from question order by gmt_modified desc limit #{offset},#{size}" )
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator=#{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);

    @Select("select * from question where creator=#{userId} order by gmt_modified desc limit #{offset},#{size}" )
    List<Question> listByUserId(@Param(value="userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from question where id=#{id}")
    Question getById(@Param(value="id") Integer id);

    @Update("update question set title=#{title}, description=#{description},tag=#{tag},gmt_modified=#{gmtModified} where id=#{id}")
    void update(Question question);

    @Delete("delete from question where id=#{id}")
    void delete(@Param(value="id") Integer id);

    @Select("select * from question where task_id=#{id}")
    List<Question> findById(@Param(value="id") Integer id);
}
