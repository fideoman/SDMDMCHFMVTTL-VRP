package org.moeaframework.problem.mip;

import java.io.IOException;
import java.util.stream.IntStream;

import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.RealMatrix;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.BinaryIntegerVariable;
import org.moeaframework.core.variable.BinaryVariable;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.problem.AbstractProblem;

import cl.usach.diinf.optimization.helpers.IOUtils;

public class SDMDMCHFMVTTLVRP extends AbstractProblem implements Problem {
	
	RealMatrix c1 = new Array2DRowRealMatrix();
	RealMatrix c2 = new Array2DRowRealMatrix();
	RealMatrix d  = new Array2DRowRealMatrix();
	RealMatrix q  = new Array2DRowRealMatrix();
	RealMatrix w  = new Array2DRowRealMatrix();
	RealMatrix o  = new Array2DRowRealMatrix();
	RealMatrix g  = new Array2DRowRealMatrix();
	RealMatrix a  = new Array2DRowRealMatrix();
	RealMatrix r  = new Array2DRowRealMatrix();
	
	int n1 = 0;
	int n2 = 0;
	int gdata = 0;
	
	// User Params
	int i1 = 2;
	int i2 = 3;
		
	int[] trucks = IntStream.range(0, i1).toArray();
	int[] helicopter = IntStream.range(i1, i1 + i2).toArray();		
	
	public SDMDMCHFMVTTLVRP() {
		super(7, 2, 22);
		try {
			c1 = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("C1.csv").getFile());
			c2 = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("C2.csv").getFile());
			d  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("d.csv").getFile());
			q  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("Q.csv").getFile());
			w  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("W.csv").getFile());
			o  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("O.csv").getFile());
			g  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("G.csv").getFile());
			a  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("A.csv").getFile());
			r  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("R.csv").getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 1) Get n1
		for (int i = 0; i < c1.getColumnVector(0).getDimension(); i++) {
			double data =  c1.getColumnVector(0).getEntry(i);
			if (data != 100000) {
				n1 = i;
				break;
			}
		}
		n2 = c1.getColumnDimension() - n1;
		gdata = d.getColumnDimension();
	}

	@Override
	public void evaluate(Solution solution) {
		// Initialize output variables
		double obj1 = 0.0;
		double obj2 = 0.0;
		
		double constraint1  = 0.0;
		double constraint2  = 0.0;
		double constraint3  = 0.0;
		double constraint4  = 0.0;
		double constraint5  = 0.0;
		double constraint6  = 0.0;
		double constraint7  = 0.0;
		double constraint8  = 0.0;
		double constraint9  = 0.0;
		double constraint10 = 0.0;
		double constraint11 = 0.0;
		double constraint12 = 0.0;
		double constraint13 = 0.0;
		double constraint14 = 0.0;
		double constraint15 = 0.0;
		double constraint16 = 0.0;
		double constraint17 = 0.0;
		double constraint18 = 0.0;
		double constraint19 = 0.0;
		double constraint20 = 0.0;
		double constraint21 = 0.0;
		double constraint22 = 0.0;
		
		// Problem definition section
		double ctt = 0.0;
		BinaryIntegerVariable s    = (BinaryIntegerVariable) solution.getVariable(0);
		BinaryIntegerVariable p    = (BinaryIntegerVariable) solution.getVariable(1);
		BinaryIntegerVariable k    = (BinaryIntegerVariable) solution.getVariable(2);
		BinaryIntegerVariable i    = (BinaryIntegerVariable) solution.getVariable(3);
		BinaryIntegerVariable j    = (BinaryIntegerVariable) solution.getVariable(4);
		BinaryIntegerVariable ga   = (BinaryIntegerVariable) solution.getVariable(5);
		BinaryVariable open 	   = (BinaryVariable) solution.getVariable(6);
		if (k.getValue() < i1) { // Truck
			ctt =  g.getEntry(s.getValue(), p.getValue()) * c1.getEntry(s.getValue(), p.getValue());
		} else { // Not truck
			ctt = c2.getEntry(s.getValue(), p.getValue());
		}
		
		int apertureCost = 0;
		for (int idx = 0; idx < o.getRowDimension(); idx++) {
			apertureCost += o.getEntry(idx, 0) * (open.get(idx) ? 1.0 : 0.0);
		}		
		
		// Final setup
		// Two objectives
		solution.setObjective(0, obj1);
		solution.setObjective(1, obj2);
		// 22 constraints
		solution.setConstraint(0,  constraint1  <= 0.0 ? 0.0 : 1);
		solution.setConstraint(1,  constraint2  <= 0.0 ? 0.0 : 1);
		solution.setConstraint(2,  constraint3  <= 0.0 ? 0.0 : 1);
		solution.setConstraint(3,  constraint4  <= 0.0 ? 0.0 : 1);
		solution.setConstraint(4,  constraint5  <= 0.0 ? 0.0 : 1);
		solution.setConstraint(5,  constraint6  <= 0.0 ? 0.0 : 1);
		solution.setConstraint(6,  constraint7  <= 0.0 ? 0.0 : 1);
		solution.setConstraint(7,  constraint8  <= 0.0 ? 0.0 : 1);
		solution.setConstraint(8,  constraint9  <= 0.0 ? 0.0 : 1);
		solution.setConstraint(9,  constraint10 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(10, constraint11 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(11, constraint12 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(12, constraint13 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(13, constraint14 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(14, constraint15 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(15, constraint16 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(16, constraint17 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(17, constraint18 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(18, constraint19 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(19, constraint20 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(20, constraint21 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(21, constraint22 <= 0.0 ? 0.0 : 1);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(7, 2, 22);
		//s
		solution.setVariable(0, EncodingUtils.newBinaryInt(0, g.getColumnDimension()));
		//p
		solution.setVariable(1, EncodingUtils.newBinaryInt(0, g.getColumnDimension()));
		// k
		solution.setVariable(2, EncodingUtils.newBinaryInt(0, i1 + i2 - 1));
		// i
		solution.setVariable(3, EncodingUtils.newBinaryInt(0, n1 - 1));
		// j
		solution.setVariable(4, EncodingUtils.newBinaryInt(n1, n1 + n2 -1));
		// g
		solution.setVariable(5, EncodingUtils.newBinaryInt(0, gdata -1));
		// open
		solution.setVariable(6, EncodingUtils.newBinary(o.getRowDimension()));
		
		return solution;
	}
}
