package org.adam.currency.fixture;

import org.adam.currency.builder.CountryBuilder;
import org.adam.currency.domain.Country;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CountryFixture {
    public static final Country AUSTRALIA = new CountryBuilder().withName("Australia").withCode("AUS").build();
    public static final Country GERMANY = new CountryBuilder().withName("Germany").withCode("DEU").build();
    public static final Country HUNGARY = new CountryBuilder().withName("Hungary").withCode("HUN").build();
    public static final Country JAPAN = new CountryBuilder().withName("Japan").withCode("JPN").build();
    public static final Country NEWZEALND = new CountryBuilder().withName("New Zealand").withCode("NZL").build();
    public static final Country UK = new CountryBuilder().withName("United Kingdom").withCode("GBR").withPostCodeRegExp("(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKPSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY])))) [0-9][A-Z-[CIKMOV]]{2})").build();
    public static final Country US = new CountryBuilder().withName("United States").withCode("USA").withPostCodeRegExp("^[0-9]{5}(?:-[0-9]{4})?$").build();

    //Countries are sorted by name in the collection.
    public static final List<Country> COUNTRIES = Collections.unmodifiableList(Arrays.asList(AUSTRALIA, GERMANY, HUNGARY, JAPAN, NEWZEALND, UK, US));
}
