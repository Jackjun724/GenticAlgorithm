package com.algorithm.gentic;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JackJun
 * 2019/8/12 20:04
 * Life is just about survival.
 */
public class Population {

    /**
     * 种群个体数量
     */
    private int count=100;

    /**
     * 种群变异率
     */
    private double variation=0.1;

    /**
     * 最优个体保留数目
     */
    private int retain=4;

    private double crossRate=0.5;

    /**
     * 最大变异基因个数
     */
    private int maxVariationNum = 10;

    private List<Individual> individuals = new ArrayList<>();


    /**
     * 初始化种群
     * @param count 种群个体数量
     * @param variation 种群变异率
     * @param retain 最优个体保留数目
     */
    public void initPopulation(int count,double variation,int retain,double crossRate){
        if(count<=0 || variation <0 || variation>1 || retain<=0 || crossRate<0){
            throw new IllegalArgumentException("参数错误!");
        }
        this.individuals = new ArrayList<>();
        this.variation = variation;
        this.retain = retain;
        this.crossRate = crossRate;
        Random random = new Random();
        for(int i=0;i<count;i++){
            Individual individual = new Individual();
            List<Gene> genes = new ArrayList<>();
            individual.setGenes(genes);
            Gene gene = new Gene();
            genes.add(gene);
            int[] geneArr = gene.getGene();

            for (int j=0;j<geneArr.length;j++){
                geneArr[j] = random.nextInt(2);
            }

            this.individuals.add(individual);
        }
    }

    /**
     * 繁衍一代
     */
    public void reproduction(){
        select();
        cross();
        variation();
    }

    /**
     * 获取最优秀子代
     */
    public Individual getTopIndividual(){
        //计算适应度
        individuals.forEach(item -> item.setFitness(item.calcFitness()));
        return individuals.stream().max((o1, o2) -> (int)(o1.calcFitness()*1000-o2.calcFitness()*1000)).orElse(new Individual());
    }

    /**
     * 选择
     */
    private void select(){
        List<Individual> sorted = individuals.stream().peek(item->item.setFitness(item.calcFitness())).sorted((o1, o2) -> (int)(o2.getFitness()*1000-o1.getFitness()*1000)).collect(Collectors.toList());

        // 选择后种群
        List<Individual> newPopulation = new ArrayList<>();
        // 复制最优个体
        for(int i=0;i<retain;i++){

            newPopulation.add(sorted.get(i));
        }

        for(int i=0;i<count-retain;i++){
            Individual selected = null;
            while(selected==null) {
                int num = new Random().nextInt(count);
                Individual individual = individuals.get(num);
                double probability = (individual.getFitness() + 20) / 40;
                double val = Math.random();
                if(val<=probability){
                    selected=individual;
                }
            }
            newPopulation.add(selected);
        }
        this.individuals=newPopulation;
    }

    /**
     * 交叉
     */
    private void cross(){
        List<Individual> newIndividual = new ArrayList<>();
        for(Individual item : individuals){
            double rate = Math.random();
            //进行交叉
            if (rate <= this.crossRate) {
                Individual target = individuals.get(new Random().nextInt(individuals.size()));
                Gene p1 = item.getGenes().get(0);
                Gene p2 = target.getGenes().get(0);
                int index = new Random().nextInt(p1.getGene().length);

                int[] newGene = new int[p1.getGene().length];
                for(int i=0;i<newGene.length;i++){
                    if(i<=index){
                        newGene[i]=p1.getGene()[i];
                    }else{
                        newGene[i]=p2.getGene()[i];
                    }
                }
                Individual result = new Individual();
                Gene resGene = new Gene();
                resGene.setGene(newGene);
                result.setGenes(new ArrayList<Gene>(){{add(resGene);}});
                newIndividual.add(result);
            }
        }
        individuals.addAll(newIndividual);
    }

    /**
     * 变异
     */
    private void variation(){
        int num=0;
        for(Individual item : individuals){
            double rate = Math.random();
            //进行变异 最优子代不变异
            if(num!=0 && rate<=this.variation){
                int count = new Random().nextInt(maxVariationNum+1);
                for(int i=0;i<count;i++){
                    Gene gene = item.getGenes().get(0);
                    int index = new Random().nextInt(gene.getGene().length);
                    gene.getGene()[index]^=1;
                }
            }
            num++;
        }
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getVariation() {
        return variation;
    }

    public void setVariation(double variation) {
        this.variation = variation;
    }

    public int getRetain() {
        return retain;
    }

    public void setRetain(int retain) {
        this.retain = retain;
    }

    public double getCrossRate() {
        return crossRate;
    }

    public void setCrossRate(double crossRate) {
        this.crossRate = crossRate;
    }
}
