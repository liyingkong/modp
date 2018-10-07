/**
 * 
 */
package com.senlang.modp.api;


import com.senlang.modp.dao.UploadRepository;
import com.senlang.modp.exception.StorageFileNotFoundException;
import com.senlang.modp.model.CommonResult;
import com.senlang.modp.model.Upload;
import com.senlang.modp.service.JsonResultFactory;
import com.senlang.modp.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Mc.D
 *
 */
@RestController
@RequestMapping("/api/files")
public class FileUploadController {
	private final StorageService storageService;

	@Autowired
	private UploadRepository ur;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	/*
	 * @GetMapping("/") public String listUploadedFiles(Model model) throws
	 * IOException {
	 * 
	 * model.addAttribute("files", storageService .loadAll() .map(path ->
	 * MvcUriComponentsBuilder .fromMethodName(FileUploadController.class,
	 * "serveFile", path.getFileName().toString()) .build().toString())
	 * .collect(Collectors.toList()));
	 * 
	 * return "uploadForm"; }
	 */

	@RequestMapping(value = "/image", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getImage(@RequestParam String filename) throws IOException {
		File file = new File(ur.findByCode(filename).getVideoImgPath());
		FileInputStream inputStream = new FileInputStream(file);
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes, 0, inputStream.available());
		return bytes;
	}

	@GetMapping("/list")
	public CommonResult serveFile(@RequestParam String type, @RequestParam String refCode) {

		return JsonResultFactory.getOkDataResult(this.ur.findByTypeAndRefCode(type, refCode));
	}

	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Upload upload = ur.findByCode(filename);
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + upload.getName() + "\"")
				.body(file);
	}

	@GetMapping("/delete/{filename:.+}")
	@ResponseBody
	public CommonResult deleteFile(@PathVariable String filename) {

		if (storageService.delete(filename)) {
			ur.deleteByCode(filename);
			return JsonResultFactory.getOkCommonResult();
		}

		return JsonResultFactory.getCommonResult(3);
	}

	@PostMapping("/upload")
	public CommonResult handleFileUpload(@RequestParam("file") MultipartFile file,
										 @RequestParam(required = false) String type, @RequestParam(required = false) String refCode) {

		Upload u = storageService.store(file, type, refCode);
		return JsonResultFactory.getOkDataResult(u);
	}

//	@PostMapping("/batch-upload")
//	public CommonResult handleBatchFileUpload(@RequestParam("file") MultipartFile[] files,
//			@RequestParam(required = false) String type, @RequestParam(required = false) String refCode) {
//
//		List<Upload> uploads = storageService.batchStore(files, type, refCode);
//		return JsonResultFactory.getOkDataResult(uploads);
//	}

	// @PostMapping("/update-ref-id")
	// public CommonResult handleFileUpload(@RequestParam String type,
	// @RequestParam long oldId,
	// @RequestParam long newId) {
	// List<Upload> u = ur.findByTypeAndRefId(type, oldId);
	// u.forEach(v -> v.setRefId(newId));
	// return JsonResultFactory.getOkDataResult(ur.save(u));
	// }

	@ExceptionHandler(StorageFileNotFoundException.class)
	public CommonResult handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return JsonResultFactory.getCommonResult(404);
	}

}
