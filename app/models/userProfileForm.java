package models;

import javax.persistence.Entity;

@Entity
public class userProfileForm {

    public String events;

    public userProfileForm(String events){
        this.events = events;
    }

    public userProfileForm(){
        this.events = null;
    }

}
