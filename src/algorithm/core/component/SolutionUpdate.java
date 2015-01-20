package algorithm.core.component;

public class SolutionUpdate {
	
	public static Individual oneMinObjectiveUpdate(Population population, Individual bestIndividual) {
		Individual best = bestIndividual;
		for(int i=0; i<population.getSize(); i++) {
			if(population.getIndividual(i).getFitness()<best.getFitness()) {
				best = population.getIndividual(i).clone();
			}
		}
		return best;
	}
	
	public static Individual oneMinObjectiveUpdate(Population population) {
		Individual best = new Individual();
		best.setFitness(Double.MAX_VALUE);
		for(int i=0; i<population.getSize(); i++) {
			if(population.getIndividual(i).getFitness()<best.getFitness()) {
				best = population.getIndividual(i).clone();
			}
		}
		return best;
	}

}
