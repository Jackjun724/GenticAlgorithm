package com.algorithm;

import com.algorithm.gentic.Population;


/**
 * @author JackJun
 */
public class Main {

    public static void main(String[] args) {
        // 利用遗传算法
        // f(x)=x*sin(2*π*x)+2  x∈[2,16] 求最优解
        Population population = new Population();
        population.initPopulation(100,0.1,4,0.5);
        for(int i=0;i<100;i++){
            population.reproduction();

            System.out.println("第"+(i+1)+"代最优解:"+ population.getTopIndividual().calcFitness());
        }

    }
}
