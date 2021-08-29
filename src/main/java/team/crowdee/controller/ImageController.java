package team.crowdee.controller;

import lombok.AllArgsConstructor;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class ImageController {

    private FileUtils fileUtils;

    @PostMapping(value = "/image")
    public ResponseEntity<?> upload(@RequestParam("upload") MultipartFile file) throws Exception {
        String imgUrl = fileUtils.uploadImage(file);
        String filename = imgUrl.substring(imgUrl.lastIndexOf("/"));
        Map<String, String> toEditor = new HashMap<>();
        toEditor.put("filename", filename);
        toEditor.put("uploaded", "1");
        toEditor.put("url", imgUrl);
        return new ResponseEntity<>(toEditor, HttpStatus.OK);
    }

    @PostMapping(value = "/thumbNail")
    public ResponseEntity<?> thumbNail(@RequestParam("file") MultipartFile file) throws Exception {
        String imgUrl = fileUtils.uploadImage(file);
        String filename = imgUrl.substring(imgUrl.lastIndexOf("/"));
        Map<String, String> toEditor = new HashMap<>();
        toEditor.put("filename", filename);
        toEditor.put("uploaded", "1");
        toEditor.put("url", imgUrl);
        return new ResponseEntity<>(toEditor, HttpStatus.OK);
    }

    @GetMapping("/image/{date}/{fileName}")
    public void image(HttpServletResponse res,
                      @PathVariable String date,
                      @PathVariable String fileName) throws IOException {
        String path = fileUtils.findImagePath() + File.separator + date + File.separator;

        OutputStream out = res.getOutputStream();
        FileInputStream fis = new FileInputStream(path + fileName);
        res.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

        FileCopyUtils.copy(fis, out);
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
