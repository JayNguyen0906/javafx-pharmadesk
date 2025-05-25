package BUS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DAO.Symptom_DAO;
import DTO.Symptom;

public class Symptom_BUS {

    private Symptom_DAO symptomDAO;

    public Symptom_BUS() {
        symptomDAO = new Symptom_DAO();
    }

    public List<Symptom> getAllSuggestions() throws SQLException {
        return symptomDAO.getAllSuggestions();
    }

    public boolean addSuggestion(Symptom symptom) throws SQLException {
        return symptomDAO.addSuggestion(symptom);
    }

    public boolean deleteSuggestion(int id) throws SQLException {
        return symptomDAO.deleteSuggestion(id);
    }

    public boolean updateSuggestion(Symptom symptom) throws SQLException {
        return symptomDAO.updateSuggestion(symptom);
    }

    public Symptom getSuggestionById(int id) throws SQLException {
        return symptomDAO.getSuggestionById(id);
    }
    
    public List<Symptom> getSuggestionsBySymptom(String symptomKeyword) throws SQLException {
        return symptomDAO.getSuggestionsBySymptom(symptomKeyword);
    }
    
    public List<Symptom> getSuggestionsByMultiSymptoms(String input) throws SQLException {
        String[] keywords = input.split(",");
        Set<Integer> seenItemIds = new HashSet<>();
        List<Symptom> result = new ArrayList<>();

        for (String keyword : keywords) {
            keyword = keyword.trim();
            List<Symptom> list = getSuggestionsBySymptom(keyword);
            for (Symptom s : list) {
                if (seenItemIds.add(s.getItemId())) {
                    result.add(s);
                }
            }
        }
        return result;
    }

}
