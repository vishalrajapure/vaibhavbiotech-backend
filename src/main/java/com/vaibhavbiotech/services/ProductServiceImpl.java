package com.vaibhavbiotech.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vaibhavbiotech.models.ClientSequence;
import com.vaibhavbiotech.models.DeleteModel;
import com.vaibhavbiotech.models.Product;
import com.vaibhavbiotech.repository.ClientSequenceRepository;
import com.vaibhavbiotech.repository.ProductRepository;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientSequenceRepository clientSequenceRepository;

    @Autowired
    private AmazonS3 s3client;

    @Value("${jsa.s3.bucket}")
    private String bucketName;


    @Override
    public Product addProductToDb(Product product) {
        Product storedProduct = productRepository.save(product);
        return storedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product productRequest) {
        Product productFromDb = productRepository.getById(productRequest.getId());
        productFromDb.setProductName(productRequest.getProductName());
        productFromDb.setDescription(productRequest.getDescription());
        productFromDb.setPrice(productRequest.getPrice());
        productFromDb.setImageLink(productRequest.getImageLink());
        productFromDb.setPlantType(productRequest.getPlantType());
        productFromDb.setShowOnHomePage(productRequest.isShowOnHomePage());
        return productRepository.save(productFromDb);
    }

    public Long uploadImageViaFTP(MultipartFile file, String extension) {
        String server = "ftp.vaibhavbiotech.com";
        int port = 21;
        String user = "u620014590.vbimages";
        String pass = "Vaibhavbiotech@123";

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            InputStream inputStream = file.getInputStream();

            List<ClientSequence> clientSequenceList = clientSequenceRepository.findAll();
            ClientSequence clientSequence = clientSequenceList.get(0);
            String updatedFileName = clientSequence.getNext_val() + extension;

            OutputStream outputStream = ftpClient.storeFileStream(updatedFileName);
            byte[] bytesIn = new byte[8192];
            int read = 0;
            System.out.println("Started uploading image at " + Calendar.getInstance().getTime());
            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }

            inputStream.close();
            outputStream.close();
            boolean completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("Image uploaded successfully at " + Calendar.getInstance().getTime());
                return clientSequence.getNext_val();
            }


        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            return -1L;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return -1L;
            }
        }
        return -1L;
    }

    public Long uploadImageToS3(MultipartFile multipartFile, String extension) {

        List<ClientSequence> clientSequenceList = clientSequenceRepository.findAll();
        ClientSequence clientSequence = clientSequenceList.get(0);
        String updatedFileName = clientSequence.getNext_val() + extension;

        try {
            File file = new File(updatedFileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, updatedFileName, file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            s3client.putObject(putObjectRequest);
            file.delete();
            System.out.println("===================== Upload File - Done! =====================");

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException | IOException ace) {
            System.out.println("Caught an AmazonClientException: ");
            System.out.println("Error Message: " + ace.getMessage());
        }

        return clientSequence.getNext_val();
    }

    @Override
    public boolean deleteProduct(long id) {
        //TODO : delete image from s3 & db
        Product product = productRepository.findById(id).get();
        productRepository.deleteById(id);
        deleteFileFromS3Bucket(product.getImageLink());
        return true;
    }


    @Async
    public void deleteFileFromS3Bucket(String imageLink) {
        String[] imgLinkTokens = imageLink.split("/");
        String fileName = imgLinkTokens[imgLinkTokens.length - 1];
        try {
            s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        } catch (AmazonServiceException ex) {
            System.out.println("Error occurred in spring boot while deleting image from s3");
        }
    }

    @Override
    public List<Product> getLatestProducts() {
        List<Product> latestProducts = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            if (product.isShowOnHomePage())
                latestProducts.add(product);
        });
        return latestProducts;
    }
}
