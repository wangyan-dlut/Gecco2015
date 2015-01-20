package experiment.functions;

import algorithm.core.component.Code;

/**
 * Created by wwhh on 2015/1/18.
 */
public class Function_3 extends Function{
	public Function_3(){
		super(-32, 32, 1000);
	}
	@Override
	public double run(Code code){
		Code shiftedSolution = getShiftedSolution(code);
		return ackleyFunction(shiftedSolution);
	}
}
