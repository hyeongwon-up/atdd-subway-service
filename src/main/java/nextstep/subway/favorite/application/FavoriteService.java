package nextstep.subway.favorite.application;

import nextstep.subway.favorite.domain.Favorite;
import nextstep.subway.favorite.domain.FavoriteRepository;
import nextstep.subway.favorite.dto.FavoriteRequest;
import nextstep.subway.favorite.dto.FavoriteResponse;
import nextstep.subway.member.application.MemberService;
import nextstep.subway.member.domain.Member;
import nextstep.subway.station.application.StationService;
import nextstep.subway.station.domain.Station;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FavoriteService {

    private final MemberService memberService;
    private final FavoriteRepository favoriteRepository;
    private final StationService stationService;

    public FavoriteService(
            MemberService memberService,
            FavoriteRepository favoriteRepository,
            StationService stationService
    ) {
        this.memberService = memberService;
        this.favoriteRepository = favoriteRepository;
        this.stationService = stationService;
    }

    public FavoriteResponse createFavorites(Long memberId, FavoriteRequest favoriteRequest) {
        Member member = memberService.findMemberById(memberId);
        Station sourceStation = stationService.findStationById(favoriteRequest.getSource());
        Station targetStation = stationService.findStationById(favoriteRequest.getTarget());

        Favorite favorite = Favorite.of(member, sourceStation, targetStation);
        Favorite favoriteResult = favoriteRepository.save(favorite);

        return FavoriteResponse.of(favoriteResult);
    }
}
