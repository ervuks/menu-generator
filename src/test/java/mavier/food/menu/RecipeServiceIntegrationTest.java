package mavier.food.menu;

import mavier.food.menu.dto.MenuRequestDto;
import mavier.food.menu.dto.MenuResponseDto;
import mavier.food.menu.dto.RecipeRequestDto;
import mavier.food.menu.dto.RecipeResponseDto;
import mavier.food.menu.mappers.RecipeMapper;
import mavier.food.menu.model.Recipe;
import mavier.food.menu.repositories.RecipeRepository;
import mavier.food.menu.service.RecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceIntegrationTest {

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Test
    public void addNewRecipeTest() {
        RecipeRequestDto recipeRequestDto = getRecipeRequestDto();
        Recipe recipe = getRecipe(recipeRequestDto);
        when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(recipe);
        RecipeResponseDto outputRecipe = recipeService.addNewRecipe(recipeRequestDto);
        assertThat(outputRecipe).isNotNull();
    }

    @Test
    public void getAllRecipesTest() {
        when(recipeRepository.findAll()).thenReturn(getRecipeList());
        List<Recipe> allRecipes = recipeService.getAllRecipes();
        assertThat(allRecipes).hasSize(3);
    }

    @Test
    public void createMenuWithSingleRecipe() {
        MenuRequestDto menuRequestDto = getMenuRequestDto(1, 0);
        when(recipeRepository.findAll()).thenReturn(getRecipeList());

        MenuResponseDto menu = recipeService.createMenu(menuRequestDto);

        assertThat(menu.getMenus()).hasSize(1);
    }

    @Test
    public void createMenuWithSingleHomeAndSingleUrlRecipe() {
        MenuRequestDto menuRequestDto = getMenuRequestDto(2, 1);
        when(recipeRepository.findAll()).thenReturn(getRecipeList());
        MenuResponseDto menu = recipeService.createMenu(menuRequestDto);
        assertThat(menu.getMenus()).hasSize(2);
    }

    @Test
    public void createMenuWithSingleUrlRecipe() {
        MenuRequestDto menuRequestDto = getMenuRequestDto(1, 1);
        when(recipeRepository.findAll()).thenReturn(getRecipeList());
        MenuResponseDto menu = recipeService.createMenu(menuRequestDto);
        assertThat(menu.getMenus()).hasSize(1);
    }

    @Test
    public void recipeMapperTest() {
        List<Recipe> recipeList = getRecipeList();
        when(recipeRepository.findAll()).thenReturn(recipeList);
        assertThat(recipeService.getAllRecipeDtos()).hasSize(3);
        assertThat(recipeService.getAllRecipeDtos().get(0).getName()).isEqualTo(recipeList.get(0).getName());
    }

    private MenuRequestDto getMenuRequestDto(int totalRecipes, int urlRecipes) {
        return MenuRequestDto.builder()
                .recipeCount(totalRecipes)
                .urlRecipes(urlRecipes)
                .build();
    }

    private RecipeRequestDto getRecipeRequestDto() {
        List<HashMap<String, String>> ingredients = new ArrayList<>();

        HashMap<String, String> ingridentMap = new HashMap<>();
        ingridentMap.put("1", "Tomato");
        ingridentMap.put("2", "Water");

        ingredients.add(ingridentMap);

        return RecipeRequestDto.builder()
                .name("Tomato soup")
                .instructions("Build tomato soup")
                .url("http://some-recipe.com")
                .ingredients(ingredients)
                .build();
    }

    private Recipe getRecipe(RecipeRequestDto recipeRequestDto) {
        return Recipe.builder()
                    .instructions(recipeRequestDto.getInstructions())
                    .url(recipeRequestDto.getUrl())
                    .name(recipeRequestDto.getName())
                    .ingredients(recipeRequestDto.getIngredients())
                    .build();
    }

    private List<Recipe> getRecipeList() {
        Recipe r1 = Recipe.builder()
                .name("R1")
                .build();

        Recipe r2 = Recipe.builder()
                .name("R2")
                .build();

        Recipe r3 = Recipe.builder()
                .name("R3")
                .url("http://recipe-url.com")
                .build();

        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(r1);
        recipeList.add(r2);
        recipeList.add(r3);
        return recipeList;
    }

}
