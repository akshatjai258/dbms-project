package com.project.elearningwebapp.controllers;

import java.io.File;
import java.io.IOException;

import static java.lang.Math.min;

import com.project.elearningwebapp.dao.TopicDAO;
import com.project.elearningwebapp.models.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

@Controller
public class VideoStreamController {
    @Autowired
    private TopicDAO topicDAO;

    @Value("${app.upload.dir}")
    private String volumePath;


    private static final long CHUNK_SIZE = 1000000L;


    @GetMapping(value = "/course/lecture/{topicId}", produces = "application/octet-stream")
    public ResponseEntity<ResourceRegion> getVideo(@RequestHeader(value = "Range", required = false) String rangeHeader, @PathVariable("topicId") int topicId, Model model)
            throws IOException {


        if (!new File(volumePath ).exists()) {
            new File(volumePath).mkdirs();
        }
        Topic topic = topicDAO.get(topicId);
        String videoPath = topic.getVideoPath();

        return getVideoRegion(rangeHeader, videoPath);
    }

    public ResponseEntity<ResourceRegion> getVideoRegion(String rangeHeader, String videoPath) throws IOException {
        FileUrlResource videoResource = new FileUrlResource(volumePath + videoPath);
        ResourceRegion resourceRegion = getResourceRegion(videoResource, rangeHeader);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(videoResource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);
    }

    private ResourceRegion getResourceRegion(UrlResource video, String httpHeaders) throws IOException {
        ResourceRegion resourceRegion = null;

        long contentLength = video.contentLength();
        int fromRange = 0;
        int toRange = 0;
        if (StringUtils.isNotBlank(httpHeaders)) {
            String[] ranges = httpHeaders.substring("bytes=".length()).split("-");
            fromRange = Integer.valueOf(ranges[0]);
            if (ranges.length > 1) {
                toRange = Integer.valueOf(ranges[1]);
            } else {
                toRange = (int) (contentLength - 1);
            }
        }

        if (fromRange > 0) {
            long rangeLength = min(CHUNK_SIZE, toRange - fromRange + 1);
            resourceRegion = new ResourceRegion(video, fromRange, rangeLength);
        } else {
            long rangeLength = min(CHUNK_SIZE, contentLength);
            resourceRegion = new ResourceRegion(video, 0, rangeLength);
        }

        return resourceRegion;
    }

}
