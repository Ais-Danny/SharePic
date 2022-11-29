package top.aisdanny.dao;

import org.apache.ibatis.annotations.Select;
import top.aisdanny.model.Pic;

import java.util.List;

public interface PicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pic record);

    int insertSelective(Pic record);

    Pic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pic record);

    int updateByPrimaryKey(Pic record);

    @Select("select * from pic")
    List<Pic> selectAll();
}