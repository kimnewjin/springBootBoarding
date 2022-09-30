package boarding.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import boarding.dto.BoardingDto;
import boarding.dto.BoardingFileDto;

@Mapper //마이바티스의 매퍼 인터페이스임을 선언함
public interface BoardingMapper {

	List<BoardingDto> selectBoardList() throws Exception; // 인터페이스이기 때문에 메서드의 이름과 반호나 형식만
//지정한다. 여기서 나오는 메서드이 이름은 SQL이름과 동일해야한다.
	void insertBoard(BoardingDto board) throws Exception;
	
	void updateHitCount(int boardIdx) throws Exception;
	
	BoardingDto selectBoardDetail(int boardIdx) throws Exception;
	
	void updateBoard(BoardingDto board) throws Exception;
	void deleteBoard(int boardIdx) throws Exception;
	void insertBoardFileList(List<BoardingFileDto> list) throws Exception;
	List<BoardingFileDto> selectBoardFileList(int boardIdx) throws Exception;
	
	BoardingFileDto selectBoardFileInformation(@Param("idx") int idx, @Param("boardIdx") int boardIdx);

}