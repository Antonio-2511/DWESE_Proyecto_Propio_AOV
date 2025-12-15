package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.Advertencia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class AdvertenciaDAOImpl implements AdvertenciaDAO {

    private static final Logger logger = LoggerFactory.getLogger(AdvertenciaDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdvertenciaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Advertencia> getAll() throws SQLException {
        String hql = "SELECT a FROM Advertencia a";
        return entityManager.createQuery(hql, Advertencia.class).getResultList();
    }

    @Override
    public Advertencia getById(Long id) throws SQLException {
        return entityManager.find(Advertencia.class, id);
    }

    @Override
    public boolean insert(Advertencia advertencia) throws SQLException {
        try {
            entityManager.persist(advertencia);
            return true;
        } catch (Exception e) {
            logger.error("Error inserting Advertencia", e);
            return false;
        }
    }

    @Override
    public boolean update(Advertencia advertencia) throws SQLException {
        try {
            entityManager.merge(advertencia);
            return true;
        } catch (Exception e) {
            logger.error("Error updating Advertencia", e);
            return false;
        }
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        try {
            Advertencia a = entityManager.find(Advertencia.class, id);
            if (a != null) {
                entityManager.remove(a);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error deleting Advertencia", e);
            return false;
        }
    }
}
