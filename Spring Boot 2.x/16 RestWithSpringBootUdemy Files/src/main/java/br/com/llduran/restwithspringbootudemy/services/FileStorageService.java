package br.com.llduran.restwithspringbootudemy.services;

import br.com.llduran.restwithspringbootudemy.config.FileStorageConfig;
import br.com.llduran.restwithspringbootudemy.exception.FileStorageException;
import br.com.llduran.restwithspringbootudemy.exception.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService
{
	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig)
	{
		this.fileStorageLocation = Paths
				.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath()
				.normalize();

		try
		{
			Files.createDirectories((this.fileStorageLocation));
		}
		catch (IOException e)
		{
			throw new FileStorageException("Could not create the directory where the uploaded files will be storaged!", e);
		}
	}

	public String storeFile(MultipartFile file)
	{
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try
		{
			if(fileName.contains(".."))
			{
				throw new FileStorageException("Sorry! Filename contains invalide path sequence " + fileName);
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		}
		catch (Exception e)
		{
			throw new FileStorageException("Could not store file " + fileName + ". Please, try again!", e);
		}
	}

	public Resource loadFileAsResource(String fileName)
	{
		try
		{
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

			Resource resource = new UrlResource(filePath.toUri());

			if(resource.exists())
			{
				return resource;
			}
			else
			{
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		}
		catch(Exception e)
		{
			throw new MyFileNotFoundException("File not found " + fileName, e);
		}
	}
}
