package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class FractionUtilTest {

    @Test
    public void parseFraction_quarterTwo_quarter() {
        assertEquals("0.25", FractionUtil.toString(FractionUtil.parseFraction("0.25"), 2));
    }

    @Test
    public void parseFraction_quarterOne_pointThree() {
        assertEquals("0.3", FractionUtil.toString(FractionUtil.parseFraction("0.25"), 1));
    }

    @Test
    public void sum_halfQuarter_threeQuarters() {
        assertEquals("0.75", FractionUtil.toString(FractionUtil
                .sum(List.of(FractionUtil.parseFraction("0.25"), FractionUtil.parseFraction("0.5"))), 2));
    }
}
