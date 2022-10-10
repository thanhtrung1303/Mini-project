package vn.techmaster.jobhunt.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.techmaster.jobhunt.model.Applicant;
import vn.techmaster.jobhunt.model.Job;
import vn.techmaster.jobhunt.repository.ApplicantRepository;
import vn.techmaster.jobhunt.repository.EmployerRepository;
import vn.techmaster.jobhunt.repository.JobRepository;
import vn.techmaster.jobhunt.request.ApplicantRequest;
import vn.techmaster.jobhunt.request.JobRequest;

@Controller
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @GetMapping("/list")
    public String listJob(Model model, String keyword) {
        model.addAttribute("employerRepository", employerRepository);
        if (keyword != null) {
            model.addAttribute("jobs", jobRepository.findByKeyword(keyword));
        } else {
            model.addAttribute("jobs", jobRepository.listjob());
        }
        return "job_list";
    }

    @GetMapping("/update/{id}")
    public String getUpdateJob(Model model, @PathVariable String id) {
        Job job = jobRepository.getById(id);
        model.addAttribute("job", job);
        model.addAttribute("employers", employerRepository.getEmployers());
        return "job_update";
    }

    @PostMapping("/update/{id}")
    public String updateJob(@PathVariable String id, @ModelAttribute JobRequest request) {
        LocalDateTime preUpdateTime = jobRepository.getById(id).getCreated_at();
        Job job = new Job(id, request.emp_id(), request.title(), request.description(), request.city(),
                preUpdateTime, LocalDateTime.now());
        jobRepository.updateJob(job);
        return "redirect:/job/list";
    }

    @GetMapping("/add")
    public String createNewJob(Model model) {
        model.addAttribute("addJob", new Job());
        model.addAttribute("employers", employerRepository.getEmployers());
        return "job_add";
    }

    @PostMapping("/add")
    public String addNewJob(@ModelAttribute JobRequest request) {
        String uuid = UUID.randomUUID().toString();
        Job job = new Job(uuid, request.emp_id(), request.title(), request.description(), request.city(),
                LocalDateTime.now(), LocalDateTime.now());
        jobRepository.addJob(job);
        return "redirect:/job/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable String id) {
        jobRepository.deleteJobById(id);
        return "redirect:/job/list";
    }

    @GetMapping("/apply/{job_id}")
    public String applyJob(Model model, @PathVariable String job_id) {
        Job job = jobRepository.getById(job_id);

        model.addAttribute("applicant", new Applicant());
        model.addAttribute("job", job);
        return "applicant_add";
    }

    @PostMapping("/apply/{job_id}")
    public String AddApplicant(@ModelAttribute ApplicantRequest applicantRequest)
    {
    String uuid = UUID.randomUUID().toString();
    Applicant applicant = new Applicant(uuid, applicantRequest.job_id(),
    applicantRequest.name(),
    applicantRequest.email(), applicantRequest.phone(),
    applicantRequest.skills());
    applicantRepository.createApplicant(applicant);
    return "redirect:/applicant/list";
    }

}
