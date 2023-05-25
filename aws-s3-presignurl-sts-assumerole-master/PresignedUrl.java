public class PresignedUrl {

    static AmazonS3 client = AmazonS3ClientBuilder.standard().build();

    private Date getExpirationTime(long time) {
        Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += time;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    private String generatePresignedForGet(String bucketName, String key) {
        URL url = client.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, key).withExpiration(getExpirationTime(12000)).withMethod(HttpMethod.GET));
        return url.toString();
    }

    private String generatePresignedForPut(String bucketName, String key) {
        URL url = client.generatePresignedUrl(new GeneratePresignedUrlRequest(bucketName, key).withExpiration(getExpirationTime(12000)).withMethod(HttpMethod.PUT));
        return url.toString();
    }

    public static void main(String[] args) {
    }
}