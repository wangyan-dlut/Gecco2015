package experiment.functions;

import algorithm.core.component.Code;

/**
 * Created by wwhh on 2015/1/18.
 */
public class Function_20 extends Function{
	public Function_20(){
		super(-100, 100, 1000);
	}
	@Override
	public double run(Code code){
		Code z = getShiftedSolution(code);
		return rosenbrockFunction(z);
	}
}
