package com.wenlin.roundbanner.diamond;

/**
 * @author 文琳
 * @time 2020/5/8 10:21
 * @desc
 */
public class DiamondUtil {


    /**
     * 获取菱形的中心点坐标数组
     *         //以屏幕左下角为原点(0,0)，第一行的脚标为0的菱形在左下角；                           1个
     *         // 第二行，从第一个菱形的右边数，从右下到左上分别是脚标为1、2、3的菱形；              3个
     *         //第三行，从右下到左上分别是脚标为4、5、6、7、8的菱形；                              5个
     *         //第四行，从右下到左上分别是脚标为9、10、11、12、13、14、15的菱形；                  7个
     *         //第五行，从右下到左上分别是脚标为16、17、18、19、20、21、22的菱形；                 7个
     *         //第六行，从右下到左上分别是脚标为23、24、25、26、27的菱形；                         5个
     *         //第七行，从右下到左上分别是脚标为28、29、30的菱形；                                 3个
     *         //第八行，最右上角为脚标为31的菱形；                                                1个
     * @param firstCenterX
     * @param firstCenterY
     * @param space
     * @return
     */
    public static Point[] getDiamondsCenterPoints(float firstCenterX, float firstCenterY, float space){
        Point[] points = new Point[32];
        //第一行
        points[0] = new Point(firstCenterX, firstCenterY);
        //第二行
        points[1] = new Point(firstCenterX + space, firstCenterY);
        points[2] = new Point(firstCenterX + space / 2, firstCenterY - space / 2);
        points[3] = new Point(firstCenterX, firstCenterY - space);
        //第三行
        points[4] = new Point(firstCenterX + space * 2, firstCenterY);
        points[5] = new Point(firstCenterX + space * 3 / 2, firstCenterY - space / 2);
        points[6] = new Point(firstCenterX + space, firstCenterY - space);
        points[7] = new Point(firstCenterX + space / 2, firstCenterY - space * 3 / 2);
        points[8] = new Point(firstCenterX, firstCenterY - space * 2);
        //第四行
        points[9] = new Point(firstCenterX + space * 3, firstCenterY);
        points[10] = new Point(firstCenterX + space * 5 / 2, firstCenterY - space / 2);
        points[11] = new Point(firstCenterX + space * 2, firstCenterY - space);
        points[12] = new Point(firstCenterX + space * 3 / 2, firstCenterY - space * 3 / 2);
        points[13] = new Point(firstCenterX + space, firstCenterY - space * 2);
        points[14] = new Point(firstCenterX + space / 2, firstCenterY - space * 5 / 2);
        points[15] = new Point(firstCenterX, firstCenterY - space * 3);
        //第五行
        points[16] = new Point(firstCenterX + space * 3, firstCenterY - space);
        points[17] = new Point(firstCenterX + space * 5 / 2, firstCenterY - space * 3 / 2);
        points[18] = new Point(firstCenterX + space * 2, firstCenterY - space * 2);
        points[19] = new Point(firstCenterX + space * 3 / 2, firstCenterY - space * 5 / 2);
        points[20] = new Point(firstCenterX + space, firstCenterY - space * 3);
        points[21] = new Point(firstCenterX + space / 2, firstCenterY - space * 7 / 2);
        points[22] = new Point(firstCenterX, firstCenterY - space * 4);
        //第六行
        points[23] = new Point(firstCenterX + space * 3, firstCenterY - space * 2);
        points[24] = new Point(firstCenterX + space * 5 / 2, firstCenterY - space * 5 / 2);
        points[25] = new Point(firstCenterX + space * 2, firstCenterY - space * 3);
        points[26] = new Point(firstCenterX + space * 3 / 2, firstCenterY - space * 7 / 2);
        points[27] = new Point(firstCenterX + space, firstCenterY - space * 4);
        //第七行
        points[28] = new Point(firstCenterX + space * 3, firstCenterY - space * 3);
        points[29] = new Point(firstCenterX + space * 5 / 2, firstCenterY - space * 7 / 2);
        points[30] = new Point(firstCenterX + space * 2, firstCenterY - space * 4);
        //第八行
        points[31] = new Point(firstCenterX + space * 3, firstCenterY - space * 4);
        return points;
    }
}
