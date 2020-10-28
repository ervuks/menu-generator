package mavier.food.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import mavier.food.menu.controller.RecipeController;
import mavier.food.menu.dto.MenuRequestDto;
import mavier.food.menu.dto.MenuResponseDto;
import mavier.food.menu.dto.RecipeRequestDto;
import mavier.food.menu.dto.RecipeResponseDto;
import mavier.food.menu.mappers.RecipeMapper;
import mavier.food.menu.model.Recipe;
import mavier.food.menu.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private static final String BASE_URL = "/v1/recipes";

    @Test
    void getAllRecipesTest() throws Exception {
        given(this.recipeService.getAllRecipeDtos())
                .willReturn(getRecipeList());

        ResultActions resultActions = this.mockMvc.perform(get(BASE_URL + "/").secure(true)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        List<RecipeResponseDto> response = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(response).hasSize(3);
    }

    @Test
    void addNewLocalRecipeTest() throws Exception {
        String recipeName = "Local recipe";
        createNewRecipe(recipeName);
    }

    @Test
    void addNewLocalUtf8RecipeTest() throws Exception {
        String recipeName = "Tomātu biezzupa ar kraukšķiem";
        createNewRecipe(recipeName);
    }

    @Test
    void createMenuTest() throws Exception {
        MenuRequestDto menuRequest = MenuRequestDto.builder()
                .recipeCount(3)
                .urlRecipes(1)
                .build();

        MenuResponseDto menuResponse = MenuResponseDto.builder()
                .menus(getRecipeList())
                .build();

        when(recipeService.createMenu(any(MenuRequestDto.class))).thenReturn(menuResponse);

        MvcResult resultActions = this.mockMvc.perform(post(BASE_URL + "/menu")
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJsonString(menuRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = resultActions.getResponse().getContentAsString();
        MenuResponseDto response = objectMapper.readValue(contentAsString, MenuResponseDto.class);
        assertThat(response.getMenus()).hasSize(3);
    }

    private void createNewRecipe(String recipeName) throws Exception {
        given(this.recipeService.addNewRecipe(RecipeRequestDto.builder()
                .name(recipeName)
                .build()))
                .willReturn(RecipeResponseDto.builder()
                        .name(recipeName)
                        .build());

        mockMvc.perform(post(BASE_URL + "/")
                .secure(true)
                .contentType(APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON)
                .content(getLocalUtf8Recipe(recipeName)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(recipeName)));

    }

    private String toJsonString(Object o) throws JsonProcessingException {
        ObjectWriter ow = objectMapper.writer()
                .withDefaultPrettyPrinter();
        return ow.writeValueAsString(o);
    }

    private String getLocalUtf8Recipe(String recipeName) throws JsonProcessingException {
        return getRecipeString(recipeName);
    }

    private String getRecipeString(String recipeName) throws JsonProcessingException {
        RecipeRequestDto localRecipe = RecipeRequestDto.builder()
                .name(recipeName)
                .build();
        return toJsonString(localRecipe);
    }

    private List<RecipeResponseDto> getRecipeList() {
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
        return recipeList.stream().map(RecipeMapper::toRecipeResponseDto)
                .collect(Collectors.toList());
    }
}
