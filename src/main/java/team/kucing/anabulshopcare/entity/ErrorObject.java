package team.kucing.anabulshopcare.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
//TODO :Remove Data Annotation Lombok

public class ErrorObject {

    private List<String> errorMessage;

    private Integer statusCode;

    private List<String> payload;

    private Date timestamp;
}