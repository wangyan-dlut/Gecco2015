package algorithm.CCPSO;

import algorithm.core.component.Individual;
import algorithm.core.component.Population;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wwhh on 2014/6/30.
 */
public class variation {
	//变异
	public static void PopulationVariation(Population population, ArrayList<ArrayList<Integer>> randomIndex){
		int var1;
		int var2;
		Random random = new Random();
		int populationSize = population.getSize();
		int indexSize = randomIndex.size();
		for(int i=0; i<populationSize; i++){
			var1 = random.nextInt(indexSize);
			var2 = random.nextInt(indexSize);
			IndividualVariation(population.getIndividual(i),randomIndex.get(var1),randomIndex.get(var2));
		}

	}
	public static void IndividualVariation(Individual individual, ArrayList<Integer> var1, ArrayList<Integer> var2){
		double tmp;
		for(int i=0; i<var1.size(); i++){
			tmp = individual.getSection(0).getDoubleGene(var1.get(i));
			individual.getSection(0).setGene(var1.get(i),
					individual.getSection(0).getGene(var2.get(i)));
			individual.getSection(0).setGene(var2.get(i),tmp);
		}
	}
	public static Population swapMutation(Population population,
										  ArrayList<ArrayList<Integer>> randomIndex, double probability){
		Population tmp = select(population,probability);
		PopulationVariation(tmp,randomIndex);
		return tmp;
	}

	public static Population wmxCrossover(Population population,
										  ArrayList<ArrayList<Integer>> randomIndex, double probability){
		Population tmp = select(population,probability);
		int id = 2;
		Random random = new Random();
		while (id <= tmp.getSize()){
			int ran = random.nextInt(randomIndex.size());
			InCrossover(randomIndex.get(ran), tmp.getIndividual(id-2), tmp.getIndividual(id-1));
			id+=2;
		}
		return  tmp;
	}
	public static void InCrossover(ArrayList<Integer> randomIndex, Individual var1, Individual var2){
		double tmp;
		for(int i=0; i<randomIndex.size(); i++){
			tmp = var1.getSection(0).getDoubleGene(randomIndex.get(i));
			var1.getSection(0).setGene(randomIndex.get(i),
					var2.getSection(0).getDoubleGene(randomIndex.get(i)));
			var2.getSection(0).setGene(randomIndex.get(i),tmp);
		}
	}

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

	public static ArrayList<Population> minElitistSelection(Population population, Population pBest, Population lBest){
		int populationSize = pBest.getSize();
		Population tempPopulation = new Population();
		Population tempPBest = new Population();
		Population tempLBest = new Population();

		Population temp = population;

		double min = Double.MAX_VALUE;
		int id = Integer.MAX_VALUE;
		for(int i=0; i<populationSize; i++) {
			min = Double.MAX_VALUE;
			id = Integer.MAX_VALUE;

			for(int j=0; j<temp.getSize(); j++) {
				if(temp.getIndividual(j).getFitness()<min) {
					min=temp.getIndividual(j).getFitness();
					id=j;
				}
			}

			tempPopulation.addIndividual(temp.getIndividual(id));
			if(id < populationSize){
				tempPBest.addIndividual(pBest.getIndividual(id));
				tempLBest.addIndividual(lBest.getIndividual(id));
			}
			else{
				tempPBest.addIndividual(temp.getIndividual(id));
				tempLBest.addIndividual(temp.getIndividual(id));
			}
			temp.removeIndividual(id);
		}

		ArrayList<Population> result = new ArrayList<Population>();
		result.add(tempPopulation);
		result.add(tempPBest);
		result.add(tempLBest);
		return  result;
	}

}
