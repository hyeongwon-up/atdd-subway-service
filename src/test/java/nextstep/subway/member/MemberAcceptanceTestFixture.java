package nextstep.subway.member;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.member.dto.MemberRequest;
import nextstep.subway.member.dto.MemberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class MemberAcceptanceTestFixture {
    public static ExtractableResponse<Response> 회원_생성을_요청(String email, String password, Integer age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_정보_조회_요청(ExtractableResponse<Response> response) {
        String uri = response.header("Location");

        return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(uri)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_정보_수정_요청(ExtractableResponse<Response> response, String email,
                                                            String password, Integer age) {
        String uri = response.header("Location");
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().put(uri)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_삭제_요청(ExtractableResponse<Response> response) {
        String uri = response.header("Location");
        return RestAssured
                .given().log().all()
                .when().delete(uri)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_정보_조회_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().get("/members/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_정보_수정_요청(String accessToken, String email, String password, int age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(memberRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/members/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_정보_삭제_요청(String accessToken) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/members/me")
                .then().log().all()
                .extract();
    }

    public static void 회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 회원_정보_조회됨(ExtractableResponse<Response> response, String email, int age) {
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertThat(memberResponse.getId()).isNotNull();
        assertThat(memberResponse.getEmail()).isEqualTo(email);
        assertThat(memberResponse.getAge()).isEqualTo(age);
    }

    public static void 회원_정보_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 회원_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 내_정보_조회됨(ExtractableResponse<Response> response, String email, int age) {
        MemberResponse meResponse = response.as(MemberResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(meResponse.getEmail()).isEqualTo(email);
        assertThat(meResponse.getAge()).isEqualTo(age);
    }

    public static void 내_정보_수정됨(ExtractableResponse<Response> response, String email, int age) {
        MemberResponse meResponse = response.as(MemberResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(meResponse.getEmail()).isEqualTo(email);
        assertThat(meResponse.getAge()).isEqualTo(age);
    }

    public static void 내_정보_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
