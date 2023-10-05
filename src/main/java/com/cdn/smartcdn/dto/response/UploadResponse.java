package com.cdn.smartcdn.dto.response;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.*;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class UploadResponse {

    @NonNull
    private String name;

    private String fileKey;

    private ObjectMetadata metaData;

    private String status = "UNDEFINED";

    private Date createdAt;

    private String link;

}
