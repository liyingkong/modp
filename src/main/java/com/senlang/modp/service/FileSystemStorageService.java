/**
 * 
 */
package com.senlang.modp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import com.senlang.modp.dao.UploadRepository;
import com.senlang.modp.exception.StorageException;
import com.senlang.modp.exception.StorageFileNotFoundException;
import com.senlang.modp.model.Upload;
import com.senlang.modp.utils.FetchFrameUtil;
import com.senlang.modp.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mc.D
 *
 */
@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	private UploadRepository ur;
	@Autowired
	private FetchFrameUtil fetchFrameUtil;

	@Value("${videofile-path}")
	private String videofile;
	@Value("${framefile-path}")
	private String framefile;


	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public Upload store(MultipartFile file, String type, String refCode) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}
			Upload upload = new Upload();

			upload.setType(type);
			if (!StringHelper.isNullOrEmpty(refCode)) {
				upload.setRefCode(refCode);
			}
			upload.setCode(UUID.randomUUID().toString());
			upload.setMime(file.getContentType());
			upload.setName(file.getOriginalFilename());
			upload.setUploadDate(new Date());
			upload.setPath(this.rootLocation.resolve(upload.getCode()).toString());
			String final_framefile =  framefile + upload.getCode()+".jpg";
			upload.setVideoImgPath(final_framefile);
			upload = ur.save(upload);
			Files.copy(file.getInputStream(), this.rootLocation.resolve(upload.getCode()));
			fetchFrameUtil.fetchFrame(videofile+upload.getCode(),final_framefile);
			return upload;
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

//	@Override
//	public List<Upload> batchStore(MultipartFile[] files, String type, String refCode) {
//		List<Upload> ret = new ArrayList<>();
//		for (int i = 0; i < files.length; i++) {
//			try {
//
//				MultipartFile file = files[i];
//				ret.add(store(file, type, refCode));
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			}
//		}
//		return ret;
//	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String code) {
		return rootLocation.resolve(code);
	}

	@Override
	public Resource loadAsResource(String code) {
		try {
			Path file = load(code);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + code);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + code, e);
		}
	}

	/*
	 * @Override public void deleteAll() {
	 * FileSystemUtils.deleteRecursively(rootLocation.toFile()); }
	 */

	@Override
	public boolean delete(String code) {
		return FileSystemUtils.deleteRecursively(load(code).toFile());
	}

	@Override
	public void init() {
		try {
			if (!Files.exists(rootLocation)) {
				Files.createDirectory(rootLocation);
			}
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

}
