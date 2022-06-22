package nextstep.subway.line.domain;

import java.util.Arrays;

public enum UserCost {
    ADULT(18,0,0), TEENAGER(12,350,20), CHILD(5,350,50), FREE(0,0,100);

    private final int minAge;
    private final Charge deduction;
    private final Discount discount;

    UserCost(int minAge, final long deduction, final long discount) {
        this.minAge = minAge;
        this.deduction = new Charge(deduction);
        this.discount = new Discount(discount);
    }

    public Charge calculate(final Charge payableCharge) {
        return discount.calculate(payableCharge.minus(deduction));
    }

    public static UserCost valueOf(final int age) {
        return Arrays.stream(UserCost.values())
                .filter(it -> age > it.minAge)
                .findFirst()
                .orElse(FREE);
    }
}
