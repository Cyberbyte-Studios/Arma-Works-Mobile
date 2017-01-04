package org.uk.cyberbyte.armaworks.Services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.uk.cyberbyte.armaworks.Application;
import org.uk.cyberbyte.armaworks.Models.User;

public class UserService {
    public static User saveAuthUser() {
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (currentFirebaseUser == null) {
            Log.e(Application.TAG, "Cannot save not logged in user");
            return null;
        }

        if (currentFirebaseUser.isAnonymous()) {
            Log.e(Application.TAG, "Cannot save anonymous user");
            return null;
        }
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        User currentUser = new User(currentFirebaseUser.getDisplayName(), currentFirebaseUser.getEmail());
        mDatabase.child("users").child(currentFirebaseUser.getUid()).setValue(currentUser);
        return currentUser;
    }
}
