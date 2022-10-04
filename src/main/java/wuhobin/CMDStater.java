package wuhobin;

import wuhobin.container.CMDContainer;
import wuhobin.container.CMDSetting;
import wuhobin.listener.GLListener;

/**
 * @author XL Shi
 * @email xueli_shi@foxmail.com
 * @date 2022.04.13
 */
public class CMDStater {
    public static void main(String[] args) {

        CMDSetting setting = CMDSetting.getDefaultSetting();
        CMDContainer.init(setting);

        GLListener glListener = new GLListener();
        glListener.setUp();

    }
}
