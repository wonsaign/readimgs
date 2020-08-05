package com.jiangjiang.readimgs;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

    public static void main(String[] args) {

        regexDate();
        regexMoney();
        regexCompany();
    }

    public static void regexDate(){
        String dataRegex = "(\\d{0,2}?\\d{0,2})[年|-](\\d{1,2})[月|-](\\d{1,2})[日]?";
        Pattern patternData = Pattern.compile(dataRegex);

        Matcher matcher = patternData.matcher("日期:2020年03月27日");
        String dataStr = "";
        while (matcher.find()) {
            String group = matcher.group();
            if (Strings.isNullOrEmpty(dataStr)) {
                dataStr = group;
                System.out.println(group);
            }

        }
        dataStr = dataStr.replaceAll("[年月日-]","");
        System.out.println(dataStr);
    }

    public static void regexMoney(){
        String moneyRegex = "[Cc][Nn][Yy](\\d{1,3}(,\\d{3})*(.((\\d{3},)*\\d{1,3}))?)|(\\d+(.\\d+)?)";
        Pattern patternMoney = Pattern.compile(moneyRegex);
        String moneyStr = "";
        Matcher matcherMoney = patternMoney.matcher("人民币:CnY31,240.00");
        while (matcherMoney.find()){
            String group = matcherMoney.group();
            if (Strings.isNullOrEmpty(moneyStr)) {
                moneyStr = group;
            }
        }
        System.out.println(moneyStr.replaceAll("[Cc][Nn][Yy]","") + "元");
    }

    public static void regexCompany(){
        String companyRegex = "收款人(户名|名称).+";
        Pattern patternCompany = Pattern.compile(companyRegex);
        String companyStr = "";
        Matcher matcherCompany = patternCompany.matcher("收款人户名:北京外企人力资源服务有限公司");
        //Matcher matcherCompany = patternCompany.matcher("收款人名称:北京外企人力资源服务有限公司");
        while (matcherCompany.find()){
            String group = matcherCompany.group();
            if (Strings.isNullOrEmpty(companyStr)) {
                companyStr = group;
            }
        }

        String[] splits = companyStr.split(":");
        System.out.println(splits[1]);
    }
}
