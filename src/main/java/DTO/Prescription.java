package DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private int prescriptionID;
    private int customerID;
    private int suggestionID;
    private String vitalSigns;
    private String symptom;
    private String diagnosis;
    private LocalDate prescribedDate;
    private String prescriberName;
    private List<Medicine> medicineList = new ArrayList<>();

    public Prescription() {
    }

    public Prescription(int prescriptionID, int customerID, String vitalSigns, String symptom, String diagnosis, LocalDate prescribedDate, String prescriberName) {
        this.prescriptionID = prescriptionID;
        this.customerID = customerID;
        this.vitalSigns = vitalSigns;
        this.symptom = symptom;
        this.diagnosis = diagnosis;
        this.prescribedDate = prescribedDate;
        this.prescriberName = prescriberName;
    }

    // Getters and Setters
    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    public int getsuggestionID() {
        return suggestionID;
    }

    public void setsuggestionID(int suggestionId) {
        this.suggestionID = suggestionId;
    }

    public String getVitalSigns() {
        return vitalSigns;
    }

    public void setVitalSigns(String vitalSigns) {
        this.vitalSigns = vitalSigns;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public LocalDate getPrescribedDate() {
        return prescribedDate;
    }

    public void setPrescribedDate(LocalDate prescribedDate) {
        this.prescribedDate = prescribedDate;
    }

    public String getPrescriberName() {
        return prescriberName;
    }

    public void setPrescriberName(String prescriberName) {
        this.prescriberName = prescriberName;
    }

    public List<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }
}
