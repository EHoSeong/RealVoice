package com.example.RealVoice_Backend.Firebase;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

public class FirebaseAuthenticationService {
	public static void main(String[] args) throws IOException {
		initializeFirebaseApp();

		// 데이터 추가
		addDataToFirebase();
	}

	private static void initializeFirebaseApp() {

		try {
			FileInputStream serviceAccount = new FileInputStream(
					"C:\\RealVoice_STS\\firebase\\realvoice-7d654-firebase-adminsdk-lmptf-0300d84c0d.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

			FirebaseApp.initializeApp(options);

			System.out.println("Firebase initialized successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addDataToFirebase() {
		// Firebase 데이터베이스 참조
		try {
			DatabaseReference databaseReference = FirebaseDatabase
					.getInstance("https://realvoice-7d654-default-rtdb.firebaseio.com/").getReference();

			// 데이터 추가
			databaseReference.addChildEventListener(new ChildEventListener() {

				@Override
				public void onChildRemoved(DataSnapshot snapshot) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
					// TODO Auto-generated method stub
					System.out.println("aadd");
					System.out.println("aadd");
				}

				@Override
				public void onCancelled(DatabaseError error) {
					// TODO Auto-generated method stub

				}
			});
			String data = "test Success";
			JsonObject test = new JsonObject();
			test.addProperty("key", "check");
			test.addProperty("value", "SUccess");
			
			databaseReference.child("users").child("userId1").removeValue(null);
			databaseReference.child("one").child("db").setValue(test.toString(), new CompletionListener() {
				@Override
				public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
					if (databaseError == null) {
						System.out.println("에러없음!!!");
					} else {
						System.err.println(
								"Data could not be added to Firebase Realtime Database: " + databaseError.getMessage());
					}
				}
			});

			databaseReference.setValueAsync("Async!!");
			System.out.println("Data added to Firebase Realtime Database successfully");

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}