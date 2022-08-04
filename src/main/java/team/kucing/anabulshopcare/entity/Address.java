package team.kucing.anabulshopcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.kucing.anabulshopcare.entity.subaddress.Kecamatan;
import team.kucing.anabulshopcare.entity.subaddress.Kelurahan;
import team.kucing.anabulshopcare.entity.subaddress.Kota;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserApp userApp;

    @OneToOne
    private Provinsi provinsi;

    @OneToOne
    private Kota kota;

    @OneToOne
    private Kecamatan kecamatan;

    @OneToOne
    private Kelurahan kelurahan;
}