package top.aisdanny.service.Impl;

import org.springframework.stereotype.Service;
import top.aisdanny.dao.PicMapper;
import top.aisdanny.model.Pic;
import top.aisdanny.service.PicServices;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PicServicesImpl implements PicServices{
    @Resource
    PicMapper picMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Pic record) {
        picMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(Pic record) {
        picMapper.insertSelective(record);
        return 0;
    }

    @Override
    public Pic selectByPrimaryKey(Integer id) {
        Pic pic=picMapper.selectByPrimaryKey(id);
        return pic;
    }

    @Override
    public int updateByPrimaryKeySelective(Pic record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Pic record) {
        return 0;
    }

    @Override
    public List<Pic> selectAll() {
        return picMapper.selectAll();
    }
}
