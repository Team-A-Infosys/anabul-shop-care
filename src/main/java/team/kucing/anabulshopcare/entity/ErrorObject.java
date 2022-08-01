package team.kucing.anabulshopcare.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ErrorObject {

    private Integer statusCode;

    private List<String> errorMessage;

    private Date timestamp;
}