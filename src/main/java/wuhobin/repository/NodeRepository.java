package wuhobin.repository;
import wuhobin.container.CMDSetting;
import wuhobin.entity.Node;
import wuhobin.enums.ResultTypeEnum;
import wuhobin.listener.CMDSettingListener;
import wuhobin.util.NormalUtil;
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
public class NodeRepository implements CMDSettingListener {

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

        for (int i = 0; i < size; i++) {
            String oneFile = nodeFileMapList.get(i).get(activeResult);
            if (!NormalUtil.floatEquals(coefficients.get(i), 0.0f)) {
                addNodeMapFromFile(oneFile, coefficients.get(i));
            }
        }

        System.out.println("节点数据加载完毕，节点数: " + nodeMap.size());
        System.out.println("最大值: " + maxData + ", 最小值: " + minData);
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
