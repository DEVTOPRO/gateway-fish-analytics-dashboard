package com.fishmonitor.dashbordtool.controller;

import com.fishmonitor.dashbordtool.ExceptionHandling.FileNotFoundException;
import com.fishmonitor.dashbordtool.models.ZipFileEntity;
import com.fishmonitor.dashbordtool.repos.ZipFilerepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ZipFile {
    @Autowired
    private ZipFilerepos zipFilerepos;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile[] files) throws IOException {
        try {
            List<String> uploadedFiles = new ArrayList<>();
            for (MultipartFile file : files) {

                if (file.isEmpty()) {
                    throw new IllegalArgumentException("File is empty. Please upload a valid file.");
                }
                if (!file.getContentType().equals("application/zip") && !file.getOriginalFilename().endsWith(".zip")) {
                    throw new IllegalArgumentException("Invalid file format. Please upload a ZIP file.");
                }

                ZipFileEntity zipFileEntity = new ZipFileEntity();
                zipFileEntity.setContent(file.getBytes());
                zipFileEntity.setOriginalFilename(file.getOriginalFilename());
                uploadedFiles.add(file.getOriginalFilename());
                zipFilerepos.save(zipFileEntity);

            }
            return "Files uploaded successfully: " + uploadedFiles;
        }
        catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
        catch(Exception e){
            return "Unknown exception";
        }

    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Optional<ZipFileEntity> zipFileOptional = zipFilerepos.findById(id);
        if (zipFileOptional.isPresent()) {
            ZipFileEntity zipFileEntity = zipFileOptional.get();
            String originalFilename = "file_" + id + ".zip"; // Default if original filename is not available
            if (zipFileEntity.getOriginalFilename() != null && !zipFileEntity.getOriginalFilename().isEmpty()) {
                originalFilename = zipFileEntity.getOriginalFilename();
            }
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + zipFileEntity.getOriginalFilename())
                    .body(zipFileEntity.getContent());
        } else {
            throw new FileNotFoundException("File with ID " + id + " not found");
        }
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
