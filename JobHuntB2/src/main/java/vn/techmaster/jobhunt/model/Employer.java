package vn.techmaster.jobhunt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@Builder
public class Employer {
  @Id
  private String id;
  private String name;
  private String logo_path;
  private String website;
  private String email;
}
