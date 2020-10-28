package mavier.food.menu.mappers;

import mavier.food.menu.dto.RecipeResponseDto;
import mavier.food.menu.model.Recipe;

public class RecipeMapper {

    private RecipeMapper() {
        throw new IllegalStateException("Mapper class");
    }

    public static RecipeResponseDto toRecipeResponseDto(Recipe recipe) {
        return RecipeResponseDto.builder()
                .name(recipe.getName())
                .ingredients(recipe.getIngredients())
                .instructions(recipe.getInstructions())
                .url(recipe.getUrl())
                .build();
    }
}
