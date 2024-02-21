package com.poly.ecommercestore.service.product;

import com.poly.ecommercestore.model.request.PriceListRequest;
import com.poly.ecommercestore.entity.*;
import com.poly.ecommercestore.model.request.ProductRequest;
import com.poly.ecommercestore.repository.*;
import com.poly.ecommercestore.model.request.SpecificationRequest;
import com.poly.ecommercestore.util.extractToken.IExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final IExtractToken iExtractToken;
    private final PriceListService priceListService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageProductService imageProductService;
    private final SpecificationService specificationService;
    private final SupplierRepository supplierRepository;
    private final TaxRepository taxRepository;

    private static final String UPLOAD_DIR = "D:\\NAM4-HK3\\THUCTAPTOTNGHIEP\\ecommercestore-web-customer\\src\\assets\\images\\product";

    @Transactional
    @Override
    public int addProduct(ProductRequest request, List<MultipartFile> imageProduct, String tokenHeader) {
        try {
            Employers employer = iExtractToken.extractEmployee(tokenHeader);
            Categories category = categoryRepository.findById(request.getCategory()).get();

            Suppliers supplier = supplierRepository.findById(request.getSupplier()).get();

//            Products pd = productRepository.checkProduct(supplier, product.getProductName());
//            if(pd != null){
//                return 2;
//            }
            boolean checkProduct = productRepository.existsByProductName(request.getProductName());
            if (checkProduct == true){
                return 2;
            }

            Tax tax = taxRepository.findById(request.getTax()).get();

////            return 2;
//            int check = 1;
//            if(check == 1)
//                return 2;

            Products newProduct = new Products();
            newProduct.setProductName(request.getProductName());
            newProduct.setCategory(category);
            newProduct.setSupplier(supplier);
            newProduct.setTax(tax);
            newProduct.setQuantity(request.getQuantity());
            newProduct.setFeature(request.getFeature());
            newProduct.setContents(request.getContents());
            newProduct.setGuarantee(request.getGuarantee());

            newProduct = productRepository.save(newProduct);
            List<ImageProducts> imageProducts = new ArrayList<>();
            for (MultipartFile image : imageProduct){
                String imageUrl = saveImage(image);
                System.out.println(imageUrl);
                ImageProducts imageProductTemp = imageProductService.add(imageUrl, newProduct.getIDProduct());
                imageProducts.add(imageProductTemp);
            }

            List<Specifications> specifications = new ArrayList<>();
            for (SpecificationRequest specification : request.getSpecification()){
                System.out.println(specification);
                Specifications specificationTemp = specificationService.add(specification, newProduct.getIDProduct());
                specifications.add(specificationTemp);
            }

            List<PriceLists> priceLists = new ArrayList<>();
            for (PriceListRequest priceListDTO : request.getPriceList()){
                PriceLists priceListsTemp = priceListService.addPriceList(priceListDTO, employer.getIDEmployer(), newProduct.getIDProduct());
                priceLists.add(priceListsTemp);
            }

            newProduct.setPriceLists(priceLists);
            newProduct.setImageProducts(imageProducts);
            newProduct.setSpecifications(specifications);

            productRepository.save(newProduct);


            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public boolean updateProduct(String tokenHeader, int iDProduct, ProductRequest request, List<MultipartFile> imageProduct) {

        Employers employer = iExtractToken.extractEmployee(tokenHeader);
        if(employer == null)
        {
            return false;
        }

        Products productUpdate = productRepository.findById(iDProduct).get();
        if(productUpdate == null)
        {
            return false;
        }

        Categories category = categoryRepository.findById(request.getCategory()).get();
        if(category == null){
            return false;
        }

        Suppliers supplier = supplierRepository.findById(request.getSupplier()).get();
        if(supplier == null){
            return false;
        }

        Tax tax = taxRepository.findById(request.getTax()).get();
        if(tax == null){
            return false;
        }


        productUpdate.setProductName(request.getProductName());
        productUpdate.setCategory(category);
        productUpdate.setSupplier(supplier);
        productUpdate.setTax(tax);
        productUpdate.setQuantity(request.getQuantity());
        productUpdate.setFeature(request.getFeature());
        productUpdate.setContents(request.getContents());
        productUpdate.setGuarantee(request.getGuarantee());

        List<ImageProducts> imageProducts = new ArrayList<>();
        for (MultipartFile image : imageProduct){
            String imageUrl = saveImage(image);
            ImageProducts imageProductTemp = imageProductService.add(imageUrl, iDProduct);
            imageProducts.add(imageProductTemp);
        }
        List<Specifications> specifications = new ArrayList<>();
        for (SpecificationRequest specification : request.getSpecification()){
            Specifications specificationTemp = specificationService.add(specification,iDProduct);
            specifications.add(specificationTemp);
        }
        System.out.printf("2222\n");
        List<PriceLists> priceLists = new ArrayList<>();
        for (PriceListRequest priceListDTO : request.getPriceList()){
            PriceLists priceListsTemp = priceListService.addPriceList(priceListDTO, employer.getIDEmployer(),iDProduct);
            priceLists.add(priceListsTemp);
        }
        System.out.printf("3333\n");
        productUpdate.setPriceLists(priceLists);
        productUpdate.setImageProducts(imageProducts);
        productUpdate.setSpecifications(specifications);

        productRepository.save(productUpdate);

        return true;
    }

    @Override
    public boolean removeProduct(int iDProduct) {

        Products product = productRepository.getReferenceById(iDProduct);

        if(product == null)
            return false;

        productRepository.delete(product);
        return true;
    }

    @Override
    public Products getProductById(int iDProduct) {
        return productRepository.findById(iDProduct).get();
    }

    @Override
    public List<Products> getProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Products> getProductByPage(int page, int size) {
        int offset = (page-1) * size;
        Pageable pageable = PageRequest.of(page, size);
        List<Products> productList = productRepository.findByPage(offset, size);

        for(Products product : productList){
            List<PriceLists> price = new ArrayList<>();
            for(PriceLists item : product.getPriceLists()){
                if(item.getStatus() == true && item.getType().equals("export"))
                {
                    price.add(item);
                    break;
                }
            }
            product.setPriceLists(price);
        }
        return productList;
    }

    @Override
    public List<Products> getProductByCategoryByPage(int iDCategory, int page, int size) {
        int offset = (page-1) * size;
        List<Products> productList = productRepository.findByCategoryByPage(iDCategory, offset, size);

        for(Products product : productList){
            List<PriceLists> price = new ArrayList<>();
            for(PriceLists item : product.getPriceLists()){
                if(item.getStatus() == true && item.getType().equals("export"))
                {
                    price.add(item);
                    break;
                }
            }
            product.setPriceLists(price);
        }
        return productList;
    }

    private String saveImage(MultipartFile image) {
        try {
            String urlImage = imageProductService.save(image);
            System.out.println(urlImage);
//            String fileName = image.getOriginalFilename();
//            Path filePath = Paths.get(UPLOAD_DIR, fileName);
//            Files.write(filePath, image.getBytes());
            return urlImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private MultipartFile convertPathToMultipartFile(String filePath) {
//        try {
//            Path path = Paths.get(filePath);
//            String fileName = path.getFileName().toString();
//            String contentType = Files.probeContentType(path);
//
//            byte[] content = Files.readAllBytes(path);
//
//            return new MockMultipartFile(fileName, fileName, contentType, content);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
