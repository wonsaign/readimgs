package com.jiangjiang.readimgs;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListTest {

    public static void main(String[] args) {
        LinkedList<Integer> weights = Lists.newLinkedList(Stream.of(4,5,3,2,1).collect(Collectors.toList()));
        cutOffLastWeight(weights, 1);
        //cutOffLastWeight(weights, 3);
        System.out.println(weights);

        System.out.println(weights.stream().filter(e->e == 2).collect(Collectors.toList()));

    }

    private static LinkedList<Integer> cutOffLastWeight(LinkedList<Integer> weights, int thisWeight){
        int removeLastTimes = 0;
        for (int i = 0; i < weights.size(); i++) {
            if(weights.get(i) == thisWeight){
                removeLastTimes = weights.size() - 1 - i;
            }
        }
        while (removeLastTimes > 0){
            weights.removeLast();
            removeLastTimes--;
        }
        return weights;
    }
}
