package nextstep.subway.line.unit;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import nextstep.subway.line.domain.Sections;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철 구간 리스트 기능")
public class SectionsTest {

    private Station 강남역;
    private Station 광교역;
    private Station 판교역;
    private Station 역삼역;
    private Station 없는역;
    private Line 신분당선;
    private Section 판교_역삼_구간;
    private Sections 구간들;

    @BeforeEach
    public void setUp() {
        강남역 = new Station("강남역");
        광교역 = new Station("광교역");
        판교역 = new Station("판교역");
        역삼역 = new Station("역삼역");
        없는역 = new Station("없는역");

        신분당선 = new Line("신분당선", "빨간색", 강남역, 판교역, 10);
        신분당선.addStation(광교역, 판교역, 5);

        판교_역삼_구간 = new Section(null, 판교역, 역삼역, 10);

        구간들 = 신분당선.getSections();
    }

    @Test
    @DisplayName("구간 추가")
    void add() {
        구간들.add(판교_역삼_구간);
        assertThat(구간들.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("구간 삭제")
    void remove() {
        구간들.remove(구간들.getList().get(0));
        assertThat(구간들.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("상행 구간 조회")
    void getUpLineStation() {
        assertThat(구간들.getUpLineStation(강남역)).isPresent();
        assertThat(구간들.getUpLineStation(없는역)).isEmpty();
    }

    @Test
    @DisplayName("하행 구간 조회")
    void getDownLineStation() {
        assertThat(구간들.getDownLineStation(광교역)).isPresent();
        assertThat(구간들.getDownLineStation(없는역)).isEmpty();
    }

    @Test
    @DisplayName("구간 내 지하철역 정렬")
    void getStations() {
        구간들.add(판교_역삼_구간);
        List<Station> stations = 구간들.getStations();
        assertThat(stations.get(0)).isEqualTo(강남역);
        assertThat(stations.get(1)).isEqualTo(광교역);
        assertThat(stations.get(2)).isEqualTo(판교역);
        assertThat(stations.get(3)).isEqualTo(역삼역);
    }

}