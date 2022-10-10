package vn.techmaster.jobhunt.request;

import java.util.List;


import vn.techmaster.jobhunt.model.Skill;

/**
 * Thêm sửa xoá Applicant:
 * - Chọn job từ danh sách job hiện có
 * - Họ và tên
 * - Email
 * - Điện thoại
 * - Mô tả kỹ năng
 */

public record ApplicantRequest(
        String id,
        String job_id,
        String name,
        String email,
        String phone,
        List<Skill> skills) {
}