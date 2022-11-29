package top.aisdanny.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import top.aisdanny.model.Pic;
import top.aisdanny.pojo.Result;
import top.aisdanny.service.PicServices;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PicController {
    @Resource
    PicServices picServices;
    @RequestMapping(value = "/saveImg",
            method = RequestMethod.POST
    )
    @ResponseBody//type--文件类型，name--文件名，img--base64图片
    public Object saveImg(@RequestBody JSONObject json, HttpServletRequest request) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            try {
                //mysql datetime类型与java.util.Date需转码
                Pic pic=new Pic(null, new java.sql.Date(new Date().getTime()),"img/"+json.getString("name")+json.getString("type"));
                picServices.insertSelective(pic);
            }catch (Exception e){
                return "重复文件";
            }
            String src=request.getSession().getServletContext().getRealPath("/")+"/img/";
            new File(src).mkdirs();
            String imgSrc=src+json.getString("name")+json.getString("type");
            FileOutputStream write = new FileOutputStream(new File(imgSrc));
            byte[] decoderBytes = decoder.decodeBuffer(json.getString("img"));
            write.write(decoderBytes);
            write.close();
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }
    @RequestMapping("/getPic")
    @ResponseBody
    public Result getPic(Date date){
        List<Pic> picList=picServices.selectAll();
        List<String> srcList=new ArrayList<>();
        SimpleDateFormat sp=new SimpleDateFormat("yyyy/MM/dd");
        String flag="\nAll:"+picList.size();
        for (Pic pic:picList) {
            if(date==null) {
                srcList.add(pic.getSrc());
                continue;
            }
            if(sp.format(pic.getTime()).equals(sp.format(date)))
                srcList.add(pic.getSrc());
        }
        flag="Select:"+srcList.size()+flag;
        return new Result(flag,srcList);
    }
}
