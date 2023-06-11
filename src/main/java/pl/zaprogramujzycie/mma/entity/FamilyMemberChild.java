package pl.zaprogramujzycie.mma.entity;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class FamilyMemberChild extends FamilyMember {

    private int age;
    private float weight;
}
