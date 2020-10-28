package mavier.food.menu.controller;

import lombok.AllArgsConstructor;
import mavier.food.menu.RecipeSpecification;
import mavier.food.menu.dto.MenuRequestDto;
import mavier.food.menu.dto.MenuResponseDto;
import mavier.food.menu.dto.RecipeRequestDto;
import mavier.food.menu.dto.RecipeResponseDto;
import mavier.food.menu.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/recipes")
@RestController
@AllArgsConstructor
public class RecipeController implements RecipeSpecification {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeResponseDto>> findAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipeDtos());
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDto> addNewRecipe(@RequestBody RecipeRequestDto recipeRequestDto) {
        return new ResponseEntity<>(recipeService.addNewRecipe(recipeRequestDto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/menu")
    public ResponseEntity<MenuResponseDto> generateMenu(@RequestBody MenuRequestDto requestDto) {
        return ResponseEntity.ok(recipeService.createMenu(requestDto));
    }
}
