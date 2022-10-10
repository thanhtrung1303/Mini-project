package vn.techmaster.jobhunt.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import vn.techmaster.jobhunt.model.City;
import vn.techmaster.jobhunt.model.Job;

@Repository
public class JobRepository {

    private ConcurrentHashMap<String, Job> jobs;

    public JobRepository() {
        jobs = new ConcurrentHashMap<>();
        jobs.put("Jb1",
                new Job("Jb1", "employer1", "Web-design", "Lập trình Font-End",
                        City.HoChiMinh, LocalDateTime.now(), LocalDateTime.now()));
        jobs.put("Jb2",
                new Job("Jb2", "employer2", "Java-programing", "Lập trình Back-End",
                        City.HaNoi, LocalDateTime.now(), LocalDateTime.now()));
        jobs.put("Jb3",
                new Job("Jb3", "employer3", "Fullstack", "Lập trình Fullstack",
                        City.HaiPhong, LocalDateTime.now(), LocalDateTime.now()));

    }

    public List<Job> listjob() {
        return jobs.values().stream().toList();
    }

    public Job getById(String id) {
        return jobs.get(id);
    }

    public void addJob(Job job) {
        jobs.put(job.getId(), job);
    }

    public void deleteJobById(String id) {
        jobs.remove(id);
    }

    public void updateJob(Job job) {
        jobs.put(job.getId(), job);
    }

    public List<Job> findByKeyword(String keyword) {
        List<Job> jobList = jobs.values().stream()
                .filter(j -> j.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || j.getCity().label.toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
        return jobList;
    }
}
