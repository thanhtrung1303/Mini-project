package vn.techmaster.jobhunt.repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.jobhunt.model.Employer;

@Repository
public class EmployerRepository {
    ConcurrentHashMap<String, Employer> employers;

    String logoPath = "upload/employer_logo";

    public EmployerRepository() {
        employers = new ConcurrentHashMap<>();
        employers.put("employer1",
                new Employer("employer1", "FPT", "fpt.png", "http://fpt.com.vn",
                        "fpt@gmail.com"));
        employers.put("employer2",
                new Employer("employer2", "VNG", "vng.png", "http://vng.com.vn",
                        "vng@gmail.com"));
        employers.put("employer3",
                new Employer("employer3", "Viettel", "viettel.png", "https://vietteltelecom.vn",
                        "viettel@gmail.com"));
    }

    public List<Employer> getEmployers() {
        return employers.values().stream().toList();
    }

    public Employer getEmployerById(String id) {
        return employers.get(id);
    }

    public Employer createEmployer(Employer employer) {
        String id = UUID.randomUUID().toString();
        employer.setId(id);
        employers.put(id, employer);
        return employer;
    }

    public void updateEmployer(Employer employer) {
        employers.put(employer.getId(), employer);
    }

    public void updateLogo(String id, String logo_path) {
        var emp = employers.get(id);
        emp.setLogo_path(logo_path);
        employers.put(id, emp);
    }

    public Employer deleteEmployerById(String id) {
        return employers.remove(id);
    }

    public List<Employer> findByEmployer(String keyword) {
        List<Employer> employerList = employers.values().stream().
                filter(e -> e.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        return employerList;
    }

}
