package tatu.bar.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class imageController {

    @Value("${image.storage.path}")
    private String imageStoragePath;

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            if (!StringUtils.hasText(imageName)) {
                return ResponseEntity.badRequest().build();
            }

            Path imagePath = Paths.get(imageStoragePath, imageName);

            if (!Files.exists(imagePath)) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(imagePath);

            byte[] imageBytes = Files.readAllBytes(imagePath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(imageBytes);
        } catch (IOException e) {
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
