package boarding.dto;

import java.util.List;

import lombok.Data;

@Data
public class BoardingDto {
	
	private List<BoardingFileDto> fileList;
	
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createdDatetime;
	private String updaterId;
	private String updatedDatetime;
}
