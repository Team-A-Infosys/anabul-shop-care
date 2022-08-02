package team.kucing.anabulshopcare.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    private Boolean isDeleted;

    @Override
    public String toString(){
        return "Category{"+
                "categoryId="+ categoryId +
                ", categoryName="+categoryName+
                ", isDeleted="+isDeleted+
                "}";
    }

}
