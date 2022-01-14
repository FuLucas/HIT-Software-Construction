package API;

import java.util.*;

import Interval.*;

import java.lang.Math;

public class APIs<L> {

    public static <L> double Similarity(MultiIntervalSet<L> s1, MultiIntervalSet<L> s2) {
        List<Interval<L>> intervals1 = s1.allIntervals();
        List<Interval<L>> intervals2 = s2.allIntervals();
        Collections.sort(intervals1);
        Collections.sort(intervals2);
        long start1 = intervals1.get(0).start();
        long start2 = intervals2.get(0).start();
        long startTime = start1 <= start2 ? start1 : start2; 
        long end1 = intervals1.get(0).end();
        for (Interval<L> I1 : intervals1) {
            if (I1.end() > end1) end1 = I1.end();
        }
        long end2 = intervals2.get(0).end();
        for (Interval<L> I2 : intervals2) {
            if (I2.end() > end2) end2 = I2.end();
        }
        long endTime = end1 >= end2 ? end1 : end2;
        double ret = 0.0;
        long sum = 0;
        for (Interval<L> I1 : intervals1) {
            for (Interval<L> I2 : intervals2) {
                sum += getRepeatCount(I1, I2);
            }
        }
        ret = (double)sum / (double)(endTime - startTime);
        return ret;
    }

    private static <L> long getRepeatCount(Interval<L> i1, Interval<L> i2) {
        // label is different not similar
        if (!i1.label().toString().equals(i2.label().toString())) return 0;
        long start1 = i1.start();
        long start2 = i2.start();
        long end1 = i1.end();
        long end2 = i2.end();
        // have no repeated part
        if (start1 >= end2 || end1 <= start2) return 0;
        // others
        else {
            long end = end1 >= end2 ? end1 : end2;
            long start = start1 <= start2 ? start1 : start2;
            return (end - start) - Math.abs(start1 - start2) - Math.abs(end1 - end2);
        }
    }

    public static <L>double calcConflictRatio(IntervalSet<L> set) {
        List<Interval<L>> list = set.getIntervals();
        Collections.sort(list);
        long conf = 0;
        int size = list.size();
        if (size == 0) return 0;
        for (int i = 0; i < size; i++) {
            int temp = 1;
            while(i + temp < size && list.get(i+temp).start() < list.get(i).end()) {
                if (list.get(i).end() > list.get(i+temp).end()) 
                    conf += (list.get(i+temp).end() - list.get(i+temp).start());
                else conf += (list.get(i).end() - list.get(i+temp).start());
                temp++;
            }
        }
        long start = list.get(0).start();
        long end = 0;
        for (int i = 0; i < size; i++) {
            long temp = list.get(i).end();
            end = end > temp ? end : temp;
        }
        return (double)conf / (double)(end - start);
    }

    public static <L>double calcConflictRatio(MultiIntervalSet<L> set) {
        List<Interval<L>> list = set.allIntervals();
        Collections.sort(list);
        long conf = 0;
        int size = list.size();
        if (size == 0) return 0;
        for (int i = 0; i < size; i++) {
            int temp = 1;
            while(i + temp < size && list.get(i+temp).start() < list.get(i).end()) {
                if (list.get(i).end() > list.get(i+temp).end()) 
                    conf += (list.get(i+temp).end() - list.get(i+temp).start());
                else conf += (list.get(i).end() - list.get(i+temp).start());
                temp++;
            }
        }
        long start = list.get(0).start();
        long end = Long.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            long temp = list.get(i).end();
            end = end > temp ? end : temp;
        }
        return (double)conf / (double)(end - start);
    }

    public static <L> double calcFreeTimeRatio(IntervalSet<L> set) {
        List<Interval<L>> list = set.getIntervals();
        Collections.sort(list);
        long free = 0;
        int size = list.size();
        if (size == 0) return 1;
        for (int i = 0; i < size; i++) {
            if (i + 1 < size && list.get(i+1).start() > list.get(i).end()) {
                free += list.get(i+1).start() - list.get(i).end();
            }
        }
        long start = list.get(0).start();
        long end = 0;
        for (int i = 0; i < size; i++) {
            long temp = list.get(i).end();
            end = end > temp ? end : temp;
        }
        return (double)free / (double)(end - start);
    }

    public static <L>double calcFreeTimeRatio(MultiIntervalSet<L> set) {
        List<Interval<L>> list = set.allIntervals();
        Collections.sort(list);
        long free = 0;
        int size = list.size();
        if (size == 0) return 1;
        for (int i = 0; i < size; i++) {
            if (i + 1 < size && list.get(i+1).start() > list.get(i).end()) {
                free += list.get(i+1).start() - list.get(i).end();
            }
        }
        long start = list.get(0).start();
        long end = 0;
        for (int i = 0; i < size; i++) {
            long temp = list.get(i).end();
            end = end > temp ? end : temp;
        }
        return (double)free / (double)(end - start);
    }
    
}
