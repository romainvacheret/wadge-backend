from json import load

from src.initializer import UserInitializer

def load_recipes(filename: str) -> list[dict]:
	with open(filename, 'r') as file:
		return load(file)



recipes = load_recipes('./recipes.json')
user_initializer = UserInitializer()
users = user_initializer.initialize(recipes)

for user in users:
    print([(recipe_tuple[0], recipe_tuple[1]['id']) for recipe_tuple in user.selected_recipes])
