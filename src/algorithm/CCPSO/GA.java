package algorithm.CCPSO;

import algorithm.core.component.Individual;
import algorithm.core.component.Population;
import experiment.jsp.Jsp;
import experiment.jsp.JspDecode;
import algorithm.core.scheduling.Schedule;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wwhh on 2014/7/1.
 */
public class GA {
	public static Population select(Population population, double probability){
		Population offspring = new Population();
		Random rd = new Random();
		for(int i=0; i<population.getSize(); i++) {
			double rate = rd.nextDouble();
			if(rate<=probability) {
				offspring.addIndividual((Individual)population.getIndividual(i).clone(0));
			}
		}
		return offspring;
	}
	public static Population swapMutation(Population population, ArrayList<Integer> randomIndex, double probability){
		Population offspring = select(population,probability);
		Random random = new Random();
		int indexSize = randomIndex.size();
		for(int i=0; i<offspring.getSize(); i++){
			int var1 = random.nextInt(indexSize);
			int var2 = random.nextInt(indexSize);
			while(var2 == var1){		//keep var2!=var1
				var2 = random.nextInt(indexSize);
			}

			double tmp = offspring.getIndividual(i).getSection(0).getDoubleGene(randomIndex.get(var1));
			offspring.getIndividual(i).getSection(0).setGene(randomIndex.get(var1),
					offspring.getIndividual(i).getSection(0).getGene(randomIndex.get(var2)));
			offspring.getIndividual(i).getSection(0).setGene(randomIndex.get(var2),tmp);
		}
		return offspring;
	}
	public static Population wmxCrossover(Population population, ArrayList<Integer> randomIndex, double probability){
		Population offspring = select(population,probability);
		int id = 2;
		Random random = new Random();
		int indexSize = randomIndex.size();
		while (id <= offspring.getSize()){
			int ran = random.nextInt(indexSize);
			for(int i=ran; i<indexSize; i++){
				double tmp = offspring.getIndividual(id-2).getSection(0).getDoubleGene(randomIndex.get(i));
				offspring.getIndividual(id-2).getSection(0).setGene(randomIndex.get(i),
						offspring.getIndividual(id-1).getSection(0).getDoubleGene(randomIndex.get(i)));
				offspring.getIndividual(id-1).getSection(0).setGene(randomIndex.get(i),tmp);
			}

			id+=2;
		}
		return  offspring;
	}
	public static Population minElitistSelection(Population tmp, ArrayList<Integer> randomIndex, int populationSize,
												 Individual gBest, Jsp jsp){
		Population result = new Population();
		ArrayList<Double> benefit = new ArrayList<Double>();
		for(int i=0; i<tmp.getSize(); i++){
			benefit.add(bestPostionUpdate.benefit(randomIndex,tmp.getIndividual(i),gBest,jsp));
		}
		double min = Double.MAX_VALUE;
		int id = Integer.MAX_VALUE;
		for(int i=0; i<populationSize; i++) {
			min = Double.MAX_VALUE;
			id = Integer.MAX_VALUE;

			for(int j=0; j<tmp.getSize(); j++) {
				if(benefit.get(j) < min) {
					min=benefit.get(j);
					id=j;
				}
			}
			result.addIndividual(tmp.getIndividual(id));
			tmp.removeIndividual(id);
			benefit.remove(id);
		}
		return result;
	}
	public static void populationReplace(ArrayList<Integer> randomIndex, Population invariant, Population variant){
		for(int i=0; i<invariant.getSize(); i++){
			bestPostionUpdate.replace(randomIndex,invariant.getIndividual(i),variant.getIndividual(i));
		}
	}
	public static int getLBest(Population population,Jsp jsp){
		int id = 0;
		Double minfitness = Double.MAX_VALUE;
		for (int i = 0; i < population.getSize(); i++) {  //每个 individual 有自己的 code ，即一个染色体
			population.getIndividual(i).setSolution
					(JspDecode.doublePriorityDecode(population.getIndividual(i).getSection(0), jsp));
			Schedule s = (Schedule) population.getIndividual(i).getSolution();
			population.getIndividual(i).setFitness(s.getMakespan());
			if(population.getIndividual(i).getFitness() < minfitness){
				minfitness = population.getIndividual(i).getFitness();
				id = i;
			}
		}
		return id;
	}
}
