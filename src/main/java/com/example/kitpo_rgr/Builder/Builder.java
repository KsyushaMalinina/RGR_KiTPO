package com.example.kitpo_rgr.Builder;

import com.example.kitpo_rgr.Comparator.Comparator;

import java.io.InputStreamReader;


public interface Builder
{
    Object createObject();
    Object readObject();
    Object clone();
    Object parseObject(String ss);
    Comparator getComparator();
    String getName();

}
