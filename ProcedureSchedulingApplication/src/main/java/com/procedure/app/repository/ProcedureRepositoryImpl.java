/**
 * 
 */
package com.procedure.app.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.procedure.app.model.Study;

/**
 * @author Pulin
 *
 * Repository Interface implementation for Procedure/Study related methods
 */
@Repository
@Transactional
public class ProcedureRepositoryImpl implements ProcedureRepository{

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
	@Override
	public List<Study> getAllProcedures() {
		
		return getSession().createQuery("from Study").list();
	}

	@Override
	public Study save(Study study) {
		
		getSession().save(study);
		return study;
	}

	@Override
	public Study findById(String id) {
		return getSession().get(Study.class, id);
	}
	
	@Override
	public Study update(Study study) {
		
		getSession().update(study);
		return study;
	}
}
