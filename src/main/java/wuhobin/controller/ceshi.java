package wuhobin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wuhobin.container.CMDContainer;
import wuhobin.container.CMDSetting;
import wuhobin.listener.GLListener;
import wuhobin.mapper.NoderMapper;
import wuhobin.pojo.Noder;

import java.util.List;

@RestController
public class ceshi {
    @Autowired
    private NoderMapper noderMapper;
    @RequestMapping("ceshi")
    public String ceshi(){
        List<Noder> list= noderMapper.findAll();
        System.out.println(list);
        CMDSetting setting = CMDSetting.getDefaultSetting();
        CMDContainer.init(setting);
        GLListener glListener = new GLListener();
        glListener.setUp();
        return "祝佳雯大憨批";
    }
}
