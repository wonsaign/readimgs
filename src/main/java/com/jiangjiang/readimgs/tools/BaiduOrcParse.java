package com.jiangjiang.readimgs.tools;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.google.common.base.Strings;
import com.jiangjiang.readimgs.constant.ConstantEnum;
import com.jiangjiang.readimgs.describer.BaiduOcrResult;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaiduOrcParse implements OrcParse{

    //设置APPID/AK/SK
    private static final String APP_ID = "19186630";
    private static final String API_KEY = "xtBoulTpFNSnqn8w2fS0B8ic";
    private static final String SECRET_KEY = "dFNxu3o2SxD45DwCITG2fpMKGKqOw00G";
    private final static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    @Override
    public void parse(byte[] bytes) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>(16);
        options.put("recognize_granularity", "big");
        options.put("probability", "true");
        options.put("accuracy", "normal");
        options.put("detect_direction", "true");

        // 参数为本地图片二进制数组
        JSONObject res = client.receipt(bytes, options);

        BaiduOcrResult baiduOcrResult = JSON.parseObject(res.toString(), BaiduOcrResult.class);
        System.out.println(JSON.toJSONString(baiduOcrResult));
        if(baiduOcrResult == null ){
            return;
        }

        String dataRegex = "(\\d{0,2}?\\d{0,2})[年|-](\\d{1,2})[月|-](\\d{1,2})[日]?";

        String moneyRegex = "[Cc][Nn][Yy](\\d{1,3}(,\\d{3})*(.((\\d{3},)*\\d{1,3}))?)";

        String receiverRegex = "收款人(户名|名称).+";

        String payRegex = "付款人(户名|名称).+";

        // 支票类型
        String invoicePayType = "付款(回单|通知).?";

        String invoiceReceiverType = "收款(回单|通知).?";


        // 时间
        String dataStr = "";
        // 金额
        String moneyStr = "";

        // 有可能 是收款方  或者 付款方
        String receiverCompanyStr = "";

        String payCompanyStr = "";

        int invoiceType = 0;


        for (BaiduOcrResult.WordResult wordResult : baiduOcrResult.getWords_result()) {
            String words = wordResult.getWords();
            if (Strings.isNullOrEmpty(words)) {
                continue;
            }
            if (Strings.isNullOrEmpty(dataStr)) {
                dataStr = regexContent(dataRegex, words);
            }

            if (Strings.isNullOrEmpty(moneyStr)) {
                moneyStr = regexContent(moneyRegex, words);
            }

            // 收款人
            if (Strings.isNullOrEmpty(receiverCompanyStr)) {
                receiverCompanyStr = regexContent(receiverRegex, words);
            }

            // 付款人
            if (Strings.isNullOrEmpty(payCompanyStr)) {
                payCompanyStr = regexContent(payRegex, words);
            }

            // 付款人
            if (invoiceType == 0) {
                if(!Strings.isNullOrEmpty(regexContent(invoicePayType, words))){
                    invoiceType = ConstantEnum.InvoiceType.PAY.getCode();
                }
                if(!Strings.isNullOrEmpty(regexContent(invoiceReceiverType, words))){
                    invoiceType = ConstantEnum.InvoiceType.RECEIVER.getCode();
                }
            }

        }

        dataStr = dataStr.replaceAll("[年月日-]","");
        moneyStr = moneyStr.replaceAll("[Cc][Nn][Yy]","") + "元";


        String companyStr = "";
        String invoiceTypeName = "";
        if(ConstantEnum.InvoiceType.RECEIVER.getCode() == invoiceType){
            companyStr = payCompanyStr;
            invoiceTypeName = ConstantEnum.InvoiceType.RECEIVER.getComment();
        }
        if(ConstantEnum.InvoiceType.PAY.getCode() == invoiceType){
            companyStr = receiverCompanyStr;
            invoiceTypeName = ConstantEnum.InvoiceType.PAY.getComment();
        }

        String[] splis = companyStr.split(":");
        if (splis != null && splis.length > 1) {
            companyStr = companyStr.split(":")[1];
        }

        String filename =invoiceTypeName + "-" + dataStr + "-" + companyStr + "-" + moneyStr + ".png";

        String path = System.getProperty("user.dir") + File.separator + "CMB" + File.separator;
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        String localPath = path+filename;

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream =
                    new FileOutputStream(localPath);
            try {
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
            } catch (IOException e) {
            }
        } catch (FileNotFoundException e) {
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
            }
        }
        System.out.println("日期解析是：" + dataStr);
        System.out.println("金钱解析是：" + moneyStr);
        System.out.println("公司解析是：" + companyStr);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~");
    }



    private String regexContent(String regexStr, String words){
        if(regexStr != null && regexStr.length() > 0){
            Matcher matcherD = Pattern.compile(regexStr).matcher(words);
            while (matcherD.find()){
                return matcherD.group();
            }
        }
        return "";
    }
}
