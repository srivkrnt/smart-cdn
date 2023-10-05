package com.cdn.smartcdn.dto.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class SizeAwareLink {
    Integer height;
    Integer width;
    Long size;
    String link;
    Boolean isPrimary;
    String fileKey;
}
