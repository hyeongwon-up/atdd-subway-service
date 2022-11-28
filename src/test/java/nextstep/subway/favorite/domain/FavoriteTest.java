package nextstep.subway.favorite.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.subway.member.domain.Member;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FavoriteTest {
    private Member member;
    private Station source;
    private Station target;

    @BeforeEach
    void setUp() {
        member = new Member("valid@email.com", "valid_password", 26);
        source = new Station("출발역");
        target = new Station("도착역");
    }

    @DisplayName("출발역과 도착역이 동일한 경우 즐겨찾기 등록 시 예외가 발생한다.")
    @Test
    void createFavoriteWithSameSourceTarget() {
        assertThatThrownBy(() -> new Favorite(member, source, source))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출발역과 도착역이 동일한 경우 즐겨찾기로 등록할 수 없습니다.");
    }

    @DisplayName("즐겨찾기의 소유자가 아닌 경우 작업 요청 시 예외가 발생한다.")
    @Test
    void isNotOwner() {
        Favorite favorite = new Favorite(member, source, target);
        Member anotherMember = new Member("another@email.com", "another_password", 25);

        assertThatThrownBy(() -> favorite.isOperateByOwner(anotherMember))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본인의 즐겨찾기가 아닌 경우 작업을 진행할 수 없습니다.");
    }
}
