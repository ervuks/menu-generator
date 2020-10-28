package mavier.food.menu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mavier.food.menu.dto.MenuRequestDto;
import mavier.food.menu.dto.MenuResponseDto;
import mavier.food.menu.dto.RecipeRequestDto;
import mavier.food.menu.dto.RecipeResponseDto;
import mavier.food.menu.mappers.RecipeMapper;
import mavier.food.menu.model.Recipe;
import mavier.food.menu.repositories.RecipeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static mavier.food.menu.mappers.RecipeMapper.toRecipeResponseDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeResponseDto addNewRecipe(RecipeRequestDto recipeRequestDto) {
        Recipe recipe = buildRecipe(recipeRequestDto);
        Recipe createdRecipe = recipeRepository.save(recipe);
        return toRecipeResponseDto(createdRecipe);
    }

    private Recipe buildRecipe(RecipeRequestDto recipeRequestDto) {
        return Recipe.builder()
                .name(recipeRequestDto.getName())
                .ingredients(recipeRequestDto.getIngredients())
                .url(recipeRequestDto.getUrl())
                .instructions(recipeRequestDto.getInstructions())
                .build();
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<RecipeResponseDto> getAllRecipeDtos() {
        return recipeRepository.findAll()
                .stream()
                .map(RecipeMapper::toRecipeResponseDto)
                .collect(Collectors.toList());
    }

    public MenuResponseDto createMenu(MenuRequestDto requestDto) {
        List<Recipe> recipes = getRecipes(requestDto);
        List<RecipeResponseDto> collect = getRecipeResponseList(recipes);
        return MenuResponseDto.builder()
                .menus(collect)
                .build();
    }

    private List<Recipe> getRecipes(MenuRequestDto requestDto) {
        List<Recipe> allRecipes = getAllRecipes();
        int homeRecipeCount = getHomeRecipeCount(requestDto);
        List<Recipe> recipes = getNRandomRecipes(allRecipes, requestDto.getUrlRecipes());
        List<Recipe> homeRecipes = getNRandomHomeRecipes(allRecipes, homeRecipeCount);
        recipes.addAll(homeRecipes);
        return recipes;
    }

    private int getHomeRecipeCount(MenuRequestDto requestDto) {
        return requestDto.getRecipeCount() - requestDto.getUrlRecipes();
    }

    private List<RecipeResponseDto> getRecipeResponseList(List<Recipe> recipes) {
        return recipes.stream()
                .map(RecipeMapper::toRecipeResponseDto)
                .collect(Collectors.toList());
    }

    private static List<Recipe> getNRandomRecipes(List<Recipe> list, int totalRecipes) {
        return new ArrayList<>(list).stream()
                .map(unused -> list.remove((int) (list.size() * Math.random())))
                .limit(totalRecipes)
                .collect(Collectors.toList());
    }

    private static List<Recipe> getNRandomHomeRecipes(List<Recipe> list, int recipeCount) {
        if (recipeCount > 0) {
            return new ArrayList<>(list).stream()
                    .filter(recipe -> StringUtils.isEmpty(recipe.getUrl()))
                    .map(unused -> list.remove((int) (list.size() * Math.random())))
                    .limit(recipeCount)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
