package mavier.food.menu.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

@Builder
@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "recipes")
@ToString
public class Recipe {

    @Id
    private String id;
    private String name;
    private List<HashMap<String, String>> ingredients;
    private String url;
    private String instructions;

}
