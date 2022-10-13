package ru.sinforge.mywebapplication.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.protobuf.Api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.sinforge.mywebapplication.Entities.Flower;
import ru.sinforge.mywebapplication.Entities.FlowerBouquet;
import ru.sinforge.mywebapplication.Entities.Rating;
import ru.sinforge.mywebapplication.Entities.User;
import ru.sinforge.mywebapplication.Repositories.BasketRepo;
import ru.sinforge.mywebapplication.Repositories.ReviewRepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;

@Service
public class FlowerService {
    @Value("${upload.path}")
    private String uploadPath;

    private final BasketRepo basketRepo;
    private final ReviewRepo reviewRepo;


    public FlowerService(BasketRepo basketRepo, ReviewRepo reviewRepo) {
        this.basketRepo = basketRepo;
        this.reviewRepo = reviewRepo;
    }

    public Boolean createFlower(Flower flower, MultipartFile img) throws ExecutionException, InterruptedException {
        flower.setId(UUID.randomUUID().toString());
        if(!img.isEmpty()) {
            try {
                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String ImgName = flower.getId() + "." + img.getOriginalFilename();
                img.transferTo(new File(uploadPath + "/"+ ImgName));
                flower.setImgName(ImgName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<Flower> flowers =  getAllFlowers();
        for (Flower fl: flowers) {
            if(fl.getName().equalsIgnoreCase(flower.getName())) {
                return false;
            }
        }
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("Flower").document(flower.getId()).set(flower);
        return true;
    }

    public Boolean updateFlower(Flower flower) {
        HashMap<String, Object> update = new HashMap<>();
        update.put("id", flower.getId());
        update.put("name", flower.getName());
        update.put("description", flower.getDescription());
        update.put("price", flower.getPrice());
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResultApiFuture =
                dbFirestore.collection("Flower").document(flower.getId()).set(update, SetOptions.merge());
        return writeResultApiFuture.isDone();

    }
    public Flower getFlower(String flower_id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("Flower").document(flower_id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Flower flower;
        if(document.exists()) {
            flower = document.toObject(Flower.class);
            return flower;
        }
        return null;
    }
    public String deleteFlower(String flower_id) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("Flower").document(flower_id).delete();
        return "Flower" + flower_id + " was successfully deleted";

    }

    public Iterable<Flower> getAllFlowers() {
        List<Flower> flowers = new ArrayList<Flower>();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> apiFuture = dbFirestore.collection("Flower").get();
        List<QueryDocumentSnapshot> documents = null;

        try {
            documents = apiFuture.get().getDocuments();
            for(QueryDocumentSnapshot document : documents) {
                flowers.add(document.toObject(Flower.class));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return flowers;

    }

    public Iterable<Flower> getFlowerBySearch(String Pattern) {
        List<Flower> flowers = new ArrayList<Flower>();
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> apiFuture = dbFirestore.collection("Flower").whereEqualTo("name", Pattern).get();
        List<QueryDocumentSnapshot> documents = null;

        try {
            documents = apiFuture.get().getDocuments();
            for(QueryDocumentSnapshot document : documents) {
                flowers.add(document.toObject(Flower.class));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return flowers;
    }

    public Iterable<Flower> SortFlowerByPrice(int min, int max, Iterable<Flower> flowers) {
        ArrayList<Flower> sortedFlowers = new ArrayList<>();
        for (Flower flower: flowers) {
            if(flower.getPrice() <= max && flower.getPrice() >= min) {
                sortedFlowers.add(flower);
            }
        }
        return sortedFlowers;
    }

    public void addReview(Long userid, String flowerId, short userRating) {
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
    public double getSummaryRating(String flowerId) {
        ArrayList<Rating> flowerRatings = reviewRepo.findAllByFlowerid(flowerId);
        double averageRating = 0;
        for ( Rating rating: flowerRatings) {
            averageRating += rating.getRating();
        }
        return (averageRating / flowerRatings.size());
    }
    public int getUserRatingOfFlower(String flowerId, Long userId) {
        Rating rating = reviewRepo.findByFloweridAndUserid(flowerId, userId);
        if (rating != null) {
            return rating.getRating();
        }
        return -1;
    }
}
