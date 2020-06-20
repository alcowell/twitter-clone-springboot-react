package com.example.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Image;
import com.example.demo.exception.FileStorageException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ImageRepository;

/**
 * @author yosuk
 *
 */
@Service
public class ImageService {

	@Autowired
	private ImageRepository imageRepository;

	public Image storeImage(MultipartFile file) {

		//Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			//Check if the file's name contains invalid characters
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			Image image = new Image(fileName, file.getContentType(), file.getBytes());
			return imageRepository.save(image);

		}catch (IOException e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
	}

	public Image getImage(String id) {
		return imageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Image", "id", id));
	}
}
