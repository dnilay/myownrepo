package org.example;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.Copy;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;

/*
 @author Dev Problems
 104857600 bytes - 100mb
 */

public class App {
    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.US_EAST_1)
            .build();
    TransferManager tm = null;
    void uploadMultipartFile(String bucketName, String keyName, String filePath) {
        try {
            tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .withMultipartUploadThreshold((long) (104857600))
                    .withMinimumUploadPartSize((long) (104857600))
                    .build();
            Upload upload = tm.upload(bucketName, keyName, new File(filePath));
            System.out.println("Object upload started");
            System.out.println(tm.getConfiguration().getMultipartCopyPartSize());
            System.out.println(upload.getState().toString());
            while(!upload.isDone())
            {
                System.out.println(upload.getDescription().toString());

                System.out.println("Transferred-->  "+upload.getProgress().getBytesTransferred());

            }
         //   upload.waitForCompletion();
            System.out.println("Object upload complete");
            tm.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void copyMultipartFile(String fromBucket, String fromKeyName, String toBucket, String toKeyName) {
        try {
            TransferManager  tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .withMultipartCopyThreshold((long) (104857600))
                    .withMultipartCopyPartSize((long) (104857600))
                    .build();
            Copy copy = tm.copy(fromBucket, fromKeyName, toBucket, toKeyName);
            System.out.println("Object copy started");
            copy.waitForCompletion();
            System.out.println("Object copy completed");
            tm.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new App().uploadMultipartFile("spring-boot-s3-bucket-nd-065", "sts", "/home/nilay/Downloads/spring-tool-suite-4-4.18.1.RELEASE-e4.27.0-linux.gtk.x86_64.tar.gz");
        //new App().copyMultipartFile("source-bucket-name", "source-key-name", "destination-bucket-name", "destination-key-name");
    }
}
