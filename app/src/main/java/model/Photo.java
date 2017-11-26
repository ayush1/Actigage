package model;

/**
 * Created by ayushgarg on 25/11/17.
 */

public class Photo {

    public String id;
    public String farm;
    public String secret;
    public String server;

    public String getId() {
        return id;
    }

    public Photo setId(String id) {
        this.id = id;
        return this;
    }

    public String getFarm() {
        return farm;
    }

    public Photo setFarm(String farm) {
        this.farm = farm;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public Photo setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public String getServer() {
        return server;
    }

    public Photo setServer(String server) {
        this.server = server;
        return this;
    }
}
