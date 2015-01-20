package algorithm.CCPSO;

import algorithm.core.component.Individual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wwhh on 2014/6/22.
 */
public class postionUpdate {
	public static void udpdate(ArrayList<Integer> index, Individual individual,
							   Individual pBest, Individual lBest,double updateProbability){
		Random random = new Random();
		for(int i=0; i<index.size(); i++){
			double p = random.nextDouble();
			if(p<updateProbability){
				double  cauchy = Math.tan(Math.PI * (random.nextDouble() - 0.5));
				double pTemp = pBest.getSection(0).getDoubleGene(index.get(i));
				double lTemp = lBest.getSection(0).getDoubleGene(index.get(i));
				double temp = pTemp+(cauchy*Math.abs(pTemp-lTemp));
				BigDecimal b = new BigDecimal(temp);
				temp = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				double result = temp>0? (temp<100? temp : pTemp) : pTemp;
				individual.getSection(0).setGene(index.get(i),result);
				//System.out.println(""+result);
				//individual.getSection(0).setGene(index.get(i),pTemp+Math.abs(cauchy*Math.abs(pTemp-lTemp)));
				//individual.getSection(0).setGene(index.get(i),(pTemp+Math.abs(cauchy*Math.abs(pTemp-lTemp)));
				//System.out.println(""+(cauchy*Math.abs(pTemp-lTemp)));
			}
			else{
				double Gaussian = random.nextGaussian();
				double pTemp = pBest.getSection(0).getDoubleGene(index.get(i));
				double lTemp = lBest.getSection(0).getDoubleGene(index.get(i));
				double temp = lTemp+(Gaussian*Math.abs(pTemp-lTemp));
				BigDecimal b = new BigDecimal(temp);
				temp = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				double result = temp>0? (temp<100? temp : pTemp) : pTemp;
				individual.getSection(0).setGene(index.get(i),result);
				//individual.getSection(0).setGene(index.get(i),lTemp+Math.abs(Gaussian*Math.abs(pTemp-lTemp)));
				//System.out.println(""+(Gaussian*Math.abs(pTemp-lTemp)));
			}
		}
	}
}
