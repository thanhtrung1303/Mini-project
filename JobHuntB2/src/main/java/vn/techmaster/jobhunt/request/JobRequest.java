package vn.techmaster.jobhunt.request;

import javax.validation.constraints.NotBlank;

import vn.techmaster.jobhunt.model.City;

/**
 * private String id;
 * private String emp_id;
 * private String title;
 * private String description;
 * private City city;
 */

public record JobRequest(
    String id,
    String emp_id,
        @NotBlank(message = "title cannot null") String title,
        @NotBlank(message = "description cannot null") String description,
    City city) {
}