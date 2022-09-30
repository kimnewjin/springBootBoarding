package boarding.service;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import boarding.common.FileUtils;
import boarding.dto.BoardingDto;
import boarding.dto.BoardingFileDto;
import boarding.mapper.BoardingMapper;

@Service
//비지니스 로직을 처리하는 서비스 클래스를 나태내는 어노테이션이다.
@Transactional
public class BoardingServiceImpl implements BoardingService{

@Autowired
private BoardingMapper boardMapper;
@Autowired
private FileUtils fileUtils;
//데이터 베이스에 접근하는 DAO 빈을 선언한다. 

private Logger log = LoggerFactory.getLogger(this.getClass());
@Override
public List<BoardingDto> selectBoardList() throws Exception{
	return boardMapper.selectBoardList(); //사용자 요청을 처리하기 위한 비지니스 로직 구현

}

@Override
public BoardingFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception{
	return boardMapper.selectBoardFileInformation(idx,boardIdx);
}



@Override

public void insertBoard(BoardingDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{

	boardMapper.insertBoard(board); //업로드된 파일 정보 확인하는 목적에는 잠시 주석처리 
	//FileUtils클래스이용해 파일을 서버에 저장/파일의 정보를 가져옴
	//board.getBoardIdx(),multipartHttpServletRequest
	List<BoardingFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(),multipartHttpServletRequest);

		//파일정보를 Map에 저장
		
		if(CollectionUtils.isEmpty(list) == false){
		
			boardMapper.insertBoardFileList(list);
		
		}

		if(ObjectUtils.isEmpty(multipartHttpServletRequest)  == false) {

				Iterator<String> iterator = multipartHttpServletRequest.getFileNames(); 

				// 서버에 한꺼번에 전송되는  한개 이상의 파일 태그 이름을 이터레이터 형식으로 가져올 수 있다
				//이터레이터를 이용해 파일 태크 이름을 하나씩 가져오면 해당 파일 태그에서 전송된 파일 구분이 가능하다.

				String name;

				while (iterator.hasNext()) {

					name = iterator.next();
					
					log.debug("file tag name :"+name);
					
					List<MultipartFile> list1 = multipartHttpServletRequest.getFiles(name);
					
					// multple 형태로 받아오기 때문에 여러개일 수 있음으로 리스트 형태로 받아온다.
					
					for(MultipartFile multipartFile : list1) {
					
					//boardMapper.insertBoardFileList(list1);
					
					log.debug("start file information");
					
					log.debug("file name:"+ multipartFile.getOriginalFilename());
					
					log.debug("file size : " + multipartFile.getSize());
					
					log.debug("file content type:"+multipartFile.getContentType());
					
					log.debug("end file information.\n");

				}

}

}

}

@Override

public BoardingDto selectBoardDetail(int boardIdx) throws Exception{
	BoardingDto board = boardMapper.selectBoardDetail(boardIdx);
	List<BoardingFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
	board.setFileList(fileList);
	boardMapper.updateHitCount(boardIdx);
	return board;

}

@Override

public void updateBoard(BoardingDto board) throws Exception{

boardMapper.updateBoard(board);

}

@Override
public void deleteBoard(int boardIdx) throws Exception{
boardMapper.deleteBoard(boardIdx);
}



}