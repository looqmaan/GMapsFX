package com.lynden.example.ucsdgraphs.roadgraph;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import com.lynden.example.ucsdgraphs.geography.GeographicPoint;

/**
 * 
 * @author Nay Lwin
 *
 */
public class BreadthFirstSearch extends Search<GeographicPoint> {

	/**
	 * Constructor of BFS class. Call to initialize method.
	 * 
	 * @param graph
	 */
	public BreadthFirstSearch(final MapGraph graph) {
		super();
		this.graph = graph;
		initialize();
	}

	/**
	 * BreadFirstSearch implementation method.
	 */
	@Override
	public Optional<List<GeographicPoint>> searchPath(GeographicPoint start, GeographicPoint goal) {

		final Queue<GeographicPoint> queue = new LinkedList<>();
		boolean found = false;

		/**
		 * Mark starting geographic point (vertex) as visited and add it to queue.
		 */
		visit(start);
		queue.add(start);

		/**
		 * Loop through vertices in queue: For each vertex, compared to goal. If they
		 * are equal, set boolean true to found, and break out of loop. Otherwise, loop
		 * through neighboring vertices, and mark it as visited and add to queue. Also
		 * keep track of the path in parentMap.
		 * 
		 */
		while (!queue.isEmpty()) {
			GeographicPoint current = queue.remove();

			if (current.equals(goal)) {
				found = true;
				break;
			}

			List<Road<GeographicPoint>> neighbors = graph.getNeighbors(current);

			neighbors.forEach(e -> {
				Road<GeographicPoint> road = (Road<GeographicPoint>) e;
				if (!isVisited(road.getTo())) {
					GeographicPoint next = road.getTo();
					visit(next);
					getNodeAccepter().accept(next);
					queue.add(next);
					parentMap.put(next, current);
				}
			});
		}

		/**
		 * Utilize Java 8 feature Optional class here to avoid NullPointerException.
		 */
		final Optional<List<GeographicPoint>> optional;
		if (!found)
			optional = Optional.empty();
		else {
			List<GeographicPoint> value = constructPath(start, goal, parentMap);
			optional = Optional.of(value);
		}

		return optional;
	}

}
