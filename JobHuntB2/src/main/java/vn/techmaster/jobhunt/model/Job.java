package vn.techmaster.jobhunt.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
@Entity

public class Job {
  @Id
  private String id;
  private String emp_id;
  private String title;
  private String description;

  @Column(name = "city")
  @Enumerated(EnumType.STRING)
  private City city;
  
  private LocalDateTime updated_at;
  private LocalDateTime created_at;
  
}
