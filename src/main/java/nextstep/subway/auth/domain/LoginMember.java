package nextstep.subway.auth.domain;

public class LoginMember {
    private Long id;
    private String email;
    private Integer age;

    public static LoginMember fromGuest() {
        return Guest.instance;
    }

    public LoginMember() {
        this.age = 0;
    }

    public LoginMember(Long id, String email, Integer age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    private static class Guest {
        private static final LoginMember instance = new LoginMember();
    }
}
