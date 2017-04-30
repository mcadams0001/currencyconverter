package org.adam.currency.repository;

import org.adam.currency.domain.Currency;
import org.adam.currency.domain.History;
import org.adam.currency.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository("historyRepository")
public class HistoryRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public History findBy(Currency currencyFrom, Currency currencyTo, LocalDate date) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<History> query = criteriaBuilder.createQuery(History.class);
        Root<History> root = query.from(History.class);
        query.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("currencyFrom"), currencyFrom),
                criteriaBuilder.equal(root.get("currencyTo"), currencyTo),
                criteriaBuilder.equal(root.get("date"), date)
        ));
        query.orderBy(criteriaBuilder.desc(root.get("createDate")));
        List<History> list = sessionFactory.getCurrentSession().createQuery(query).getResultList();
        return !list.isEmpty() ? list.get(0) : null;
    }

    public History findRecent(Currency currencyFrom, Currency currencyTo) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<History> query = criteriaBuilder.createQuery(History.class);
        Root<History> root = query.from(History.class);
        query.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("currencyFrom"), currencyFrom),
                criteriaBuilder.equal(root.get("currencyTo"), currencyTo),
                criteriaBuilder.greaterThan(root.get("timeStamp"), LocalDateTime.now().minusMinutes(55))
        ));
        query.orderBy(criteriaBuilder.desc(root.get("timeStamp")));
        List<History> list = sessionFactory.getCurrentSession().createQuery(query).getResultList();
        return !list.isEmpty() ? list.get(0) : null;
    }

    public List<History> findByUser(User user, int limit) {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<History> query = criteriaBuilder.createQuery(History.class);
        Root<History> root = query.from(History.class);
        query.where(criteriaBuilder.equal(root.get("user"), user));
        query.orderBy(criteriaBuilder.desc(root.get("createDate")));
        List<History> list = sessionFactory.getCurrentSession().createQuery(query).getResultList();
        return list.subList(0, limit);
    }
}
