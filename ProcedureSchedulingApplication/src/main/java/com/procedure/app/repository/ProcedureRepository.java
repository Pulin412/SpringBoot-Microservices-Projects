/**
 * 
 */
package com.procedure.app.repository;

import java.util.List;

import com.procedure.app.model.Study;

/**
 * @author Pulin
 *
 * Repository Interface for Procedure/Study related methods
 */
public interface ProcedureRepository {

	public List<Study> getAllProcedures();

	public Study save(Study study);

	public Study findById(String id);
	
	public Study update(Study study);

}
