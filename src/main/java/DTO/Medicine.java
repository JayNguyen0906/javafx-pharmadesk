package DTO;

public class Medicine {
    private String name;
    private String dosage;
    private String note;

    public Medicine(String name, String dosage, String note) {
        this.name = name;
        this.dosage = dosage;
        this.note = note;
    }

    public Medicine() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
