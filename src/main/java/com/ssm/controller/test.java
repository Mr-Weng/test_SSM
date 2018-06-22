package com.ssm.controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void textOut(String fileName, String text1, String text2) {
        try {
            File f = new File(fileName);

            BufferedImage image = ImageIO.read(f);
            Graphics g = image.getGraphics();
            int width = image.getWidth();
            //画黑色矩形
            g.setColor(Color.black);
            g.fillRect(0,0,width,80);
            //设置字体
            g.setFont(new Font("宋体", Font.PLAIN, 30));
            g.setColor(Color.WHITE);
            g.drawString(text1, 0, 30);
            g.drawString(text2, 0,70);
            //释放资源
            g.dispose();
            ImageIO.write(image, "JPG", f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String date1 = format2.format(date);

        String cp = "粤D8888";
        int cd = 3;
        String sd = "100m/s";

        double co2 = 1.33;
        double co = 1.33;
        double hc = 1.33;
        double no = 1.33;
        double yd = 1.33;
        String jg = "正常";

        String text1 = "抓拍时间:"+ date1 +"\t   行驶方向:南向北\t   车牌号:"+ cp +"\t   车道号:"+ cd +"\t   地点:东沽路\t   速度:"+ sd;
        String text2 = "CO2:"+ co2 +"\t   CO:"+ co +"\t   HC:"+ hc +"\t   NO:"+ no +"\t   不透光烟度:"+ yd +"\t   检测结果:"+jg;

        textOut("C:\\Users\\NHT\\Desktop\\image\\1.jpg", text1, text2);
    }
}
