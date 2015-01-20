package algorithm.CBCC;

import org.apache.commons.math3.distribution.CauchyDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

import algorithm.core.component.Individual;
import algorithm.core.component.Population;
import experiment.functions.Function;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by evolab.cn on 2015/1/17.
 * Self-adaptive Differential Evolution with Neighborhood Search
 * Zhenyu Yang, Ke Tang and Xin Yao
 * IEEE Congress on Evolutionary Computation 2008
 */

public class SaNSDE {
	private int groupSize; //种群大小
	private Function function;

	/*用于p的更新*/
	private double P;
	private int indicatorOfP[];//用来指示本次世代用的是哪个公式 1:(1),2:(3)
	private int Pns1;
	private int Pns2;
	private int Pnf1;
	private int Pnf2;
	/*用于权重的概率值的更新*/
	private double FP;
	private int indicatorOfFP[];//用来指示本次世代用的是哪个分布,1:normal, 2:cauchy
	private int Fns1;
	private int Fns2;
	private int Fnf1;
	private int Fnf2;
	/*用于变异率的更新*/
	private double CRm;
	private double CR[];
	private ArrayList<Double> deltaFunc_rec;
	private ArrayList<Double> CR_rec;

	public SaNSDE(int groupSize, Function function){
		this.groupSize = groupSize;
		this.function = function;
		P = 0.5;
		indicatorOfP = new int[groupSize];
		Pns1 = 0;
		Pns2 = 0;
		Pnf1 = 0;
		Pnf2 = 0;
		FP = 0.5;
		indicatorOfFP = new int[groupSize];
		Fns1 = 0;
		Fns2 = 0;
		Fnf1 = 0;
		Fnf2 = 0;

		CRm = 0.5;
		CR = new double[groupSize];
		NormalDistribution normalDistributionCR = new NormalDistribution(CRm, 0.1);
		for(int i=0; i<groupSize; i++) {
			CR[i] = normalDistributionCR.sample();
		}
		deltaFunc_rec = new ArrayList<Double>();
		CR_rec = new ArrayList<Double>();
	}
	public Population optimizer(Individual best, Population subPop, ArrayList<Integer> indices, int iteration){
		Random random = new Random();
		Population res;
		//每次F都要重新生成
		double[] F = new double[subPop.getSize()];
		NormalDistribution normalDistributionF = new NormalDistribution(0.5, 0.3);
		CauchyDistribution cauchyDistribution = new CauchyDistribution();
		for(int i=0; i<subPop.getSize(); i++) {
			if (random.nextDouble() < FP) {
				F[i] = normalDistributionF.sample();
				indicatorOfFP[i] = 1;
			} else {
				F[i] = cauchyDistribution.sample();
				indicatorOfFP[i] = 2;
			}
		}
		res = mutation(best, subPop, indices, F);
		res = crossover(subPop, res);
		res = selection(subPop, res, best, indices);
		//每5次迭代重新生成CR
		if(iteration % 5 == 0) {
			NormalDistribution normalDistributionCR = new NormalDistribution(CRm, 0.1);
			for(int i=0; i<groupSize; i++){
				CR[i] = normalDistributionCR.sample();
			}
		}
		//每25世代调整CRm
		if(iteration % 25 == 0){
			double sumOfDeltaFunc_rec = 0.0;
			for(int i=0; i<deltaFunc_rec.size(); i++){
				sumOfDeltaFunc_rec += deltaFunc_rec.get(i);
			}
			CRm = 0;
			for(int i=0; i<CR_rec.size(); i++){
				CRm += (deltaFunc_rec.get(i) / sumOfDeltaFunc_rec) * CR_rec.get(i);
			}
			deltaFunc_rec.clear();
			CR_rec.clear();
		}
		//每50世代调整p和fp
		if(iteration % 50 == 0){
			double temp1 = (Pns1 * (Pns2 + Pnf2));
			double temp2 = (Pns2 * (Pns1 + Pnf1) + Pns1 * (Pns2 + Pnf2));
			P = temp1 / temp2;
			Pns1 = 0;
			Pns2 = 0;
			Pnf1 = 0;
			Pnf2 = 0;

			temp1 = Fns1 * (Fns2 + Fnf2);
			temp2 = Fns2 * (Fns1 + Fnf1) + Fns1 * (Fns2 + Fnf2);
			FP = temp1 / temp2;
			Fns1 = 0;
			Fns2 = 0;
			Fnf1 = 0;
			Fnf2 = 0;
		}
		return res;

	}
	private int getSubPopBest(Individual best, Population subPop, ArrayList<Integer> indices){
		Individual replacedBest = replace(best, subPop.getIndividual(0), indices);
		double bestFitness = function.run(replacedBest.getSection(0));
		int index = 0;
		double tempFitness;
		for(int i=1; i<subPop.getSize(); i++){
			replacedBest = replace(best, subPop.getIndividual(i), indices);
			tempFitness = function.run(replacedBest.getSection(0));
			if(tempFitness < bestFitness){
				bestFitness = tempFitness;
				index = i;
			}
		}
		return index;
	}
	private Individual replace(Individual best, Individual individual, ArrayList<Integer> indices){
		Individual res = best.clone();
		for(int i=0; i<indices.size(); i++){
			res.getSection(0).setGene(indices.get(i), individual.getSection(0).getDoubleGene(i));
		}
		return res;
	}
	private Population mutation(Individual best, Population subPop, ArrayList<Integer> indices, double[] F){
		Population res = new Population();
		Random random = new Random();
		for(int i=0; i<subPop.getSize(); i++) {
			if (random.nextDouble() < P) {
				indicatorOfP[i] = 1;
				int popSize = subPop.getSize();
				int i1 = random.nextInt(popSize);
				int i2 = random.nextInt(popSize);
				while (i2 == i1) {
					i2 = random.nextInt(popSize);
				}
				int i3 = random.nextInt(popSize);
				while (i3 == i1 || i3 == i2) {
					i3 = random.nextInt(popSize);
				}
				Individual temp = subPop.getIndividual(i1).clone();
				for (int j = 0; j < temp.getSection(0).getSize(); j++) {
					temp.getSection(0).setGene(j, temp.getSection(0).getDoubleGene(j) +
							F[i] * (subPop.getIndividual(i2).getSection(0).getDoubleGene(j) - subPop.getIndividual(i3).getSection(0).getDoubleGene(j)));
				}
				res.addIndividual(temp);

			} else {
				indicatorOfP[i] = 2;
				int popSize = subPop.getSize();
				Individual subBest = subPop.getIndividual(getSubPopBest(best, subPop, indices));
				int i1 = random.nextInt(popSize);
				int i2 = random.nextInt(popSize);
				while (i2 == i1) {
					i2 = random.nextInt(popSize);
				}
				Individual temp = subPop.getIndividual(i).clone();
				for (int j = 0; j < temp.getSection(0).getSize(); j++) {
					temp.getSection(0).setGene(j, temp.getSection(0).getDoubleGene(j) +
							F[i] * (subBest.getSection(0).getDoubleGene(j) - subPop.getIndividual(i).getSection(0).getDoubleGene(j)) +
							F[i] * (subPop.getIndividual(i1).getSection(0).getDoubleGene(j) - subPop.getIndividual(i2).getSection(0).getDoubleGene(j)));
				}
				res.addIndividual(temp);

			}
		}
		return res;
	}
	private Population crossover(Population subPop, Population candidate){
		Random random = new Random();
		Population res = new Population();
		for(int i=0; i<subPop.getSize(); i++){
			Individual temp = subPop.getIndividual(i).clone();
			int index = random.nextInt(temp.getSection(0).getSize());//保证至少有一个纬度上是变异的
			for(int j = 0; j<temp.getSection(0).getSize(); j++){
				if(random.nextDouble() <= CR[i] || j == index){
					temp.getSection(0).setGene(j, candidate.getIndividual(i).getSection(0).getDoubleGene(j));
				}
			}
			res.addIndividual(temp);
		}
		return res;
	}
	private Population selection(Population subPop, Population candidate, Individual best, ArrayList<Integer> indices){
		Population res = new Population();
		for(int i=0; i<subPop.getSize(); i++){
			Individual tempSubPop = replace(best, subPop.getIndividual(i), indices);
			double fitnessOfSubPop = function.run(tempSubPop.getSection(0));
			Individual tempCandidate = replace(best, candidate.getIndividual(i), indices);
			double fitnessOfCandidate = function.run(tempCandidate.getSection(0));
			if(fitnessOfSubPop < fitnessOfCandidate){
				//不比原来的好，更新失败
				res.addIndividual(subPop.getIndividual(i).clone());
				Pnf1 += (indicatorOfP[i] == 1) ? 1 : 0;
				Pnf2 += (indicatorOfP[i] == 2) ? 1 : 0;
				Fnf1 += (indicatorOfFP[i] == 1) ? 1 : 0;
				Fnf2 += (indicatorOfFP[i] == 2) ? 1 : 0;

			}
			else{
				//比原来的好，更新成功
				res.addIndividual(candidate.getIndividual(i).clone());
				Pns1 += (indicatorOfP[i] == 1) ? 1 : 0;
				Pns2 += (indicatorOfP[i] == 2) ? 1 : 0;
				Fns1 += (indicatorOfFP[i] == 1) ? 1 : 0;
				Fns2 += (indicatorOfFP[i] == 2) ? 1 : 0;
				CR_rec.add(CR[i]);
				deltaFunc_rec.add(fitnessOfSubPop - fitnessOfCandidate);
			}
		}
		return res;
	}
}
