package cl.usach.diinf.optimization.mip.runnables;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.moeaframework.Executor;
import org.moeaframework.core.population.NondominatedPopulation;
import org.moeaframework.util.io.PopulationIO;
import org.moeaframework.problem.mip.SDMDMCHFMVTTLVRP;

public class RunSolver {

	public static void main(String[] args) {
		NondominatedPopulation referenceSet = new NondominatedPopulation();
		Executor executor = new Executor().withProblemClass(SDMDMCHFMVTTLVRP.class).distributeOnAllCores();
		executor.withAlgorithm("NSGAII").withProperty("operator","sbx+hux+pm+bf");
		referenceSet.addAll(executor.run());

		// Store returned Pareto Fronts in your $HOME folder
		try {
			PopulationIO
					.writeObjectives(
							new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator
									+ "NonDominatedPopulation-" + Instant.now().getEpochSecond() + ".pf"),
							referenceSet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
