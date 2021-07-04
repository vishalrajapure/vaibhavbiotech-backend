package com.vaibhavbiotech.services;

import com.vaibhavbiotech.models.ClientSequence;
import com.vaibhavbiotech.models.Product;
import com.vaibhavbiotech.repository.ClientSequenceRepository;
import com.vaibhavbiotech.repository.ProductRepository;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientSequenceRepository clientSequenceRepository;


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
        String user = "u620014590.vaibhavbiotech";
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

    @Override
    public String deleteProduct(long id) {
        productRepository.deleteById(id);
        return "product deleted";
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
