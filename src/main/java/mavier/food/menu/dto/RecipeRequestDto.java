package mavier.food.menu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class RecipeRequestDto {

    private String name;
    private List<HashMap<String, String>> ingredients;
    private String url;
    private String instructions;

}
