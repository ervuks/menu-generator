package mavier.food.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mavier.food.menu.dto.MenuRequestDto;
import mavier.food.menu.dto.MenuResponseDto;
import mavier.food.menu.dto.RecipeRequestDto;
import mavier.food.menu.dto.RecipeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public interface RecipeSpecification {


    @Operation(
            method = "GET",
            summary = "Get all recipes",
            description = "Get all recipes",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Get all recipes",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RecipeResponseDto.class))))
            }
    )
    @ResponseStatus(code = OK)
    @Tag(name = "recipe-controller")
    ResponseEntity<List<RecipeResponseDto>> findAllRecipes();

    @Operation(
            method = "POST",
            summary = "Create new recipe",
            description = "Create new recipe",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Create new recipe",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RecipeResponseDto.class)))
            }
    )
    @ResponseStatus(code = CREATED)
    @Tag(name = "recipe-controller")
    ResponseEntity<RecipeResponseDto> addNewRecipe(@RequestBody RecipeRequestDto recipeRequestDto);

    @Operation(
            method = "POST",
            summary = "Create menu for desired days",
            description = "Creates menu from available recipes for desired days",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Create menu for desired days",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MenuResponseDto.class)))
            }
    )
    @ResponseStatus(code = OK)
    @Tag(name = "recipe-controller")
    ResponseEntity<MenuResponseDto> generateMenu(@RequestBody MenuRequestDto requestDto);
}
