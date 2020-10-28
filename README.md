This project was created in the scope of personal home automation, to facilitate daily / weekly food menu ideas. 

The idea is the following: 

* Add your own and known food recipes (or just names);
* Add completely new recipes with reference link and more detailed information on how to make this food and what is needed for it;

Once recipes are stored, anyone can generate a random food menu for X meals with the possibility to include unknown recipes. 
Example:  
```
curl -X POST "http://localhost:9998/v1/recipes/menu" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"recipe_count\":2,\"url_recipes\":2}"
```
will return two unknown recipes  
```json
{
  "menu": [
    {
      "name": "R1",
      "ingredients": [
        {
          "1": "Some ingredient"
        }
      ],
      "url": "http://recipe-url.com/1",
      "instructions": "Hard to make"
    },
    {
      "name": "R2",
      "ingredients": [
        {
          "1": "Some ingredient"
        }
      ],
      "url": "http://recipe-url.com/2",
      "instructions": "How to make recipe Nr2"
    }
  ]
}
```

### Technical part

Application uses SpringBoot stack and MongoDB as database. 

To run application locally, use command:
```
./gradlew bootRun --args='--spring.profiles.active=local'
```

Note that SSL is enabled and certificates should be provided.

Build application docker image: 
```
./gradlew clean docker
```

Run application: 
```
docker run -e ENV=prod --name feeder -v <place_on_host>:/app/logs -d -p 443:9998 feeder:1.0
```