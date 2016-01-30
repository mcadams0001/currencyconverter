package org.adam.currency.repository;

import org.adam.currency.builder.HistoryBuilder;
import org.adam.currency.domain.History;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.UserFixture;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class HistoryRepositoryTest extends BaseRepositoryTests {

    @Autowired
    private HistoryRepository historyRepository;

    @Before
    public void setUp() throws Exception {
        initialSetup();
    }

    @Test
    public void testFindBy() throws Exception {
        History h1 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 30)).withRate(1.25d).withResult(250.0d).withUser(UserFixture.TEST_USER).build();
        History h2 = new HistoryBuilder().withAmount(180.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 30)).withRate(1.25d).withResult(225.0d).withUser(UserFixture.TEST_USER).build();
        History h3 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 2)).withRate(1.4d).withResult(280.0d).withUser(UserFixture.TEST_USER).build();
        getSession().save(h1);
        getSession().save(h2);
        getSession().save(h3);
        getSession().flush();
        History history = historyRepository.findBy(CurrencyFixture.GBP, CurrencyFixture.EUR, LocalDate.of(2016, 1, 30));
        assertThat(history, notNullValue());
        assertThat(history.getCurrencyFrom(), equalTo(CurrencyFixture.GBP));
        assertThat(history.getCurrencyTo(), equalTo(CurrencyFixture.EUR));
        assertThat(history.getDate(), equalTo(LocalDate.of(2016, 1, 30)));
        assertThat(history.getRate(), equalTo(1.25d));
    }

    @Test
    public void shouldFindByUser() throws Exception {
        History h1 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 30)).withRate(1.25d).withResult(250.0d).withUser(UserFixture.TEST_USER).withCreateDate(LocalDateTime.of(2016, 1, 30, 20, 0, 0)).build();
        History h2 = new HistoryBuilder().withAmount(180.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 30)).withRate(1.25d).withResult(225.0d).withUser(UserFixture.TEST_USER).withCreateDate(LocalDateTime.of(2016, 1, 30, 18, 0, 0)).build();
        History h3 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 2)).withRate(1.4d).withResult(280.0d).withUser(UserFixture.TEST_USER).withCreateDate(LocalDateTime.of(2016, 1, 25, 20, 0, 0)).build();
        History h4 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 2)).withRate(1.4d).withResult(280.0d).withUser(UserFixture.TEST_USER).withCreateDate(LocalDateTime.of(2016, 1, 11, 20, 0, 0)).build();
        History h5 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 2)).withRate(1.4d).withResult(280.0d).withUser(UserFixture.TEST_USER).withCreateDate(LocalDateTime.of(2016, 1, 2, 20, 0, 0)).build();
        History h6 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 2)).withRate(1.4d).withResult(280.0d).withUser(UserFixture.TEST_USER2).withCreateDate(LocalDateTime.of(2016, 1, 30, 20, 0, 0)).build();
        getSession().save(h6);
        getSession().save(h5);
        getSession().save(h4);
        getSession().save(h3);
        getSession().save(h2);
        getSession().save(h1);
        getSession().flush();
        List<History> historyList = historyRepository.findByUser(UserFixture.TEST_USER, 3);
        assertThat(historyList, notNullValue());
        assertThat(historyList.size(), equalTo(3));
        assertThat(historyList.get(0), equalTo(h1));
        assertThat(historyList.get(1), equalTo(h2));
        assertThat(historyList.get(2), equalTo(h3));
    }

}