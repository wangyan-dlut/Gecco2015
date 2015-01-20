package experiment.functions;

import org.ejml.ops.RandomMatrices;

import algorithm.core.component.Code;
import algorithm.core.component.Encode;

import java.util.Random;

/**
 * Created by wwhh on 2015/1/18.
 */
public class Function_5 extends Function {
	private int groupSize;
	public Function_5(){
		super(-5, 5, 1000);
		groupSize = 50;
		M = RandomMatrices.createOrthogonal(groupSize, groupSize, new Random());
	}
	@Override
	public double run(Code code){
		Code P = Encode.createIntPriorityCode(code.getSize());
		Code z = getShiftedSolution(code);
		Code z1 = new Code();
		for(int i=0; i<groupSize; i++){
			z1.addGene(z.getDoubleGene(P.getIntGene(i)));
		}
		Code z2 = new Code();
		for(int i=groupSize; i<code.getSize(); i++){
			z2.addGene(z.getDoubleGene(P.getIntGene(i)));
		}
		return rot_rastriginFunction(z1) * 1e6 + rastriginFunction(z2);
	}
}
