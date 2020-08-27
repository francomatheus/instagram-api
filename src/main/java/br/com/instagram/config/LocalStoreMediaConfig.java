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

    @Value("${directory.media.name}")
    private String directoryName;

    @Bean
    public void createDirectoryForMedia(){

        try {
            Path path = FileSystems.getDefault().getPath(System.getenv("HOME"), "instagram-media");
            Files.createDirectories(path);
            log.info("Create directory about images");
        }catch (IOException e ){
            e.printStackTrace();
            log.error("Problem to create directory: ", e);
        }

    }

}
