# Recipe Tracker App

Management of cooking recipes in one convenient place. 

It aims to simplify meal organization, help track nutritional data and make it easy to adjust recipes for different serving sizes.

## Features

- Add, edit, and delete recipes
- Track nutritional information for each recipe
- Adjust recipe quantities for different serving sizes
- Search for recipes by name
- Filter recipes by tags
- CameraX for capturing recipe photos
- User-configurable light and dark theme

## Images

### Onboarding

<img width="300" height="700" alt="image" src="https://github.com/user-attachments/assets/bab59146-e67a-444c-8b1d-40982624c696" />

### Home Without Recipes

<img width="300" height="700" alt="image" src="https://github.com/user-attachments/assets/db8044db-9f41-4a51-abe1-b844a1a3c9f5" />

### Add Recipe

<img width="300" height="700" alt="image" src="https://github.com/user-attachments/assets/466b0c3b-5e9c-45d0-b4f8-b56072c70022" />

### View Recipe

<img width="300" height="700" alt="image" src="https://github.com/user-attachments/assets/c2ad6fb2-e7e3-4fe4-839f-05b36b0d10f8" />

### Settings (Dark Theme)

<img width="300" height="700" alt="image" src="https://github.com/user-attachments/assets/4db5f21a-b318-44a7-b1b4-782a08ec0d3d" />

### Home (Dark Theme)

<img width="300" height="700" alt="image" src="https://github.com/user-attachments/assets/2e3fa541-3add-4319-aa22-52b4382f9dc6" />

## Test Cases

### 1. RecipeFormStateTest

#### Test: `validate returns false when title is blank`
- **Given:** Empty title field
- **When:** Form validation is triggered
- **Expected:** Validation fails, error flag is set

#### Test: `validate returns false when servings is invalid`
- **Given:** Non-numeric servings value
- **When:** Form validation is triggered
- **Expected:** Validation fails

#### Test: `validate returns true when title and servings are valid`
- **Given:** Valid title and numeric servings
- **When:** Form validation is triggered
- **Expected:** Validation succeeds

#### Test: `toRecipe creates valid recipe entity`
- **Given:** Complete form with valid data
- **When:** Converting form to RecipeEntity
- **Expected:** All fields correctly mapped, tags split properly

#### Test: `toRecipe filters out blank ingredients`
- **Given:** Mix of valid and empty ingredients
- **When:** Converting form to RecipeEntity
- **Expected:** Only non-empty ingredients included

---

### 2. IngredientInputTest

#### Test: `toIngredientEntity converts valid input correctly`
- **Given:** Valid ingredient with name, amount, unit
- **When:** Converting to IngredientEntity
- **Expected:** Numeric conversion successful, all fields mapped

#### Test: `toIngredientEntity handles invalid amount`
- **Given:** Non-numeric amount value
- **When:** Converting to IngredientEntity
- **Expected:** Amount becomes null, other fields preserved

---

### 3. RecipeRepositoryTest

#### Test: `insertAndRetrieveRecipe`
- **Given:** New recipe with ingredients and instructions
- **When:** Insert and retrieve from database
- **Expected:** Recipe persisted with all data intact

#### Test: `updateRecipe`
- **Given:** Existing recipe in database
- **When:** Modify and update recipe
- **Expected:** Changes persisted, ID unchanged

#### Test: `deleteRecipe`
- **Given:** Existing recipe in database
- **When:** Delete recipe
- **Expected:** Recipe no longer retrievable

#### Test: `getRecipeById`
- **Given:** Multiple recipes in database
- **When:** Query by specific ID
- **Expected:** Correct recipe returned

---

### 4. AddRecipeScreenTest

#### Test: `addRecipeScreen_displaysFormElements`
- **Given:** Screen is rendered
- **Expected:** All key form fields visible (title, description, servings, etc.)

#### Test: `addRecipeScreen_canEnterTitle`
- **Given:** Screen is rendered
- **When:** User types in title field
- **Expected:** Text appears in UI

#### Test: `addRecipeScreen_saveButtonExists`
- **Given:** Screen is rendered
- **Expected:** Save button is present and clickable
