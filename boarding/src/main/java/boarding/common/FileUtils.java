package boarding.common;
import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import boarding.dto.BoardingFileDto;

@Component //FileUtils클래스를 스프링의 빈으로 등록한다.
public class FileUtils{

	public List<BoardingFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
	
	if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {	
		return null;	
	}
	List<BoardingFileDto> fileList = new ArrayList<>();

	//파일이 업로드될 폴더를 생성한다.
	
	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyymmdd");
	
	ZonedDateTime current = ZonedDateTime.now();
	
	String path ="images/"+current.format(format);
	
	File file = new File(path);
	
	if(file.exists() == false) {
	
	file.mkdirs(); 

	}
	
Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

String newFileName, originalFileExtension = null,contentType; 

while(iterator.hasNext()) {

List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());

for(MultipartFile multipartFile : list) {

//파일 형식을 확인하고 그에 따라 이미지의 확장자를 지정한다. 

//실제 개발시에는 JDK1.7 이상에서 지원되는 nio 패키지를 이용하거나  아파치 티카와 같은 라이브러리를 이용.

if(multipartFile.isEmpty() == false) {

contentType = multipartFile.getContentType();

if(ObjectUtils.isEmpty(contentType)) {

break;

}else {

if(contentType.contains("image/jpeg")){

originalFileExtension = ".jpg"; 

}else if (contentType.contains("image/png")) {

originalFileExtension =".png";

}else if(contentType.contains("image/gif")) {

originalFileExtension ="gif";

}else {

break;

}

} 

//서버에 저장될 파일이름을 생성한다.

newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
//데이터베이스에 저장할 파일 정보를 앞에서 만든 BoardFileDto에 저장한다.

BoardingFileDto boardFile = new BoardingFileDto();

boardFile.setBoardIdx(boardIdx);

boardFile.setFileSize(multipartFile.getSize());

boardFile.setOriginalFileName(multipartFile.getOriginalFilename());

boardFile.setStoredFilePath(path+"/"+newFileName);

fileList.add(boardFile);

//업로드된 파일을 새로운 이름으로 바꾸어 지정된 경로에 저장한다.

file = new File(path +"/" + newFileName);

multipartFile.transferTo(file); 

}

} 

} 

return fileList; 

}

}
