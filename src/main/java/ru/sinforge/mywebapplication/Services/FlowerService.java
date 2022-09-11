package ru.sinforge.mywebapplication.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.protobuf.Api;
import org.springframework.stereotype.Service;
import ru.sinforge.mywebapplication.Entities.Flower;

import java.util.concurrent.ExecutionException;

@Service
public class FlowerService {
    public String createFlower(Flower flower) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("Flower").document(flower.getName()).set(flower);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String updateFlower(Flower flower) {
        return "";
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
}
