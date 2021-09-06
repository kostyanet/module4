package dao;

import entity.RaceReport;
import entity.RaceStat;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class RaceDao {
    public void createReport(RaceReport report) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(report);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<RaceStat> getStats() {
        Transaction transaction = null;
        List<RaceStat> stats = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "SELECT id, bet_horse_result, horses_total FROM race_report";
            Query query = session.createSQLQuery(sql).addEntity(RaceStat.class);
            stats = query.getResultList();
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return stats;
    }

    public RaceReport getById(int id) {
        Transaction transaction = null;
        RaceReport report = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM RaceReport r WHERE r.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            List results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                report = (RaceReport) results.get(0);
            }
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return report;
    }
}
