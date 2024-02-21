package com.poly.ecommercestore.service.product;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.poly.ecommercestore.entity.ImageProducts;
import com.poly.ecommercestore.entity.Products;
import com.poly.ecommercestore.repository.ImageProductRepository;
import com.poly.ecommercestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ImageProductService implements IImageProductService{

    @Autowired
    private ImageProductRepository imageProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ImageProducts> getByProduct(int iDProduct) {
        return imageProductRepository.findByProduct(iDProduct);
    }

    @Override
    public ImageProducts add(String image, int iDProduct) {
        if(image.equals(""))
            return null;

        try {
            Products product = productRepository.findById(iDProduct).get();
            ImageProducts imageProduct = imageProductRepository.findByURL(image);

            if(product == null || imageProduct != null)
                return null;
            ImageProducts newImageProduct = new ImageProducts(image, product);

            return imageProductRepository.save(newImageProduct);
        }
        catch (Exception e){
            System.out.printf(e.toString());
            return null;
        }
    }

    @Override
    public Boolean delete(int idProduct) {
        return false;
    }

    @Override
    public String getImageUrl(String name) {
        return "https://storage.googleapis.com/linkkiendientu-796a8.appspot.com/".concat(name);
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Tạo một số ngẫu nhiên dưới dạng UUID và ghép vào tên tệp
        String name = UUID.randomUUID().toString() + extension;

        bucket.create(name, file.getBytes(), file.getContentType());

        return "https://storage.googleapis.com/linkkiendientu-796a8.appspot.com/".concat(name);
    }

    @Override
    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {
        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(originalFileName);

        bucket.create(name, bytes);

        return name;
    }

    @Override
    public void delete(String name) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new IOException("file not found");
        }

        blob.delete();
    }
}
