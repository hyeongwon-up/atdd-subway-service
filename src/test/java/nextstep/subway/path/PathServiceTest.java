package nextstep.subway.path;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.path.application.PathFinder;
import nextstep.subway.path.application.PathService;
import nextstep.subway.path.dto.PathResponse;
import nextstep.subway.sections.domain.Section;
import nextstep.subway.station.application.StationService;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PathServiceTest {

    @Mock
    private StationService stationService;

    @Mock
    private PathFinder pathFinder;

    @Mock
    private LineRepository lineRepository;

    private Station 강남역;
    private Station 양재역;
    private Station 남부터미널역;
    private Station 부산역;
    private List<Section> 모든구간;

    @BeforeEach
    public void setUp() {
        강남역 = new Station("강남역");
        양재역 = new Station("양재역");
        남부터미널역 = new Station("남부터미널역");
        부산역 = new Station("부산역");
        모든구간 = new ArrayList<>();
    }

    @DisplayName("역과 역 사이의 최단 경로를 조회한다.")
    @Test
    public void findShortestPath() {
        //given
        PathService pathService = new PathService(stationService, pathFinder, lineRepository);
        when(stationService.findStationById(1l)).thenReturn(강남역);
        when(stationService.findStationById(2l)).thenReturn(남부터미널역);
        when(pathFinder.findShortestPath(모든구간, 강남역, 남부터미널역)).thenReturn(new PathResponse(Arrays.asList(강남역, 양재역, 남부터미널역), 12L));

        //when
        PathResponse shortestPath = pathService.findShortestPath(1l, 2l);
        //then
        assertThat(shortestPath).isNotNull();
        assertThat(shortestPath.getStations())
            .hasSameElementsAs(Arrays.asList(강남역, 양재역, 남부터미널역));
    }

    @DisplayName("출발역과 도착역이 같은 경우 최단 경로 조회를 실패한다.")
    @Test
    public void findShortestPathWithSameStation() {
        //given
        PathService pathService = new PathService(stationService, pathFinder, lineRepository);
        when(stationService.findStationById(1l)).thenReturn(강남역);
        when(pathFinder.findShortestPath(모든구간, 강남역, 강남역)).thenThrow(IllegalArgumentException.class);

        //when
        //then
        assertThatThrownBy(() -> pathService.findShortestPath(1l, 1l))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출발역과 도착역이 연결되어 있지 않으면 최단 경로 조회를 실패한다.")
    @Test
    public void findShortestPathWithNotConnectedStation() {
        //given
        PathService pathService = new PathService(stationService, pathFinder, lineRepository);
        when(stationService.findStationById(1l)).thenReturn(강남역);
        when(stationService.findStationById(3l)).thenReturn(부산역);
        when(pathFinder.findShortestPath(모든구간, 강남역, 부산역)).thenThrow(IllegalArgumentException.class);
        //when
        //then
        assertThatThrownBy(() -> pathService.findShortestPath(1l, 3l))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않은 출발역이나 도착역을 조회 할 경우 최단 경로 조회를 실패한다.")
    @Test
    public void findShortestPathWithNotExistStation() {
        //given
        PathService pathService = new PathService(stationService, pathFinder, lineRepository);
        when(stationService.findStationById(1l)).thenReturn(강남역);
        when(stationService.findStationById(9999l)).thenReturn(null);
        when(pathFinder.findShortestPath(모든구간, 강남역, null)).thenThrow(IllegalArgumentException.class);
        //when
        //then
        assertThatThrownBy(() -> pathService.findShortestPath(1l, 9999l))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
