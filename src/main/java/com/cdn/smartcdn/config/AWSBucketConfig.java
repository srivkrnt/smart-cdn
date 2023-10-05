package com.cdn.smartcdn.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AWSBucketConfig {

    @Value("${cloud.aws.storage.secretId}")
    private String secretId;

    @Value("${cloud.aws.storage.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.storage.region}")
    private String region;

    @Value("${cloud.aws.storage.endpoint}")
    private String endpoint;

    @Bean("awsStorageClient")
    public AmazonS3 getClient(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(secretId, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
