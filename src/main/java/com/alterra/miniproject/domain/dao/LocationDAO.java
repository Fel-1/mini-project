package com.alterra.miniproject.domain.dao;

import com.alterra.miniproject.domain.base.BaseDAO;
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
@Table(name = "M_LOCATION")
@SQLDelete(sql = "UPDATE M_LOCATION SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class LocationDAO extends BaseDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kelurahan")
    private String kelurahan;

    @Column(name = "kecamatan")
    private String kecamatan;

    @Column(name = "kota")
    private String kota;

    @Column(name = "provinsi")
    private String provinsi;

}
