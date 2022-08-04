package team.kucing.anabulshopcare.repository.subrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;

@Repository
public interface ProvinsiRepository extends JpaRepository<Provinsi, String> {
}