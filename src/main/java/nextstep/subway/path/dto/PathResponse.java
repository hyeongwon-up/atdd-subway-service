package nextstep.subway.path.dto;

import nextstep.subway.station.domain.Station;

import java.util.List;

public class PathResponse {

    private List<Station> stations;

    private int distance;

    private int fare;

    public PathResponse(List<Station> stations, int distance) {
        this.stations = stations;
        this.distance = distance;
        this.fare = 0;
    }

    public PathResponse(List<Station> stations, int distance, int fare) {
        this(stations, distance);
        this.fare = fare;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }
}
