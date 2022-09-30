package boarding.dto;

import lombok.Data;
@Data

public class BoardingFileDto {
private int idx;
private int boardIdx;
private String originalFileName;
private String storedFilePath;
private long fileSize;

}