package com.jiangjiang.readimgs.tools;

import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  abstract class RegexTemplate {

    private String regexStr;

    abstract void setRegexStr(String regexStr);

    public String regexContent(String words){

        if(regexStr != null && regexStr.length() > 0){
            Matcher matcherD = Pattern.compile(regexStr).matcher(words);
            while (matcherD.find()){
                return matcherD.group();
            }
        }
        return "";
    }
}
