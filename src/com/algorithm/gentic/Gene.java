package com.algorithm.gentic;

/**
 * @author JackJun
 * 2019/8/12 19:50
 * Life is just about survival.
 * 基因序列
 * 单个基因序列 在区间2-16之间 求出保留三位小数最优解
 * 将 2-16 平分成14000份  即2^14
 */
public class Gene {
    private int[] gene = new int[14];

    /**
     * 解码
     * @return 解码后值
     * @param encode 要解码值
     */
    public double decode(int[] encode){
        int decimalNum = binaryToDecimalNum(encode);
        return (double)2+(double)decimalNum/Math.pow(2,14)*(16-2);
    }

    /**
     * 编码
     * @param decimal 被编码值
     * @return 编码后值
     */
    public int[] encode(double decimal){
        int num = (int) (((decimal-2)/14)*Math.pow(2,14));
        return decimalNumToBinary(num);
    }


    private int binaryToDecimalNum(int[] binary){
        StringBuilder strBuilder = new StringBuilder();
        for (int i : binary) {
            strBuilder.append(i);
        }
        strBuilder.reverse();
        return Integer.valueOf(strBuilder.toString(),2);
    }

    private int[] decimalNumToBinary(int num){
        String resStr = Integer.toBinaryString(num);
        int[] res = new int[resStr.length()];
        char[] charArr = resStr.toCharArray();
        for(int i = charArr.length,index=0;i>0;i--,index++){
            res[index]= Integer.parseInt(String.valueOf(charArr[i-1]));
        }
        return res;
    }

    public int[] getGene() {
        return gene;
    }

    public void setGene(int[] gene) {
        this.gene = gene;
    }
}
