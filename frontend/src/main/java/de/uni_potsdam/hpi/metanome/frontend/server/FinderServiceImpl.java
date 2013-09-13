package de.uni_potsdam.hpi.metanome.frontend.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_potsdam.hpi.metanome.algorithm_integration.FunctionalDependencyAlgorithm;
import de.uni_potsdam.hpi.metanome.algorithm_integration.InclusionDependencyAlgorithm;
import de.uni_potsdam.hpi.metanome.algorithm_integration.UniqueColumnCombinationsAlgorithm;
import de.uni_potsdam.hpi.metanome.algorithm_loading.AlgorithmFinder;
import de.uni_potsdam.hpi.metanome.frontend.client.services.FinderService;

/**
 * Service Implementation for service that lists available algorithms
 */
public class FinderServiceImpl extends RemoteServiceServlet implements
		FinderService {

	private static final long serialVersionUID = 1L;
	
	AlgorithmFinder algorithmFinder = new AlgorithmFinder();

	/**
	 * 
	 * @param algorithmClass	the subclass of algorithms to be listed, or null for all algorithms
	 * @return	a list of filenames (without path)
	 */
	public String[] listAlgorithms(Class<?> algorithmClass) {
		String[] algorithms = null;
		try {
			algorithms = algorithmFinder.getAvailableAlgorithms(algorithmClass);
		} catch (Exception e){
			//TODO: error handling
			System.out.println("FAILED to FIND algorithms");
			e.printStackTrace();
		}
		//TODO: why is this not working on server but in tests?
		//return algorithms;
		return new String[] {"testjar.jar"};
	}

	@Override
	public String[] listInclusionDependencyAlgorithms() {
		return listAlgorithms(InclusionDependencyAlgorithm.class);
	}

	@Override
	public String[] listFunctionalDependencyAlgorithms() {
		return listAlgorithms(FunctionalDependencyAlgorithm.class);
	}

	@Override
	public String[] listUniqueColumnCombinationsAlgorithms() {
		return listAlgorithms(UniqueColumnCombinationsAlgorithm.class);
	}

}