package vn.techmaster.jobhunt.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.techmaster.jobhunt.model.Applicant;
import vn.techmaster.jobhunt.repository.ApplicantRepository;
import vn.techmaster.jobhunt.repository.EmployerRepository;
import vn.techmaster.jobhunt.repository.JobRepository;
import vn.techmaster.jobhunt.request.ApplicantRequest;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {
    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/list")
    public String applicantList(Model model) {
        model.addAttribute("applicants", applicantRepository.getApplicants());
        model.addAttribute("jobRepository", jobRepository);
        model.addAttribute("employerRepository", employerRepository);
        return "applicant_list";
    }

    @GetMapping("/update/{id}")
    public String updateApplicant(Model model, @PathVariable String id) {
        Applicant applicant = applicantRepository.getApplicantById(id);
        model.addAttribute("applicant", applicant);
        model.addAttribute("jobs", jobRepository.listjob());
        model.addAttribute("employerRepository", employerRepository);
        return "applicant_update";
    }

    @PostMapping("/update/{id}")
    public String UpdateApplicant(@PathVariable String id, @ModelAttribute ApplicantRequest applicantRequest) {
        Applicant applicant = new Applicant(id, applicantRequest.job_id(), applicantRequest.name(),
                applicantRequest.email(), applicantRequest.phone(), applicantRequest.skills());
        applicantRepository.updateApplicant(applicant);
        return "redirect:/applicant/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteApplicant(@PathVariable String id) {
        applicantRepository.deleteApplicantById(id);
        return "redirect:/applicant/list";
    }

    @GetMapping("/sendEmail/{id}")
    public String showForm(@PathVariable String id, Model model) {
        Applicant applicant = applicantRepository.getApplicantById(id);
        model.addAttribute("applicant", applicant);
        return "send_mail";
    }

    @PostMapping("/sendEmail/{id}")
    public String sendMail(@RequestParam("to") String to,
            @RequestParam("subject") String subject, @RequestParam("content") String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);
        return "sendMail_result";
    }

}
