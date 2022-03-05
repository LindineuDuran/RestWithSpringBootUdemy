package br.com.llduran.restwithspringbootudemy.controller;

import br.com.llduran.restwithspringbootudemy.data.vo.v1.UploadFileResponseVO;
import br.com.llduran.restwithspringbootudemy.services.FileStorageService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags="FileEndpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController
{
	private static Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/uploadFile")
	public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file)
	{
		String fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/file/v1/downloadFile/")
				.path(fileName)
				.toUriString();

		return UploadFileResponseVO
				.builder()
				.fileName(fileName)
				.fileDownloadUri(fileDownloadUri)
				.fileType(file.getContentType())
				.size(file.getSize())
				.build();
	}
	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files)
	{
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
	{
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		String contentType = null;

		try
		{
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}
		catch (Exception e)
		{
			logger.info("Could not determine file type");
		}

		if(contentType == null)
		{
			contentType = "application/octet-stream";
		}

		return ResponseEntity
				.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attchment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
