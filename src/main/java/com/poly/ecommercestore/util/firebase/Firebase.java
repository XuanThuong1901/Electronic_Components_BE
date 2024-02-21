package com.poly.ecommercestore.util.firebase;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Firebase {
    public List<String> saveList(List<MultipartFile> fileList) throws IOException {
        List<String> listUrl = new ArrayList<>();
        for (MultipartFile item: fileList) {
            String url = save(item);

            listUrl.add(url);
        }
        return listUrl;
    }

    public static String save(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String name = UUID.randomUUID().toString() + extension;

        bucket.create(name, file.getBytes(), file.getContentType());

        return "https://storage.googleapis.com/linkkiendientu-796a8.appspot.com/".concat(name);
    }

    public void deleteList(List<String> listUrl) throws  IOException{
        for (String url: listUrl) {
            delete(url);
        }
    }

    public static void delete(String url) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        if(StringUtils.isEmpty(url)){
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(url);

        if (blob == null) {
            throw new IOException("file not found");
        }
        blob.delete();

    }
}
