package com.dcu.test.security;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    public String imageSave(MultipartFile image) throws IOException {
        // 상대 경로로 지정 (src/main/resources/static/images/ 디렉토리)
        Path uploadPath = Paths.get("src/main/resources/static/images/");  // 상대 경로로 설정
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);  // 디렉토리가 없으면 생성
        }

        // 파일 이름에 UUID를 추가하여 중복 방지
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);  // 실제 저장할 파일 경로

        // 파일 저장
        Files.copy(image.getInputStream(), filePath);

        // 저장된 이미지 경로 반환 (웹에서 접근 가능한 경로)
        return "/upload/images/" + fileName;
    }

    public void fileDelete(String file) throws IOException {
        Files.deleteIfExists(Paths.get("./upload/images").resolve(file));
    }
}
