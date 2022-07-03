package nextstep.subway.member;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.AcceptanceTest;
import nextstep.subway.auth.dto.TokenResponse;
import nextstep.subway.member.dto.MemberRequest;
import nextstep.subway.member.dto.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static nextstep.subway.auth.acceptance.AuthAcceptanceTest.로그인_요청;
import static nextstep.subway.member.MemberRequest.*;
import static nextstep.subway.member.MyInfoRequest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MemberAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final String NEW_EMAIL = "newemail@email.com";
    public static final String NEW_PASSWORD = "newpassword";
    public static final int AGE = 20;
    public static final int NEW_AGE = 21;

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

    @DisplayName("나의 정보를 관리한다.")
    @Test
    void manageMyInfo() {
        // given
        ExtractableResponse<Response> createResponse = 회원_생성을_요청(EMAIL, PASSWORD, AGE);
        회원_생성됨(createResponse);

        ExtractableResponse<Response> loginResponse = 로그인_요청(EMAIL, PASSWORD);
        String token = loginResponse.as(TokenResponse.class).getAccessToken();

        // when 내 정보 조회
        ExtractableResponse<Response> findMemberResult = 내_정보_조회_요청(token);

        MemberResponse findMemberResponse = findMemberResult.as(MemberResponse.class);
        assertNotNull(findMemberResponse.getId());
        assertEquals(EMAIL, findMemberResponse.getEmail());
        assertEquals(AGE, findMemberResponse.getAge());

        // when 내 정보 수정
        final String NEW_EMAIL = "newemail@email.com";
        final String NEW_PASSWORD = "newPassword";
        final Integer NEW_AGE = 100;

        ExtractableResponse<Response> updateResult = 내_정보_수정_요청(token, NEW_EMAIL, NEW_PASSWORD, NEW_AGE);
        assertEquals(HttpStatus.OK.value(), updateResult.statusCode());

        // when 내 정보 삭제
        ExtractableResponse<Response> deleteResult = 내_정보_삭제_요청(token);
        assertEquals(HttpStatus.NO_CONTENT.value(), deleteResult.statusCode());
    }

}
