package br.com.llduran.controllers;

import br.com.llduran.data.vo.v1.UploadFileResponseVO;
import br.com.llduran.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Tag(name = "File Endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController
{
	private final Logger logger = Logger.getLogger(FileController.class.getName());

	@Autowired
	private FileStorageService service;

	@PostMapping("/uploadFile")
	public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file)
	{
		logger.info("Storing file to disk");

		var fileName = service.storefile(file);

		String fileDownloadUri = ServletUriComponentsBuilder
				                .fromCurrentContextPath()
				                .path("/api/file/v1/downloadFile/")
				                .path(fileName).toUriString();

		return new UploadFileResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files)
	{
		logger.info("Storing files to disk");

		return Arrays.asList(files).stream().map(f -> uploadFile(f)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{filename:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request)
	{
		logger.info("Reading a file from disk");

		Resource resource = service.loadFileAsResource(filename);
		String contentType = "";

		try
		{
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

			if(contentType.isBlank())
			{
				contentType = "application/octet-stream";
			}
		}
		catch(Exception e)
		{
			logger.info("Could not determine file type!");
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
