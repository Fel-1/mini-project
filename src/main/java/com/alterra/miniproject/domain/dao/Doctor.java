package com.alterra.miniproject.domain.dao;

import com.alterra.miniproject.domain.common.BaseDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "M_DOCTOR")
@SQLDelete(sql = "UPDATE M_DOCTOR SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Doctor extends BaseDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private Character gender;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "experience")
    private Integer experience;

}
