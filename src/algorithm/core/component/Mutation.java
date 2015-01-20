package algorithm.core.component;

import java.util.Random;

public class Mutation{
	public static Population swapMutation(Population population, double mutationProbability) {
		Population offspring = Recombination.select(population, mutationProbability);
		Random rd = new Random();
		for(int i=0; i<offspring.getSize(); i++) {
			
			//Encode.printIntCode(offspring.getIndividual(i).getSection(0));
			
			int pos1 = rd.nextInt(offspring.getIndividual(i).getSection(0).getSize());
			int pos2 = pos1;
			while (pos1==pos2) {
				pos2 = rd.nextInt(offspring.getIndividual(i).getSection(0).getSize());
			}
			Gene temp1 = offspring.getIndividual(i).getSection(0).getGene(pos1);
			Gene temp2 = offspring.getIndividual(i).getSection(0).getGene(pos2);
			
			//System.out.println("pos: "+pos1+", "+pos2);
			
			offspring.getIndividual(i).getSection(0).setGene(pos1, temp2);
			offspring.getIndividual(i).getSection(0).setGene(pos2, temp1);
			
			//Encode.printIntCode(offspring.getIndividual(i).getSection(0));
		}
		return offspring;
	}
}
