package com.KoreaIT.smw.demo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenFile {
	private int id;
	private String regDate;
	private String updateDate;
	private boolean delStatus;
	private String delDate;
	private String typeCode;
	private String type2Code;
	private String relTypeCode;
	private int relId;
	private String fileExtTypeCode;
	private String fileExtType2Code;
	private int fileSize;
	private int fileNo;
	private String fileExt;
	private String fileDir;
	private String originFileName;
	// JSON 직렬화 및 역직렬화 시 특정 필드를 무시하도록 지정할 때 사용.
	// 파일의 디렉터리 경로를 가져오는 함수
	@JsonIgnore
	public String getFilePath(String genFileDirPath) {
		return genFileDirPath + getBaseFileUri();
	}
	/* 저장된 파일 위치를 가져오는 함수 */
	@JsonIgnore
	private String getBaseFileUri() {
		return "/" + relTypeCode + "/" + fileDir + "/" + getFileName();
	}
	/* 파일 이름을 가져오는 함수 */
	public String getFileName() {
		return id + "." + fileExt;
	}
	/* 업데이트날짜를 파라미터로 받는 함수 */
	public String getForPrintUrl() {
		return "/gen" + getBaseFileUri() + "?updateDate=" + updateDate;
	}
	/* 다운로드시 파라미터로 받는 함수 */
	public String getDownloadUrl() {
		return "/common/genFile/doDownload?id=" + id;
	}
	/* 이미지를 넣을 함수 */
	public String getMediaHtml() {
		return "<img src=\"" + getForPrintUrl() + "\">";
	}
}