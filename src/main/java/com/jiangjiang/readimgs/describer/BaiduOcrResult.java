package com.jiangjiang.readimgs.describer;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaiduOcrResult {

    Long log_id;

    List<WordResult> words_result;

    Integer words_result_num;

    Integer direction;

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class WordResult{
        Probability probability;
        String words;
        Location location;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Probability{
        Double average;
        Double min;
        //Double variance;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Location{
        Double top;
        Double left;
        Double width;
        Double height;
    }


}
