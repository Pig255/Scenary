package wuhobin.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wuhobin.entity.Node;
import wuhobin.entity.impl.TenNodeTetrahedronElementData;
import wuhobin.mapper.NoderMapper;
import wuhobin.pojo.Noder;
import wuhobin.repository.ElementDataRepository;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * @author XL Shi
 * @email xueli_shi@foxmail.com
 * @date 2022.05.23
 */

@Component
public class TenNodeTetrahedronElementDataRepository extends ElementDataRepository {
    @Autowired
    private NoderMapper noderMapper;
    public static TenNodeTetrahedronElementDataRepository tenNodeTetrahedronElementDataRepository;
    @PostConstruct
    public void init(){
        tenNodeTetrahedronElementDataRepository=this;
        tenNodeTetrahedronElementDataRepository.noderMapper=this.noderMapper;
    }
    public static void getALL(){
        List<Noder> list=tenNodeTetrahedronElementDataRepository.noderMapper.findAll();
        System.out.println(list+"我爱你");
    }

    @Override
    protected void readElementDataList() {
        getALL();
        try (BufferedInputStream elementIn = new BufferedInputStream(
                new FileInputStream(elementFile))) {
            Scanner sc = new Scanner(elementIn);
            while (sc.hasNext()) {
                if (!sc.hasNextInt()) {
                    sc.next();
                    continue;
                }
                for (int i = 0; i < 6; i++) {
                    sc.next();
                }
                int[] nodes = new int[10];
                for (int i = 0; i < 10; i++) {
                    nodes[i] = sc.nextInt();
                }
                elementDataList.add(new TenNodeTetrahedronElementData(nodes));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
