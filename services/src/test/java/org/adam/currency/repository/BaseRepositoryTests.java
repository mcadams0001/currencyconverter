package org.adam.currency.repository;

import org.adam.currency.fixture.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:data-applicationContext.xml", "classpath:h2-hibernate.xml"})
@Rollback
@Transactional
public class BaseRepositoryTests {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    JdbcTemplate jdbcTemplate;

    void setAbstractSessionFactoryBean(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    void setDataSource(BasicDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    void saveEntity(Object entity) {
        getSession().save(entity.getClass().getName(), entity);
    }

    void initialSetup() {
        Session session = getSession();

        SettingFixture.SETTINGS.forEach(session::save);
        CountryFixture.COUNTRIES.forEach(session::save);
        CurrencyFixture.CURRENCIES.forEach(session::save);
        RoleFixture.ROLES.forEach(session::save);
        AddressFixture.ADDRESSES.forEach(session::save);
        UserFixture.USERS.forEach(session::save);
        session.flush();
    }
}
