package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import nextstep.subway.path.exception.PathException;
import nextstep.subway.path.exception.PathExceptionType;
import nextstep.subway.station.domain.Station;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class JgraphPathFinder implements PathFinder {
    private WeightedMultigraph<Station, DefaultWeightedEdge> graph;

    @Override
    public StationPath getShortestPath(final List<Line> lines, final Station start, final Station destination) {
        validation(start, destination);
        graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        addStation(lines);
        addEdgeWeight(lines);

        return StationPath.of(new DijkstraShortestPath<>(graph).getPath(start, destination));
    }

    private void validation(final Station start, final Station destination) {
        if (start.equals(destination)) {
            throw new PathException(PathExceptionType.EQUALS_STATION);
        }
    }

    private void addStation(final List<Line> lines) {
        for (Line line : lines) {
            line.getSortedStations()
                    .forEach(it -> graph.addVertex(it));
        }
    }

    private void addEdgeWeight(final List<Line> lines) {
        for (Line line : lines) {
            line.getSections()
                    .forEach(this::addSection);
        }
    }

    private void addSection(final Section section) {
        final DefaultWeightedEdge edge = graph.addEdge(section.getUpStation(), section.getDownStation());
        graph.setEdgeWeight(edge, section.getDistance());
    }
}