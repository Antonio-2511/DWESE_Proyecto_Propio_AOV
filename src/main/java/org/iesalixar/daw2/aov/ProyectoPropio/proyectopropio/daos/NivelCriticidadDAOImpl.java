package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.NivelCriticidad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class NivelCriticidadDAOImpl implements NivelCriticidadDAO {

    private static final Logger logger = LoggerFactory.getLogger(NivelCriticidadDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NivelCriticidadDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<NivelCriticidad> getAll() throws SQLException {
        String hql = "SELECT n FROM NivelCriticidad n";
        return entityManager.createQuery(hql, NivelCriticidad.class).getResultList();
    }

    @Override
    public NivelCriticidad getById(Long id) throws SQLException {
        return entityManager.find(NivelCriticidad.class, id);
    }

    @Override
    public boolean insert(NivelCriticidad nivel) throws SQLException {
        try {
            entityManager.persist(nivel);
            return true;
        } catch (Exception e) {
            logger.error("Error inserting NivelCriticidad", e);
            return false;
        }
    }

    @Override
    public boolean update(NivelCriticidad nivel) throws SQLException {
        try {
            entityManager.merge(nivel);
            return true;
        } catch (Exception e) {
            logger.error("Error updating NivelCriticidad", e);
            return false;
        }
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        try {
            NivelCriticidad n = entityManager.find(NivelCriticidad.class, id);
            if (n != null) {
                entityManager.remove(n);
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error deleting NivelCriticidad", e);
            return false;
        }
    }

    @Override
    public boolean existsByName(String name) throws SQLException {
        String hql = "SELECT COUNT(n) FROM NivelCriticidad n WHERE UPPER(n.nombre) = :nombre";
        Long count = entityManager.createQuery(hql, Long.class)
                .setParameter("nombre", name.toUpperCase())
                .getSingleResult();

        return count != null && count > 0;
    }
}
