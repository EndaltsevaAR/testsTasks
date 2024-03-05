package hh.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> intsA = new ArrayList<>(Arrays.asList(5, 2, 4, 6, 1, 3, 2, 6));
        sort(intsA, 1, intsA.size());
        System.out.println(intsA);
    }

    public static void sort(List<Integer> intsA, int pStaCommonrtArray, int rEndCommonArray) {
        if (pStaCommonrtArray < rEndCommonArray) {
            int qMiddleArrays = (pStaCommonrtArray + rEndCommonArray) / 2;
            sort(intsA, pStaCommonrtArray, qMiddleArrays);
            sort(intsA, qMiddleArrays + 1, rEndCommonArray);
            merge(intsA, pStaCommonrtArray, qMiddleArrays, rEndCommonArray);
        }
    }

    public static void merge(List<Integer> intsA, int pStaCommonrtArray, int qMiddleArrays, int rEndCommonArray) {
        List<Integer> tempIntegers = new ArrayList<>();

        int secondArrayIndexCounter = qMiddleArrays;
        for (int i = pStaCommonrtArray - 1; i < qMiddleArrays;) {
            if (secondArrayIndexCounter >= rEndCommonArray || intsA.get(i) <= intsA.get(secondArrayIndexCounter)) {
                tempIntegers.add(intsA.get(i++));
            } else {
                tempIntegers.add(intsA.get(secondArrayIndexCounter));
                secondArrayIndexCounter++;
            }
        }

        for (int i = secondArrayIndexCounter; i < rEndCommonArray; i++) {
            tempIntegers.add(intsA.get(i));
        }

        for (int i = pStaCommonrtArray - 1, j = 0; i < rEndCommonArray; i++, j++) {
            intsA.set(i, tempIntegers.get(j));
        }
    }
}