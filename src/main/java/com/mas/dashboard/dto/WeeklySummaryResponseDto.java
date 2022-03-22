package com.mas.dashboard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Data
public class WeeklySummaryResponseDto implements MultipartFile {

    private Long articleId;

    private byte[] summaryResponse;

    private Boolean completed;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }
}
