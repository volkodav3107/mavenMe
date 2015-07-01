package org.improve.nsk.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.improve.nsk.domain.Product;
import org.improve.nsk.domain.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by smu8 on 01.07.2015.
 */
@Service("productService")
@Transactional
public class ProductService {

    protected static Logger logger = Logger.getLogger("service");

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List<Product> getAll() {
        logger.debug("return All");

        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Product ");

        return query.list();
    }

    public Product get(Integer id) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from Product as p where p.id=" + id);

        return (Product) query.uniqueResult();
    }

    public void add(Product product) {
        logger.debug("Add");

        Session session = sessionFactory.getCurrentSession();

        session.save(product);
    }

    public void edit(Product product) {
        logger.debug("Editing existing person");

        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing person via id
        Product existingPerson = (Product) session.get(Product.class, product.getId());

        // Assign updated values to this person
        existingPerson.setCatId(product.getCatId());
        existingPerson.setName(product.getName());
        existingPerson.setPrice(product.getPrice());

        // Save updates
        session.save(existingPerson);
    }
}
