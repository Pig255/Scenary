package wuhobin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wuhobin.mapper.NoderMapper;
import wuhobin.pojo.Noder;

import java.util.List;
@Service
public class NoderService {
    @Autowired
    private NoderMapper noderMapper;
    public void getID(){
        List<Noder> list= noderMapper.findAll();
        System.out.println(list);
    }
    public void start(){

    }
}
