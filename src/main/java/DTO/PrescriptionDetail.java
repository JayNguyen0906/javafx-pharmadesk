package DTO;

public class PrescriptionDetail {
    private int prescriptionId;
    private int itemId;
    private String dosage;
    private String note;

    // Constructor
    public PrescriptionDetail(int prescriptionId, int itemId, String dosage, String note) {
        this.prescriptionId = prescriptionId;
        this.itemId = itemId;
        this.dosage = dosage;
        this.note = note;
    }

    // Getters & Setters
    public int getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(int prescriptionId) { this.prescriptionId = prescriptionId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

}
