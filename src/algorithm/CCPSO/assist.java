package algorithm.CCPSO;

import algorithm.core.component.Individual;
import algorithm.core.component.Population;

/**
 * Created by wwhh on 2014/6/22.
 */
public class assist {
	public static void showIn(Individual individual){
		for(int i=0; i<individual.getSection(0).getSize(); i++){
			System.out.print(""+individual.getSection(0).getDoubleGene(i)+"  ");
		}

		System.out.println();
		System.out.println(""+individual.getFitness());
	}
	public static void	showPo(Population population){
		for(int i=0; i<population.getSize(); i++){
			showIn(population.getIndividual(i));
		}
		System.out.println("end");
	}
}
