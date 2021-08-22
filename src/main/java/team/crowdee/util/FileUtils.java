package team.crowdee.util;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class FileUtils {

    Environment env;

    // 단일 이미지 파일 업로드
    public String uploadImage(MultipartFile uploadImg) throws Exception {

        String storePath = "";
        String filePath = "";
        String result = env.getProperty("images.hostUrl");
        String originalName = uploadImg.getOriginalFilename();
        String originalFileExtension = originalName.substring(originalName.lastIndexOf("."));

        try {

            //일자별로 폴더를 만들어서 넣는다.
            DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            String strDate = fmt.format(new Date());

            //디스크의 물리적인 경로
            storePath = env.getProperty("images.path") + File.separator + strDate + File.separator;

            File saveFolder = new File(storePath);
            if (!saveFolder.exists() || saveFolder.isFile()) {
                saveFolder.mkdirs();
            }
            //한글 파일명 변경
            String newFileName = UUID.randomUUID().toString().replaceAll("-","") + originalFileExtension;

            filePath = storePath + newFileName;

            File file = new File(filePath);
            //실제 파일이 저장되는 순간
            uploadImg.transferTo(file);
            result = result + strDate + File.separator + newFileName;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public void deleteFile(String fileStreCours, String streFileNm) throws Exception {

        String path = fileStreCours + streFileNm;
        try {

            File f = new File(path);
            if (f.isFile()) {
                f.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findImagePath() {
        return env.getProperty("images.path");
    }

}
