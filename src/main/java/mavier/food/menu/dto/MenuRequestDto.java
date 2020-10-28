package mavier.food.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuRequestDto {

    @JsonProperty("recipe_count")
    private int recipeCount;
    @JsonProperty("url_recipes")
    private int urlRecipes;

}
