package vn.techmaster.jobhunt.request;

import vn.techmaster.jobhunt.model.City;

public record SearchRequest(String keyword, City city) {
}