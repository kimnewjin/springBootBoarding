package boarding.service;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import boarding.dto.BoardingDto;
import boarding.dto.BoardingFileDto;

//비지니스로직을 수행하기 위한 메서들 정의한다.

public interface BoardingService {
	List<BoardingDto> selectBoardList() throws Exception;
	void insertBoard(BoardingDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
	BoardingDto selectBoardDetail(int boardIdx) throws Exception;
	
	void updateBoard(BoardingDto board) throws Exception;
	void deleteBoard(int boardIDx) throws Exception;
	
	BoardingFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
}