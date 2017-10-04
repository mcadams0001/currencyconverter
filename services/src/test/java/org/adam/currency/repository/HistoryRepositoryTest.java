package org.adam.currency.repository;

import org.adam.currency.builder.HistoryBuilder;
import org.adam.currency.domain.History;
import org.adam.currency.fixture.CurrencyFixture;
import org.adam.currency.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

class HistoryRepositoryTest extends BaseRepositoryTests {

    @Autowired
    private HistoryRepository historyRepository;

    @BeforeEach
    void setUp() throws Exception {
        initialSetup();
    }

    @Test
    void testFindBy() throws Exception {
        History h1 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 30)).withRate(1.25d).withResult(250.0d).withUser(UserFixture.TEST_USER).build();
        History h2 = new HistoryBuilder().withAmount(180.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 30)).withRate(1.25d).withResult(225.0d).withUser(UserFixture.TEST_USER).build();
        History h3 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 2)).withRate(1.4d).withResult(280.0d).withUser(UserFixture.TEST_USER).build();
        getSession().save(h1);
        getSession().save(h2);
        getSession().save(h3);
        getSession().flush();
        History history = historyRepository.findBy(CurrencyFixture.GBP, CurrencyFixture.EUR, LocalDate.of(2016, 1, 30));
        assertNotNull(history);
        assertEquals(CurrencyFixture.GBP, history.getCurrencyFrom());
        assertEquals(CurrencyFixture.EUR, history.getCurrencyTo());
        assertEquals(LocalDate.of(2016, 1, 30), history.getDate());
        assertEquals(1.25d, history.getRate(), 0.01);
    }

    @Test
    void testFindByNullForNotFound() throws Exception {
        History history = historyRepository.findBy(CurrencyFixture.GBP, CurrencyFixture.EUR, LocalDate.of(2016, 1, 30));
        assertNull(history);
    }

    @Test
    void shouldFindByUser() throws Exception {
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
        assertNotNull(historyList);
        assertEquals(3, historyList.size());
        assertEquals(h1, historyList.get(0));
        assertEquals(h2, historyList.get(1));
        assertEquals(h3, historyList.get(2));
    }

    @Test
    void shouldFindRecent() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        History h1 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(now.toLocalDate()).withRate(1.25d).withResult(250.0d).withUser(UserFixture.TEST_USER).withTimeStamp(now.minusMinutes(10)).build();
        History h2 = new HistoryBuilder().withAmount(180.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(now.toLocalDate()).withRate(1.26d).withResult(225.0d).withUser(UserFixture.TEST_USER).withTimeStamp(now.minusMinutes(50)).build();
        History h3 = new HistoryBuilder().withAmount(200.0d).withCurrencyFrom(CurrencyFixture.GBP).withCurrencyTo(CurrencyFixture.EUR).withDate(LocalDate.of(2016, 1, 2)).withRate(1.4d).withResult(280.0d).withUser(UserFixture.TEST_USER).withTimeStamp(now.minusDays(10)).build();
        getSession().save(h1);
        getSession().save(h2);
        getSession().save(h3);
        getSession().flush();
        History history = historyRepository.findRecent(CurrencyFixture.GBP, CurrencyFixture.EUR);
        assertNotNull(history);
        assertEquals(CurrencyFixture.GBP, history.getCurrencyFrom());
        assertEquals(CurrencyFixture.EUR, history.getCurrencyTo());
        assertEquals(now.toLocalDate(), history.getDate());
        assertEquals(1.25d, history.getRate(), 0.01);
    }

    @Test
    void shouldFindRecentForNull() throws Exception {
        History history = historyRepository.findRecent(CurrencyFixture.GBP, CurrencyFixture.EUR);
        assertNull(history);
    }


}