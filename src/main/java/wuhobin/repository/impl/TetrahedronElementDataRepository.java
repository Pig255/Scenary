package wuhobin.repository.impl;
import wuhobin.entity.impl.TetrahedronElementData;
import wuhobin.repository.ElementDataRepository;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author XL Shi
 * @email xueli_shi@foxmail.com
 * @date 2022.04.22
 */
public class TetrahedronElementDataRepository extends ElementDataRepository {

    // 读取四面体网格
    @Override
    protected void readElementDataList() {
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
                int[] nodes = new int[4];
                for (int i = 0; i < 4; i++) {
                    nodes[i] = sc.nextInt();
                }
                for (int i = 0; i < 6; i++) {
                    if (sc.hasNext()) {
                        sc.next();
                    }
                }
                elementDataList.add(new TetrahedronElementData(nodes));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
