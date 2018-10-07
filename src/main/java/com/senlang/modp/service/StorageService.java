/**
 * 
 */
package com.senlang.modp.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.senlang.modp.model.Upload;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mc.D
 *
 */
public interface StorageService {
	void init();

	Upload store(MultipartFile file, String type, String refCode);

//	List<Upload> batchStore(MultipartFile[] files, String type, String refCode);

	Stream<Path> loadAll();

	Path load(String code);

	Resource loadAsResource(String code);

	boolean delete(String code);

}
