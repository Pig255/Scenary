package wuhobin.repository;

import org.springframework.stereotype.Component;
import wuhobin.container.CMDSetting;
import wuhobin.entity.ElementData;
import wuhobin.listener.CMDSettingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XL Shi
 * @email xueli_shi@foxmail.com
 * @date 2022.04.12
 */
// 加载单元数据
@Component
public abstract class ElementDataRepository implements CMDSettingListener {

    protected String elementFile;
    protected List<ElementData> elementDataList;

    @Override
    public void init(CMDSetting setting) {
        elementFile = setting.getElementFile();
        elementDataList = new ArrayList<>();
        readElementDataList();
        System.out.println("单元数据:" + elementFile + "\n读取完毕，单元数: " + elementDataList.size());
    }

    @Override
    public void update(CMDSetting setting) {
        String newElementFile = setting.getElementFile();
        if (!elementFile.equals(newElementFile)) {
            this.init(setting);
        }
    }

    // 读取网格
    protected abstract void readElementDataList();

    public List<ElementData> getElementDataList() {
        return elementDataList;
    }

}
