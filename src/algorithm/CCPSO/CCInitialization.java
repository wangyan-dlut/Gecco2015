package algorithm.CCPSO;

import algorithm.core.component.Individual;
import algorithm.core.component.Population;

import java.util.*;

/**
 * Created by wwhh on 2014/6/22.
 */
public class CCInitialization {
	public static ArrayList<ArrayList<Integer>> getRandomIndex(int codeSize, int[] S){
		int[] index = new int[codeSize];
		for(int i=0; i<codeSize; i++){
			index[i] = i;
		}
		Random random = new Random();
		int t = 0;
		while(t<codeSize){
			int tempA = random.nextInt(codeSize);
			int tempB = random.nextInt(codeSize);
			if(tempA != tempB){
				int temp = index[tempA];
				index[tempA]  = index[tempB];
				index[tempB] = temp;
				t++;
			}
		}

		ArrayList<ArrayList<Integer>> KIndex = new ArrayList<ArrayList<Integer>>();
		int s = S[random.nextInt(S.length)];
		int k = codeSize/s;
		for(int i=0; i<k; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int j=0; j<s; j++){
				temp.add(index[i*s+j]);
			}
			KIndex.add(temp);
		}
		//showKIndex(KIndex);
		//System.exit(1);
		return KIndex;
	}
	static void showKIndex(ArrayList<ArrayList<Integer>> KIndex){
		int s = KIndex.get(0).size();
		int k = KIndex.size();
		for(int i=0; i<k; i++){
			for(int j=0; j<s; j++){
				System.out.print(""+KIndex.get(i).get(j)+"  ");
			}
			System.out.println();
		}
	}
	public static Population getCopyPopulation(Population population){
		Population temp = new Population();
		for(int  i=0; i<population.getSize(); i++){
			Individual individual = (Individual)population.getIndividual(i).clone(0);
			temp.addIndividual(individual);
		}
		return temp;
	}

}
