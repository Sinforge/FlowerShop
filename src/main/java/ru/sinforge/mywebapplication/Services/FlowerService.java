package ru.sinforge.mywebapplication.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.Rating;
import ru.sinforge.mywebapplication.Repositories.BasketRepo;
import ru.sinforge.mywebapplication.Repositories.FlowerRepo;
import ru.sinforge.mywebapplication.Repositories.ReviewRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FlowerService {
    @Value("${upload.path}")
    private String uploadPath;

    private final BasketRepo basketRepo;
    private final ReviewRepo reviewRepo;

    private final FlowerRepo flowerRepo;


    public FlowerService(BasketRepo basketRepo, ReviewRepo reviewRepo, FlowerRepo flowerRepo) {
        this.basketRepo = basketRepo;
        this.reviewRepo = reviewRepo;
        this.flowerRepo = flowerRepo;
    }

    public void createFlower(Flower flower, MultipartFile img) throws ExecutionException, InterruptedException {
        if(!img.isEmpty()) {
            try {
                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String ImgName = img.getOriginalFilename();
                img.transferTo(new File(uploadPath + "/"+ ImgName));
                flower.setImgname(ImgName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        flowerRepo.save(flower);
    }

    public Boolean updateFlower(Flower flower) {
       Flower foundedFlower = flowerRepo.findById(flower.getId()).get();
       if(null == foundedFlower) {
           return false;
       }
       foundedFlower.setName(flower.getName());
       foundedFlower.setPrice(flower.getPrice());
       foundedFlower.setDescription(flower.getDescription());
       flowerRepo.save(foundedFlower);
       return true;
    }
    public Flower getFlower(Long flower_id) throws ExecutionException, InterruptedException {
        return flowerRepo.findById(flower_id).get();
    }

    public void deleteFlower(Long flower_id) {
        File dir = new File(uploadPath);
        File[] arrFiles = dir.listFiles();
        for(File file : arrFiles) {
            if(file.getName().contains(flowerRepo.findById(flower_id).get().getName())) {
                file.delete();
                break;
            }
        }
        flowerRepo.delete(flowerRepo.findById(flower_id).get());
    }

    public Iterable<Flower> getAllFlowers() {
        return flowerRepo.findAll();

    }



    public void addReview(Long userid, Long flowerId, short userRating) {
        Rating checkRating = reviewRepo.findByFloweridAndUserid(flowerId, userid);
        if(checkRating != null) {
            checkRating.setRating(userRating);
            reviewRepo.save(checkRating);
            return;
        }
        Rating userRatingObj = new Rating();
        userRatingObj.setRating(userRating);
        userRatingObj.setFlowerid(flowerId);
        userRatingObj.setUserid((userid));
        reviewRepo.save(userRatingObj);
    }
    public double getSummaryRating(Long flowerId) {
        ArrayList<Rating> flowerRatings = reviewRepo.findAllByFlowerid(flowerId);
        double averageRating = 0;
        for ( Rating rating: flowerRatings) {
            averageRating += rating.getRating();
        }
        return (averageRating / flowerRatings.size());
    }
    public int getUserRatingOfFlower(Long flowerId, Long userId) {
        Rating rating = reviewRepo.findByFloweridAndUserid(flowerId, userId);
        if (rating != null) {
            return rating.getRating();
        }
        return -1;
    }
}
