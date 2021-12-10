package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Section;
import org.jgrapht.graph.DefaultWeightedEdge;

public class SubwayWeightedEdge extends DefaultWeightedEdge {
    private Section section;

    public void setSection(Section section) {
        this.section = section;
    }

    public Section getSection() {
        return section;
    }
}