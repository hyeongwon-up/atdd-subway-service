package nextstep.subway.path.ui;

import nextstep.subway.path.dto.PathResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {

    @GetMapping(value = "/paths", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PathResponse> findShortestPath(@RequestParam Long source, @RequestParam Long target) {
        return ResponseEntity.ok().body(null);
    }
}
