package top.aisdanny.service;

import org.springframework.stereotype.Service;
import top.aisdanny.model.Pic;

import javax.annotation.Resource;
import java.util.List;


public interface PicServices {
    int deleteByPrimaryKey(Integer id);

    int insert(Pic record);

    int insertSelective(Pic record);

    Pic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pic record);

    int updateByPrimaryKey(Pic record);

    List<Pic> selectAll();
}
