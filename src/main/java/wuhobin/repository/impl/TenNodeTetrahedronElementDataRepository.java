package wuhobin.repository.impl;
import wuhobin.entity.impl.TenNodeTetrahedronElementData;
import wuhobin.repository.ElementDataRepository;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author XL Shi
 * @email xueli_shi@foxmail.com
 * @date 2022.05.23
 */
public class TenNodeTetrahedronElementDataRepository extends ElementDataRepository {

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
