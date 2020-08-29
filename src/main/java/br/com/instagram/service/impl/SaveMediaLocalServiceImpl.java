package br.com.instagram.service.impl;

import br.com.instagram.service.SaveMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Slf4j
@Primary
@Service
public class SaveMediaLocalServiceImpl implements SaveMediaService {

    @Value("${directory.image-media.name}")
    private String directoryImageName;
    @Value("${directory.video-media.name}")
    private String directoryVideoName;
    @Value("${directory.profile-media.name}")
    private String directoryProfileName;

    @Override
    public String saveImage(FilePart media, Long userId) {
        log.info("Save Image in directory!");

        try {

            Path path = FileSystems.getDefault().getPath(System.getenv("HOME"), directoryImageName);

            int i = (int) (1+Math.random() * 100000);

            String fileName = path.toString().concat("/")
                    .concat(userId.toString())
                    .concat("@")
                    .concat(Integer.toString(i))
                    .concat("-")
                    .concat("instagram-images");

            File f = new File(fileName);
            media.transferTo(f);
            log.info("Image saved!");
            return f.getAbsolutePath();
        }catch (Exception e){
            log.info("Error to save media!");
            throw new RuntimeException("Error to save Image: ", e);
        }finally {
            log.info("Finish to try save media!");
        }

    }

    @Override
    public String saveVideo(FilePart media, Long userId) {
        log.info("Save Video in directory!");

        try {

            Path path = FileSystems.getDefault().getPath(System.getenv("HOME"), directoryVideoName);

            int i = (int) (1 + Math.random() * 100000);

            String fileName = path.toString().concat("/")
                    .concat(userId.toString())
                    .concat("@")
                    .concat(Integer.toString(i))
                    .concat("-")
                    .concat("instagram-images");

            File f = new File(fileName);
            ;
            media.transferTo(f);
            log.info("Video saved!");
            return f.getAbsolutePath();
        }catch (Exception e){
            log.info("Error to save media!");
            throw new RuntimeException("Error to save Video: ", e);
        }finally {
            log.info("Finish to try save media!");
        }

    }

    @Override
    public String saveProfileImage(FilePart media, Long userId) {
        log.info("Save image Profile in directory!");

        try {

            Path path = FileSystems.getDefault().getPath(System.getenv("HOME"), directoryProfileName);

            String fileName = path.toString().concat("/")
                    .concat(userId.toString())
                    .concat("@")
                    .concat("profile-instagram");

            File f = new File(fileName);
            ;
            media.transferTo(f);
            log.info("Profile Image saved!");
            return f.getAbsolutePath();
        }catch (Exception e){
            throw new RuntimeException("Error to save image Profile: ", e);
        }finally {
            log.info("Finish to try save profile image!");
        }

    }

    @Override
    public void deleteMedia(String path) {

        File file = new File(path);
        file.delete();

        log.info("Media deleted!!");

    }


}
