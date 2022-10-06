package wuhobin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Noder {
    private int id;
    private double locX;
    private double locY;
    private double locZ;
    private double stressX;
    private double stressY;
    private double stressZ;
    private double stressXY;
    private double stressXZ;
    private double stressYZ;
    private double strainX;
    private double strainY;
    private double strainZ;
    private double strainXY;
    private double strainYZ;
    private double strainXZ;
    private double deformX;
    private double deformY;
    private double deformZ;
}
