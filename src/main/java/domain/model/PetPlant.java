package domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class PetPlant{
    private long pk;
    private long plantID;
    private long userID;
    private String petName;
    private LocalDate firstMetDay;
    private byte[] petImg;

    private PetPlant(Builder builder){
        this.pk = builder.pk;
        this.plantID = builder.plantID;
        this.userID = builder.userID;
        this.petName = builder.petName;
        this.firstMetDay = builder.firstMetDay;
        this.petImg = builder.petImg;
    }

    public static class Builder{
        private long pk = -1;
        private long plantID;
        private long userID;
        private String petName;
        private LocalDate firstMetDay;
        private byte[] petImg = new byte[0];

        public Builder(long plantID, long userID, String petName, LocalDate firstMetDay){
            this.plantID = plantID;
            this.userID = userID;
            this.petName = petName;
            this.firstMetDay = firstMetDay;
        }

        public Builder id(long pk){
            this.pk = pk;
            return this;
        }

        public Builder petImg(byte[] petImg){
            this.petImg = petImg;
            return this;
        }

        public PetPlant build(){
            validate();
            return new PetPlant(this);
        }

        private void validate() throws IllegalArgumentException{
            validate(petName);
        }

        private void validate(String petName){
            if(petName.length() > 5){
                throw new IllegalArgumentException("반려식물 이름은 5글자까지 가능합니다.");
            }
        }
    }

    public static Builder builder(long plantID, long userID, String petName, LocalDate firstMetDay) {
        return new Builder(plantID, userID, petName, firstMetDay);
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public boolean checkOwner(long ownerID){
        return userID==ownerID;
    }

    public void setPetImg(byte[] petImg) {
        this.petImg = petImg;
    }
}
