package experiment.functions;

import org.ejml.ops.RandomMatrices;

import algorithm.core.component.Code;
import algorithm.core.component.Encode;

import java.util.Random;

/**
 * Created by wwhh on 2015/1/18.
 */
public class Function_15 extends Function {
	private int groupSize;
	public Function_15(){
		super(-5, 5, 1000);
		groupSize = 50;
		M = RandomMatrices.createOrthogonal(groupSize, groupSize, new Random());
	}
	@Override
	public double run(Code code){
		Code P = Encode.createIntPriorityCode(code.getSize());
		Code z = getShiftedSolution(code);
		double res = 0.0;
		for(int i=0; i<(dimension/groupSize); i++) {
			Code z1 = new Code();
			for (int j = i * groupSize; j < (i + 1) * groupSize; j++) {
				z1.addGene(z.getDoubleGene(P.getIntGene(j)));
			}
			res += rot_rastriginFunction(z1);
		}
		return res;
	}
}
