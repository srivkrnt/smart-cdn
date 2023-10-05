package com.cdn.smartcdn.entity;

import com.cdn.smartcdn.dto.helper.SizeAwareLink;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Document("media")
public class MediaEntity {

    private String name;
    private String provider;
    private List<SizeAwareLink> links;
}
