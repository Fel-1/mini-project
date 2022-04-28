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
@Table(name = "M_FACILITY")
@SQLDelete(sql = "UPDATE M_FACILITY SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Facility extends BaseDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private FacilityType facilityType;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "address")
    private String address;

    @Column(name = "map_url")
    private String mapUrl;

    @Column(name = "website_url")
    private String websiteUrl;

}
