package br.com.instagram.service;

import org.springframework.http.codec.multipart.FilePart;

public  interface SaveMediaService {

    String saveImage(FilePart media, Long userId);

    String saveVideo(FilePart media, Long userId);

    String saveProfileImage(FilePart media, Long userId);

}
