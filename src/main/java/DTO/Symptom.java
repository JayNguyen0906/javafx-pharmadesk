package DTO;

public class Symptom {
    private int suggestionId;
    private String symptom;
    private int itemId;
    private String itemName; 
    private String recommendedDosage;
    private String note;

    public Symptom() {}

    public Symptom(int suggestionId, String symptom, int itemId, String recommendedDosage, String note) {
        this.suggestionId = suggestionId;
        this.symptom = symptom;
        this.itemId = itemId;
        this.recommendedDosage = recommendedDosage;
        this.note = note;
    }
    
    public Symptom(int suggestionId, String symptom, int itemId, String itemName, String recommendedDosage, String note) {
        this.suggestionId = suggestionId;
        this.symptom = symptom;
        this.itemId = itemId;
        this.itemName = itemName;
        this.recommendedDosage = recommendedDosage;
        this.note = note;
    }
    
    public int getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    public String getItemName() {
    	return itemName;
    }
    
    public void setItemName(String itemName) {
    	this.itemName = itemName;
    }

    public String getRecommendedDosage() {
        return recommendedDosage;
    }

    public void setRecommendedDosage(String recommendedDosage) {
        this.recommendedDosage = recommendedDosage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
