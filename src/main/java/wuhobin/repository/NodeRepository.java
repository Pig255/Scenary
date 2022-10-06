package wuhobin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wuhobin.container.CMDSetting;
import wuhobin.entity.Node;
import wuhobin.enums.ResultTypeEnum;
import wuhobin.listener.CMDSettingListener;
import wuhobin.mapper.NoderMapper;
import wuhobin.pojo.Noder;
import wuhobin.util.NormalUtil;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author XL Shi
 * @email xueli_shi@foxmail.com
 * @date 2022.04.12
 */
@Component
public class NodeRepository implements CMDSettingListener {
    @Autowired
    private NoderMapper noderMapper;
    public static NodeRepository nodeRepository;

    @PostConstruct
    public void innit() {
        nodeRepository = this;
        nodeRepository.noderMapper = this.noderMapper;
    }

    public static List<Noder> getAll() {
        List<Noder> list = nodeRepository.noderMapper.findAll();
        return list;
    }

    public static void importAction(int ID, double locx, double locy,
                                    double locz, double data, ResultTypeEnum type) {
        String tpe = String.valueOf(type);
        Noder noder = nodeRepository.noderMapper.getNoderByID(ID);
        if (null == noder) {
            //在这里调用mapper的insert方法。
            nodeRepository.noderMapper.insertNewNode(ID, locx, locy, locz, data, tpe);
        }else {
            nodeRepository.noderMapper.updateNewNode(ID,data,tpe);
        }


    }

    private Map<Integer, Node> nodeMap;
    private float maxData;
    private float minData;

    private CMDSetting setting;

    @Override
    public void init(CMDSetting setting) {
        this.setting = setting;
        this.maxData = 0;
        this.minData = 0;
        this.nodeMap = new HashMap<>();

        List<Float> coefficients = setting.getCoefficients();
        List<Map<ResultTypeEnum, String>> nodeFileMapList = setting.getNodeFileMapList();
        ResultTypeEnum activeResult = setting.getActiveResult();
        int size = coefficients.size();
        //判断是否需要入库，若需要走下面这行代码
        if (setting.getIsImport() == 1) {
            importNoder(setting);
        }
        //将数据存入NodeMap
        for (int i = 0; i < size; i++) {
            String oneFile = nodeFileMapList.get(i).get(activeResult);
            if (!NormalUtil.floatEquals(coefficients.get(i), 0.0f)) {
                addNodeMapFromFile(oneFile, coefficients.get(i));
            }
        }

        System.out.println("节点数据加载完毕，节点数: " + nodeMap.size());
        System.out.println("最大值: " + maxData + ", 最小值: " + minData);
    }

    private void importNoder(CMDSetting setting) {
        List<Float> coefficients = setting.getCoefficients();
        List<Map<ResultTypeEnum, String>> nodeFileMapList = setting.getNodeFileMapList();
        ResultTypeEnum activeResult = setting.getActiveResult();
        int size = coefficients.size();
        for (int i = 0; i < size; i++) {
            String oneFile = nodeFileMapList.get(i).get(activeResult);
            if (!NormalUtil.floatEquals(coefficients.get(i), 0.0f)) {
                importNoderFromFile(oneFile, coefficients.get(i), activeResult);
            }
        }
    }

    @Override
    public void update(CMDSetting setting) {
        if (NormalUtil.needUpdateNodeFile(setting, this.setting)) {
            init(setting);
        }
    }

    // 从单个文件中读取节点数据
    private void addNodeMapFromFile(String nodeFile, float coefficient) {

        float curMaxData = Float.MIN_VALUE;
        float curMinData = Float.MAX_VALUE;

        try (BufferedInputStream nodeIn = new BufferedInputStream(
                new FileInputStream(nodeFile))) {
            Scanner sc = new Scanner(nodeIn);
            while (sc.hasNext()) {
                if (!sc.hasNextDouble()) {
                    sc.next();
                    continue;
                }
                int index = sc.nextInt();
                float[] coord = new float[3];
                for (int i = 0; i < 3; i++) {
                    coord[i] = sc.nextFloat();
                }
                float data = sc.nextFloat();
                curMaxData = Math.max(curMaxData, data);
                curMinData = Math.min(curMinData, data);

                Node curNode = this.nodeMap.get(index);
                if (curNode == null) {
                    curNode = new Node();
                    curNode.setIndex(index);
                    curNode.setCoord(coord);
                    curNode.setData(data * coefficient);
                    this.nodeMap.put(index, curNode);
                } else {
                    curNode.setData(curNode.getData() + coefficient * data);
                }
            }

            this.maxData = this.maxData + curMaxData * coefficient;
            this.minData = this.minData + curMinData * coefficient;

        } catch (IOException e) {
            System.out.println("节点数据文件: " + nodeFile + "读取失败，已自动跳过");
        }
        System.out.println("节点数据文件: " + nodeFile + "读取完毕");
    }

    private void importNoderFromFile(String nodeFile, float coefficient, ResultTypeEnum type) {
        try (BufferedInputStream nodeIn = new BufferedInputStream(
                new FileInputStream(nodeFile))) {
            Scanner sc = new Scanner(nodeIn);
            while (sc.hasNext()) {
                if (!sc.hasNextDouble()) {
                    sc.next();
                    continue;
                }
                int index = sc.nextInt();
                double locx = sc.nextDouble();
                double locy = sc.nextDouble();
                double locz = sc.nextDouble();
                double data = sc.nextFloat() * coefficient;
                importAction(index, locx, locy, locz, data, type);
            }
        } catch (IOException e) {
            System.out.println("节点数据文件: " + nodeFile + "入库失败，已自动跳过");
        }
        System.out.println("节点数据文件: " + nodeFile + "入库成功");
    }


    public Map<Integer, Node> getNodeMap() {
        return nodeMap;
    }

    public float getMaxData() {
        return maxData;
    }

    public float getMinData() {
        return minData;
    }

}
