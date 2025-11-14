
package com.builderssas.api.infrastructure.persistence.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
@Table("construction_types")
public class ConstructionTypeEntity {
  @Id private Long id;
  private String name;
  private int estimatedDays;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String n){this.name=n;}
  public int getEstimatedDays(){return estimatedDays;} public void setEstimatedDays(int d){this.estimatedDays=d;}
}
