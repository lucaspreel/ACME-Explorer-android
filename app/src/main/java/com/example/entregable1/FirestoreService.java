package com.example.entregable1;

import com.example.entregable1.Entity.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreService {
    private static String userId;
    private static FirebaseFirestore mDatabase;
    private static FirestoreService service;

    public static FirestoreService getServiceInstance() {
        if (service == null || mDatabase == null) {
            mDatabase = FirebaseFirestore.getInstance();
            service = new FirestoreService();
        }
        if (userId == null || userId.isEmpty()) {
            userId = FirebaseAuth.getInstance().getCurrentUser() != null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "";
        }
        return service;
    }

    public void saveTrip (Trip trip, OnCompleteListener<DocumentReference> listener) {
        mDatabase.collection("users").document("U123").collection("trips")
                .add(trip).addOnCompleteListener(listener);
    }

    public ListenerRegistration getTrips (EventListener<QuerySnapshot> querySnapshotOnCompleteListener) {
        return mDatabase.collection("users").document("U123").collection("trips").addSnapshotListener(querySnapshotOnCompleteListener);
    }
}
