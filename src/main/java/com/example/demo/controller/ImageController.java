package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.Image;
import com.example.demo.payload.UploadImageResponse;
import com.example.demo.service.ImageService;

/**
 * @author yosuk
 *
 */
@RestController
@RequestMapping("/api")
public class ImageController {

	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private ImageService imageService;

	@PostMapping("/upload")
	public UploadImageResponse uploadImage(@RequestParam("file") MultipartFile file) {
		Image image = imageService.storeImage(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/downloadFile/")
				.path(image.getId())
				.toUriString();

		return new UploadImageResponse(image.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

//	@PostMapping("/uploadMulti")
//	public List<>

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId){
		Image image = imageService.getImage(fileId);

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(image.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + image.getFileName())
				.body(new ByteArrayResource(image.getData()));
	}
}
