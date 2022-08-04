package team.kucing.anabulshopcare.entity.subaddress;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Provinsi {

    @Id
    @Column(length = 10, name = "id")
    private String id;

    @Column(length = 32, name = "nama")
    private String nama;
}