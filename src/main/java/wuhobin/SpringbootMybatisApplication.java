package wuhobin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import wuhobin.container.CMDContainer;
import wuhobin.container.CMDSetting;
import wuhobin.listener.GLListener;

@SpringBootApplication
public class SpringbootMybatisApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class, args);
        System.out.println("---------------启动成功------------------");
        CMDSetting setting = CMDSetting.getDefaultSetting();
        CMDContainer.init(setting);
        GLListener glListener = new GLListener();
        glListener.setUp();
    }

}
