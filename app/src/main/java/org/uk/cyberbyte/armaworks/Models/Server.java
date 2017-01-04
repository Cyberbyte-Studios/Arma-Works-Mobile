package org.uk.cyberbyte.armaworks.Models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Server {
    public String username;
    public String email;

    public Server() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Server(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
