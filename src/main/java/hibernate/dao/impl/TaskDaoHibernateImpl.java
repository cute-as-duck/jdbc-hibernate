package hibernate.dao.impl;

import hibernate.dao.TaskDao;
import hibernate.model.Task;
import hibernate.model.TaskClass;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TaskDaoHibernateImpl implements TaskDao {
    private static SessionFactory factory;

    public TaskDaoHibernateImpl() {
        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        try {
            factory = new MetadataSources(serviceRegistry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Cannot get session factory");
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    @Override
    public void addTask(Task task) {
        Transaction tx = null;
        try (Session session = factory.openSession())
        {
            tx = session.beginTransaction();
            session.save(task);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getAll() {
        Transaction tx = null;
        List<Task> list = null;
        try (Session session = factory.openSession())
        {
            tx = session.beginTransaction();
            session.get(TaskClass.class, 0);

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(TaskClass.class);

            Root<TaskClass> root = cq.from(TaskClass.class);
            cq.select(root);

            Query query = session.createQuery(cq);

            list = query.getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteTask(Task task) {
        Transaction tx = null;
        try (Session session = factory.openSession())
        {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM tasks");
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
