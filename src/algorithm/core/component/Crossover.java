package algorithm.core.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Crossover {
	public static Population wmxCrossover(Population population, double crossoverProbability) {
		Population offspring = Recombination.select(population, crossoverProbability);
		//System.out.println("PopSize="+offspring.getSize());
		int id=2;
		Random rd = new Random();
		while(id<=offspring.getSize()) {

			//Encode.printIntCode(offspring.getIndividual(id-2).getSection(0));
			//Encode.printIntCode(offspring.getIndividual(id-1).getSection(0));
			
			int pos = rd.nextInt(offspring.getIndividual(id-2).getSection(0).getSize());
			//System.out.println("Pos:"+pos); 
			
			List<Integer> sub1 = new ArrayList<Integer>();
			List<Integer> sub2 = new ArrayList<Integer>();
			for(int i=pos; i<offspring.getIndividual(id-2).getSection(0).getSize(); i++) {
				sub1.add(offspring.getIndividual(id-2).getSection(0).getIntGene(i));
				sub2.add(offspring.getIndividual(id-1).getSection(0).getIntGene(i));					
			}
			Collections.sort(sub1);
			Collections.sort(sub2);
			/*
			for(int i=0; i<sub1.size(); i++) {
				System.out.print(" "+sub1.get(i));
			}
			System.out.println();
			for(int i=0; i<sub2.size(); i++) {
				System.out.print(" "+sub2.get(i));
			}
			System.out.println();
			*/
			for(int i=pos; i<offspring.getIndividual(id-2).getSection(0).getSize(); i++) {
				int j1=0;
				int j2=0;
				for(int j=0; j<sub2.size(); j++) {
					if(offspring.getIndividual(id-1).getSection(0).getIntGene(i)==sub2.get(j)) {
						j1=j;
						break;
					}
				}
				for(int j=0; j<sub1.size(); j++) {
					if(offspring.getIndividual(id-2).getSection(0).getIntGene(i)==sub1.get(j)) {
						j2=j;
						break;
					}
				}
				offspring.getIndividual(id-2).getSection(0).setGene(i, sub1.get(j1));
				offspring.getIndividual(id-1).getSection(0).setGene(i, sub2.get(j2));
				
			}
			
			//Encode.printIntCode(offspring.getIndividual(id-2).getSection(0));
			//Encode.printIntCode(offspring.getIndividual(id-1).getSection(0));
			//System.out.println();
			
			id=id+2;
		}
		return offspring;
	}
}
