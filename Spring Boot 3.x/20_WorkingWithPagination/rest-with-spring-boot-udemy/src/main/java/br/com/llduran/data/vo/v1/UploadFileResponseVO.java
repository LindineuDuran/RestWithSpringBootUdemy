package br.com.llduran.data.vo.v1;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadFileResponseVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;

	public UploadFileResponseVO() {}

	public UploadFileResponseVO(final String fileName, final String fileDownloadUri, final String fileType, final long size)
	{
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}
}
