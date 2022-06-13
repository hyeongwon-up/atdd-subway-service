package nextstep.subway.member;

import static nextstep.subway.member.MemberRestAssured.회원_삭제_요청;
import static nextstep.subway.member.MemberRestAssured.회원_생성을_요청;
import static nextstep.subway.member.MemberRestAssured.회원_정보_수정_요청;
import static nextstep.subway.member.MemberRestAssured.회원_정보_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.member.dto.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class MemberAcceptanceTest extends AcceptanceTest {
    String EMAIL = "email@email.com";
    String PASSWORD = "password";
    String NEW_EMAIL = "newemail@email.com";
    String NEW_PASSWORD = "newpassword";
    int AGE = 20;
    int NEW_AGE = 21;

    /**
     *   Scenario: 회원 정보를 관리한다.
     *     When 회원 생성을 요청
     *     Then 회원 생성됨
     *     When 회원 정보 조회 요청
     *     Then 회원 정보 조회됨
     *     When 회원 정보 수정 요청
     *     Then 회원 정보 수정됨
     *     When 회원 정보 삭제 요청
     *     Then 회원 정보 삭제됨
     */
    @DisplayName("회원 정보를 관리한다.")
    @Test
    void manageMember() {
        // when
        ExtractableResponse<Response> createResponse = 회원_생성을_요청(EMAIL, PASSWORD, AGE);
        // then
        회원_생성됨(createResponse);

        // when
        ExtractableResponse<Response> findResponse = 회원_정보_조회_요청(createResponse);
        // then
        회원_정보_조회됨(findResponse, EMAIL, AGE);

        // when
        ExtractableResponse<Response> updateResponse = 회원_정보_수정_요청(createResponse, NEW_EMAIL, NEW_PASSWORD, NEW_AGE);
        // then
        회원_정보_수정됨(updateResponse);

        // when
        ExtractableResponse<Response> deleteResponse = 회원_삭제_요청(createResponse);
        // then
        회원_삭제됨(deleteResponse);
    }

    /**
     *   Scenario: 나의 정보를 관리한다.
     *     Given 나의 회원정보 등록 되어 있음
     *     When 나의 정보 조회 요청
     *     Then 나의 정보 조회됨
     *     When 나의 정보 수정 요청
     *     Then 나의 정보 수정됨
     *     When 나의 정보 삭제 요청
     *     Then 나의 정보 삭제됨
     */
    @DisplayName("나의 정보를 관리한다.")
    @Test
    void manageMyInfo() {

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
}
