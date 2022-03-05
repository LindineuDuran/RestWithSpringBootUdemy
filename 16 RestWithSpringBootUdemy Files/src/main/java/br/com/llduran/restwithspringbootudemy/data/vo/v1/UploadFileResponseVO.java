package br.com.llduran.restwithspringbootudemy.data.vo.v1;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UploadFileResponseVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;
}
