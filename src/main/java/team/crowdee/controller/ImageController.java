package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.crowdee.util.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private FileUtils fileUtils;

    @PostMapping("/image")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws Exception {

        String imgUrl = fileUtils.parseCKEditorImgPath(file);

//        String path = "/Users/jangtaehwan/work/project/crowdee/src/main/resources/file/";
//        log.info("파일 이름={}", file.getOriginalFilename());
//        file.transferTo(new File(path + file.getOriginalFilename()));
//        File file1 = new File(path + file.getOriginalFilename());

        return new ResponseEntity<>(imgUrl, HttpStatus.OK);
    }

    @GetMapping("/image/{date}/{fileName}")
    public void image(HttpServletResponse response,
                      @PathVariable String date,
                      @PathVariable String fileName) throws IOException {
        String path = fileUtils.findImagePath()+File.separator+date+File.separator;
//
//        String filename = req.getPathInfo().substring(1);
//        File file = new File(getInitParameter("images.path"), filename);
//
//        if (file.exists()) {
//            res.setHeader("Content-Type", getServletContext().getMimeType(filename));
//            res.setHeader("Content-Length", String.valueOf(file.length()));
//            res.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
//            Files.copy(file.toPath(), res.getOutputStream());

        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(path+fileName);
            FileCopyUtils.copy(fis, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            out.flush();
        }
    }
}
