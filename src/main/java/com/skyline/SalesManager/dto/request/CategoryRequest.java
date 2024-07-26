package com.skyline.SalesManager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank(message = "Category name is not blank")
    @Pattern(regexp = "^[a-zA-Z ]*$")
    private String categoryName;
}
