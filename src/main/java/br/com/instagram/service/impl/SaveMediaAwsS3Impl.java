package br.com.instagram.service.impl;

import br.com.instagram.service.SaveMediaService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

@Slf4j
//@Primary
@Service
public class SaveMediaAwsS3Impl implements SaveMediaService {

    @Value("${aws-cloud-s3-bucket-image.value}")
    private String bucketImage;

    @Value("${aws-cloud-s3-bucket-video.value}")
    private String bucketVideo;

    @Value("${aws-cloud-s3-bucket-profile.value}")
    private String bucketProfile;

    @Value("${aws-cloud-s3-region-code.value}")
    private String region;

    //@Autowired
    private AmazonS3 amazonS3;

    @Override
    public String saveImage(FilePart media, Long userId) {

        int i = (int) (1+Math.random() * 100000);

        String fileName = userId.toString()
                .concat("@")
                .concat(Integer.toString(i))
                .concat("-")
                .concat("instagram-images");

        Path pathTemp = Path.of(media.filename());
        media.transferTo(pathTemp);

        try {
            File initialFile = new File(String.valueOf(pathTemp));
            InputStream targetStream = new FileInputStream(initialFile);

            amazonS3.putObject(new PutObjectRequest(bucketImage,fileName,targetStream, null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return "https://s3.".concat(region)
                    .concat(".amazonaws.com/")
                    .concat(bucketImage)
                    .concat("/")
                    .concat(fileName);

        }catch (Exception e) {
            throw new RuntimeException("Problem when send image to S3!! ",e);
        }finally {
            File file = new File(String.valueOf(pathTemp));
            file.delete();
        }
    }

    @Override
    public String saveVideo(FilePart media, Long userId) {
        int i = (int) (1+Math.random() * 100000);

        String fileName = userId.toString()
                .concat("@")
                .concat(Integer.toString(i))
                .concat("-")
                .concat("instagram-video");

        Path pathTemp = Path.of(media.filename());
        media.transferTo(pathTemp);

        try {
            File initialFile = new File(media.filename());
            InputStream targetStream = new FileInputStream(initialFile);

            amazonS3.putObject(new PutObjectRequest(bucketVideo,fileName,targetStream, null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return "https://s3.".concat(region)
                    .concat(".amazonaws.com/")
                    .concat(bucketVideo)
                    .concat("/")
                    .concat(fileName);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Problem when send video to S3!! Error:  ",e);
        }finally {
            File file = new File(String.valueOf(pathTemp));
            file.delete();
        }
    }


    @Override
    public String saveProfileImage(FilePart media, Long userId) {

        String fileName = userId.toString()
                .concat("@")
                .concat("profile-instagram");

        Path pathTemp = Path.of(media.filename());
        media.transferTo(pathTemp);

        try {
            File initialFile = new File(media.filename());
            InputStream targetStream = new FileInputStream(initialFile);

            amazonS3.putObject(new PutObjectRequest(bucketImage,fileName,targetStream, null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return "https://s3.".concat(region)
                    .concat(".amazonaws.com/")
                    .concat(bucketImage)
                    .concat("/")
                    .concat(fileName);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Problem when send profile image to S3!! Error:  ",e);
        }finally {
            File file = new File(String.valueOf(pathTemp));
            file.delete();
        }
    }

    @Override
    public void deleteMedia(String path) {

        String[] split = path.split("/");

        String namePathMedia = split[4];

        if (path.contains("profile-instagram")){
            amazonS3.deleteObject(bucketProfile, namePathMedia);
        }
        else if (path.contains("instagram-video")){
            amazonS3.deleteObject(bucketVideo, namePathMedia);
        }
        else if (path.contains("instagram-images")){
            amazonS3.deleteObject(bucketImage, namePathMedia);
        }
    }
}
