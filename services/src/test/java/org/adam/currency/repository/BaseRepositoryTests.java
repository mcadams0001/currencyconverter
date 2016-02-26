package org.adam.currency.repository;

import org.adam.currency.fixture.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"classpath:data-applicationContext.xml","classpath:h2-hibernate.xml"})
@Rollback
@Transactional
public class BaseRepositoryTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    @Qualifier("sessionFactory")
    protected SessionFactory sessionFactory;

    public void setAbstractSessionFactoryBean(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    public void setDataSource(BasicDataSource dataSource) {
        super.setDataSource(dataSource);
    }

    public void saveEntity(Object entity) {
        getSession().save(entity.getClass().getName(), entity);
    }

    public void initialSetup() {
        Logger.getLogger(SchemaExport.class).setLevel(Level.OFF);
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
