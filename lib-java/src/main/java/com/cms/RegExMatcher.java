package com.cms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExMatcher {

    public static void main(String[] args) {
        findVolume1();
        findVolume2();
        findVolume3();
        findNumberField1();
        findNumberField2();
        findAddress();
    }


    private static void findVolume1() {
        String str = "越南胡志明市新山一机场（TAN SON NHAT AIRPORT，HO CHI MINH CITY，VIETNAM）" +
                ".860 X 1270 X 1840 重量：517KG\n";
        String pattern = "([0-9]+\\.?[0-9]*) X ([0-9]+\\.?[0-9]*) X ([0-9]+\\.?[0-9]*)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()) {
            double a = Double.parseDouble(m.group(1));
            double b = Double.parseDouble(m.group(2));
            double c = Double.parseDouble(m.group(3));
            double v = a * b * c;
            System.out.println(a + "*" + b + "*" + c + "=" + v);
        }
    }

    private static void findVolume2() {
        String str = "2PLTS 246K 1.2*0.8*1.034 1.2*0.8*0.518=1.49CBM LON";
        String pattern = "([0-9]+\\.?[0-9]*)\\*([0-9]+\\.?[0-9]*)\\*([0-9]+\\.?[0-9]*)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()) {
            double a = Double.parseDouble(m.group(1));
            double b = Double.parseDouble(m.group(2));
            double c = Double.parseDouble(m.group(3));
            double v = a * b * c;
            System.out.println(a + "*" + b + "*" + c + "=" + v);
        }
    }

    private static void findVolume3() {
        String str = "2PLTS 246K 1.2*0.8*1.034 1.2*0.8*0.518=1.49CBM LON";
        //str = "你好  上海—伊朗 德黑兰  3件  7100公斤   4.2立方米";
        String pattern = "([0-9]+\\.?[0-9]*)(CBM|立方米)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()) {
            double v = Double.parseDouble(m.group(1));
            System.out.println("体积=" + v);
        }
    }

    private static void findNumberField1() {
        String str = "12 156 0.3 上海 西班牙 月底出运";
        String pattern = "([0-9]+\\.?[0-9]*) ([0-9]+\\.?[0-9]*) ([0-9]+\\.?[0-9]*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()) {
            int num = Integer.parseInt(m.group(1));
            double w = Double.parseDouble(m.group(2));
            double v = Double.parseDouble(m.group(3));
            System.out.println("件数=" + num + "重量=" + w + "体积=" + v);
        }
    }

    private static void findNumberField2() {
        String str = "2/1.96/700   bud";
        String pattern = "([0-9]+\\.?[0-9]*)/([0-9]+\\.?[0-9]*)/([0-9]+\\.?[0-9]*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while (m.find()) {
            int num = Integer.parseInt(m.group(1));
            double v = Double.parseDouble(m.group(2));
            double w = Double.parseDouble(m.group(3));
            System.out.println("件数=" + num + "重量=" + w + "体积=" + v);
        }
    }

    private static void findAddress(){
        String str = "12 156 0.3 货在上海 飞西班牙 月底出运";
        String pattern = "货在(上海|西班牙)";
        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(str);
        while (m.find()) {
            System.out.println("始发港=" + m.group(0));
        }
    }
}
