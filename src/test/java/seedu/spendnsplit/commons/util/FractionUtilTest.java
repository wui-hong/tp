package seedu.spendnsplit.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class FractionUtilTest {

    @Test
    public void parseFraction() throws Exception {
        assertEquals("0.00", FractionUtil.toString(FractionUtil.parseFraction("0"), 2));
        assertEquals("0.00", FractionUtil.toString(FractionUtil.parseFraction("."), 2));
        assertEquals("0.50", FractionUtil.toString(FractionUtil.parseFraction("1.0/2"), 2));
        assertEquals("0.25", FractionUtil.toString(FractionUtil.parseFraction("0.25"), 2));
        assertEquals("0.3", FractionUtil.toString(FractionUtil.parseFraction("0.25"), 1));
    }

    @Test
    public void compare() throws Exception {
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("1"), FractionUtil.parseFraction("1")) == 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("1"), FractionUtil.parseFraction("2")) < 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("2"), FractionUtil.parseFraction("1")) > 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("-1"), FractionUtil.parseFraction("-1")) == 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("-1"), FractionUtil.parseFraction("-2")) > 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("-2"), FractionUtil.parseFraction("-1")) < 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("0"), FractionUtil.parseFraction("0")) == 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("0"), FractionUtil.parseFraction("1")) < 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("1"), FractionUtil.parseFraction("0")) > 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("0"), FractionUtil.parseFraction("-1")) > 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("-1"), FractionUtil.parseFraction("0")) < 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("1"), FractionUtil.parseFraction("-1")) > 0);
        assertTrue(FractionUtil.compare(FractionUtil.parseFraction("-1"), FractionUtil.parseFraction("1")) < 0);
    }

    @Test
    public void sum_halfQuarter_threeQuarters() throws Exception {
        assertEquals("0.75", FractionUtil.toString(FractionUtil
                .sum(List.of(FractionUtil.parseFraction("0.25"), FractionUtil.parseFraction("0.5"))), 2));
    }
}
