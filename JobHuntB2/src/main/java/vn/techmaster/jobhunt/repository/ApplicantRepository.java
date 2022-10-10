package vn.techmaster.jobhunt.repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import vn.techmaster.jobhunt.model.Applicant;
import vn.techmaster.jobhunt.model.Skill;

@Repository
public class ApplicantRepository {
    ConcurrentHashMap<String, Applicant> applicants;

    public ApplicantRepository() {
        applicants = new ConcurrentHashMap<>();
        applicants.put("App1", new Applicant("App1", "Jb1", "Nguyễn Văn A", "nva@mail.com", "0923456789",
                List.of(Skill.CSharp, Skill.SQL)));
        applicants.put("App2", new Applicant("App2", "Jb2", "Nguyễn Thị B", "ntb@mail.com", "0923452129",
                List.of(Skill.Java, Skill.CSharp)));
        applicants.put("App3", new Applicant("App3", "Jb3", "Nguyễn Văn C", "nvc@mail.com", "0923167892",
                List.of(Skill.CSharp, Skill.SQL, Skill.AWS)));
        applicants.put("App3", new Applicant("App3", "Jb3", "Nguyễn Thị D", "ntd@mail.com", "093167892",
                List.of(Skill.CSharp, Skill.SQL, Skill.AWS, Skill.Java)));
    }

    public List<Applicant> getApplicants() {
        return applicants.values().stream().toList();
    }

    public Applicant getApplicantById(String id) {
        return applicants.get(id);
    }

    public void createApplicant(Applicant applicant) {
        applicants.put(applicant.getId(), applicant);
    }

    public void updateApplicant(Applicant applicant) {
        applicants.put(applicant.getId(), applicant);
    }

    public void deleteApplicantById(String id) {
        applicants.remove(id);
    }
}
