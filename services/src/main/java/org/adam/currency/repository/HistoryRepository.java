package org.adam.currency.repository;

import org.adam.currency.domain.Currency;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository("historyRepository")
public class HistoryRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public History findBy(Currency currencyFrom, Currency currencyTo, LocalDate date) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(History.class);
        criteria.add(Restrictions.eq("currencyFrom", currencyFrom));
        criteria.add(Restrictions.eq("currencyTo", currencyTo));
        criteria.add(Restrictions.eq("date", date));
        criteria.addOrder(Order.desc("createDate"));
        List<History> list = criteria.list();
        return !list.isEmpty() ? list.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public History findRecent(Currency currencyFrom, Currency currencyTo) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(History.class);
        criteria.add(Restrictions.eq("currencyFrom", currencyFrom));
        criteria.add(Restrictions.eq("currencyTo", currencyTo));
        criteria.add(Restrictions.gt("timeStamp", LocalDateTime.now().minusMinutes(55)));
        criteria.addOrder(Order.desc("createDate"));
        criteria.setMaxResults(1);
        List<History> list = criteria.list();
        return !list.isEmpty() ? list.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public List<History> findByUser(User user, int limit) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(History.class);
        criteria.add(Restrictions.eq("user", user));
        criteria.addOrder(Order.desc("createDate"));
        criteria.setMaxResults(limit);
        return criteria.list();
    }
}
