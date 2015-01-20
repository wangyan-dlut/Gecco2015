package algorithm.core.component;

import java.util.Random;

public class Selection {
	public static Population minRouletteWheelSelection(Population population, int populationSize) {
		Population offspring = new Population();
		double F=0;
		double p[] = new double[population.getSize()];
		double q[] = new double[population.getSize()];
		int selectedIndividualList[] = new int[populationSize];
		Random rd = new Random();
		for(int i=0; i<population.getSize(); i++) {
			F=F+population.getIndividual(i).getFitness();
		}
		for(int i=0; i<population.getSize(); i++) {
			p[i]=population.getIndividual(i).getFitness()/F;
		}
		q[0]=p[0];
		for(int i=1; i<population.getSize(); i++) {
			q[i]=q[i-1]+p[i];
		}
		int id=0;
		for(int i=0; i<populationSize; i++) {
			double r = rd.nextDouble();
			if(r<q[0]) {
				selectedIndividualList[id]=0;
				id++;
				continue;
			}
			for(int j=1; j<population.getSize(); j++) {
				if(q[j-1]<=r && r<q[j]) {
					selectedIndividualList[id]=j;
					id++;
					break;
				}
			}
		}
		for(int i=0; i<populationSize; i++) {
			offspring.addIndividual(population.getIndividual(selectedIndividualList[i]).clone());
		}
		return offspring;
	}
	public static Population minElitistSelection(Population population, int populationSize) {
		Population offspring = new Population();
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
			
			offspring.addIndividual(temp.getIndividual(id));
			temp.removeIndividual(id);
		}
		
		return offspring;
	}
	public static Population replaceWorstIndividual(Population population, Individual individual) {
		Population offspring = population;
		double worst = Double.MIN_VALUE;
		int id=0;
		for(int i=0; i<population.getSize(); i++) {
			if(population.getIndividual(i).getFitness()>worst) {
				worst = population.getIndividual(i).getFitness();
				id = i;
			}
		}
		offspring.removeIndividual(id);
		offspring.addIndividual(individual.clone());
		return offspring;
	}
}
