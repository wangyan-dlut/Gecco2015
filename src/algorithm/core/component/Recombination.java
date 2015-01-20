package algorithm.core.component;

import java.util.Random;

public class Recombination {
	public static Population select(Population population, double probability) {
		Population offspring = new Population();
		Random rd = new Random();
		for(int i=0; i<population.getSize(); i++) {
			double rate = rd.nextDouble();
			if(rate<=probability) {
				offspring.addIndividual(population.getIndividual(i));
			}
		}
		return offspring;
	}

	//public static Population imigration(Population population, double probability) { }
	/*
	public Individual oneToOneRecombine(Individual individual) {
		Individual offspring = individual;		
		return offspring;
	}
	public Population nToNRecombine(Population population) {
		Population offspring = population;
		return offspring;
	}
	public Individual nToOneRecombine(Population population) {
		Individual offspring = new Individual();
		return offspring;
	}
	*/
}
