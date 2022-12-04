package org.example.saltfish.service;

import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @author saltfish
 */
@Service
public class MinioServicempl {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    public String bucketName;

    /**
     * 判断bucket是否存在
     * @param name
     */
    public boolean existBucket(String name) {
        boolean exist = false;
        try {
            exist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exist;
    }


    /**
     * 创建库并返回是否创建成功
     * @param name 库名
     * @return
     */

    public boolean makeBucket(String name){
        boolean res = false;
        try {
             minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
             res = existBucket(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     *  删除存储块
     * @param bucketName
     * @return
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     *  上传->文件存储的增加业务
     * @param username
     * @param CarCard
     * @param file
     * @return
     */

    public String upload(String username,String CarCard,MultipartFile file) {
        String[] Ches  = file.getName().split("\\.");


        String filename = username+"/"+CarCard+"."+Ches[Ches.length-1];
        try {
            InputStream inputStream = file.getInputStream();
            // 上传到minio服务器
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(filename)
                    .stream(inputStream, -1L, 10485760L)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    /**
     *
     * @param bucket
     * @param fileName
     * @param Load_Path
     */
    public void Download(String bucket,String fileName,String Load_Path) {
        try {
            String[] aa = fileName.split("/");
            String New_Path = aa[aa.length-1];
            minioClient.getObject(bucket, fileName, Load_Path+"/"+New_Path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void Delete(String bucket,String filename){
        try {
            minioClient.removeObject(bucket, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }










}
