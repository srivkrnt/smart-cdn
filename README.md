# Smart CDN

Smart CDN is a customized Content Delivery Network (CDN) service built using Spring Boot. It is designed to optimize website performance by efficiently delivering images and reducing image load times. This CDN service automatically stores images on AWS S3 and resizes them based on the dimensions requested by the image requester.

## Features

- **Efficient image delivery:** Smart CDN optimizes the delivery of images, reducing latency and enhancing website performance.
- **Dynamic resizing:** The service automatically resizes images to the requested dimensions, eliminating the need for multiple versions of the same image.
- **AWS S3 Integration:** All images are securely stored on AWS S3, ensuring reliability and scalability.
- **Customization:** Smart CDN can be easily customized to meet your specific requirements and integrate with your existing Spring Boot projects.

## Getting Started

To use Smart CDN in your Spring Boot project, follow these steps:

1. Clone the Smart CDN repository: `git clone https://github.com/yourusername/smart-cdn.git`
2. Import the project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
3. Configure AWS credentials in `application.properties` or `application.yml`. Make sure you have the necessary AWS permissions to access S3.
4. Build and run the project.

```bash
mvn clean install
mvn spring-boot:run
```

## Configuration
You can customize the behavior of Smart CDN by modifying the application.properties or application.yml file. Here are some of the key configuration options:

- aws.accessKey and aws.secretKey: Set your AWS access and secret keys for S3 access.
- aws.s3.bucketName: Specify the name of the AWS S3 bucket where images will be stored.

## Usage

### Uploading Images
You can also upload images to Smart CDN using HTTP requests. Here's an example curl command to upload an image:
```bash
curl --location 'http://localhost:8081/upload/?provider=AWS' \
--form 'name="tester"' \
--form 'file=@"/Users/vikrant.srivastava/Pictures/file.png"'

```

### Retrieving Images

To use Smart CDN to retrieve images with the desired dimensions, you can make HTTP requests. Here's an example `curl` command to retrieve an image:

```bash
curl --location 'http://localhost:8081/media/tester?height=200&width=200'
```

Remember to replace http://localhost:8081 with your actual service URL and adjust file paths as needed.
