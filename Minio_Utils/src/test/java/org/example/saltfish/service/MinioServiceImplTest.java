package org.example.saltfish.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
@SpringBootTest
class MinioServiceImplTest {
    @Resource
    MinioServiceImpl m;
    @Test
    void existBucket() {

        System.out.println(m.existBucket("aaa"));
        System.out.println(m.removeBucket("aaa"));
        System.out.println(m.existBucket("aaa"));
    }

    @Test
    void testExistBucket() {
    }

    @Test
    void makeBucket() {
    }

    @Test
    void removeBucket() {
    }

    public FileItem getMultipartFile(File file, String fieldName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = (FileItem) factory.createItem(fieldName, ContentType.APPLICATION_OCTET_STREAM.toString(), true, file.getName());
        int bytesRead = 0;
        int len = 8192;
        byte[] buffer = new byte[len];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os =  item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Test
    void upload() {
        File f = new File("/home/lyq/图片/123.jpeg");
        FileItem fileItem = getMultipartFile(f, "123.jpg");
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        System.out.println(m.addVehicleFile("aaa","222",multipartFile));
    }


    @Test
    void download(){
        m.Download("aaa","aaa/111.jpg","/opt");
    }
    @Test
    void del(){
        m.deleteVehicleFile("aaa","aaa/111.jpg");
    }

}