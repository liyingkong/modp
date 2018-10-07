/**
 * 
 */
package com.senlang.modp.dao;

import com.senlang.modp.model.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author Mc.D
 *
 */
public interface UploadRepository extends JpaRepository<Upload, Long> {
	Upload findByCode(String code);
	Upload findByCutVideoPath(String cutVideoPath);

	@Transactional
	long deleteByCode(String code);

	List<Upload> findByTypeAndRefCode(String type, String refCode);
}
