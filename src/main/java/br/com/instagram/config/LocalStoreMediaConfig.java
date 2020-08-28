package br.com.instagram.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component
public class LocalStoreMediaConfig {

    @Value("${directory.image-media.name}")
    private String directoryImageName;
    @Value("${directory.video-media.name}")
    private String directoryVideoName;
    @Value("${directory.profile-media.name}")
    private String directoryProfileName;

    @Bean
    public void createDirectoryForMedia(){

        try {
            Path imagePath = FileSystems.getDefault().getPath(System.getenv("HOME"), directoryImageName);
            Files.createDirectories(imagePath);
            Path videoPath = FileSystems.getDefault().getPath(System.getenv("HOME"), directoryVideoName);
            Files.createDirectories(videoPath);
            Path profileImagePath = FileSystems.getDefault().getPath(System.getenv("HOME"), directoryProfileName);
            Files.createDirectories(profileImagePath);


            log.info("Create directory about medias");
        }catch (IOException e ){
            e.printStackTrace();
            log.error("Problem to create directory: ", e);
        }

    }

}
