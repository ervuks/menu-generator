package mavier.food.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {

    @JsonProperty("menu")
    private List<RecipeResponseDto> menus;
}