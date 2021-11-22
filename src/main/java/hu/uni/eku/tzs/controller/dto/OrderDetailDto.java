package hu.uni.eku.tzs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    @Pattern(regexp = "^[0-9]+", message = "invalid order number")
    private Integer orderNumber;

    @Valid
    private String orderDate;

    private String requiredDate;

    private String shippedDate;

    private String status;

    private String comments;

    private Integer customerNumber;
}
