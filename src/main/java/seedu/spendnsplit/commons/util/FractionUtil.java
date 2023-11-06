package seedu.spendnsplit.commons.util;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.numbers.fraction.BigFraction;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Class representing precise numbers.
 */
public class FractionUtil {

    public static final String ZERO_DIVISION = "Divisor cannot be zero!";

    /**
     * Creates new fraction from decimal string.
     *
     * @param s Decimal string input in the form decimal / decimal.
     * @return Fraction created.
     */
    public static BigFraction parseFraction(String s) throws ParseException {
        String[] parts = s.replace(" ", "").split("/", -1);
        switch (parts.length) {
        case 1:
            return parseDecimal(parts[0]);
        case 2:
            BigFraction divisor = parseDecimal(parts[1]);
            if (divisor.signum() == 0) {
                throw new ParseException(ZERO_DIVISION);
            }
            return parseDecimal(parts[0]).divide(divisor);
        default:
            throw new NumberFormatException();
        }
    }

    private static BigFraction parseDecimal(String s) {
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(s.replace(" ", "").split("\\.", -1)));
        switch (parts.size()) {
        case 1:
            parts.add("");
            // no break
        case 2:
            parts.set(1, parts.get(1) + "0");
            return BigFraction.of(new BigInteger(parts.get(0) + parts.get(1)),
                    new BigInteger("1" + "0".repeat(parts.get(1).length())));
        default:
            throw new NumberFormatException();
        }
    }

    /**
     * Converts fraction to string representation.
     *
     * @param fraction The fraction to be converted.
     * @param decimalPlaces The number of decimal places of precision.
     * @return The string representation of the fraction.
     */
    public static String toString(BigFraction fraction, int decimalPlaces) {
        assert decimalPlaces >= 0;
        return fraction.bigDecimalValue(decimalPlaces, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * Returns the sum of a list of fractions.
     *
     * @param list The list of fractions to be summed.
     * @return The sum of the fractions in the list.
     */
    public static BigFraction sum(List<BigFraction> list) {
        BigFraction s = BigFraction.ZERO;
        for (BigFraction i : list) {
            s = s.add(i);
        }
        return s;
    }

}
