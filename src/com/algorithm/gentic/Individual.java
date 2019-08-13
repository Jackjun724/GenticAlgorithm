package com.algorithm.gentic;

import java.util.List;

/**
 * @author JackJun
 * 2019/8/12 19:49
 * Life is just about survival.
 * 单个个体
 */
public class Individual {
    private List<Gene> genes;

    private double fitness;

    /**
     * 计算个体适应度
     * @return y值
     */
    public double calcFitness(){
        Gene gene;
        if((gene=genes.get(0))==null){
            return 0;
        }
        double val = gene.decode(gene.getGene());
        return val*Math.sin(2*Math.PI*val)+2;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
