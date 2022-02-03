import requests

from json import load

from src.initializer import UserInitializer

def load_recipes(filename: str) -> list[dict]:
	with open(filename, 'r') as file:
			return load(file)

if __name__ == '__main__':
	recipes = load_recipes('./recipes.json')
	user_initializer = UserInitializer()
	users = user_initializer.initialize(recipes)
	users_as_dict = [user.to_dict() for user in users]

	requests.post('http://localhost:8080/users', json=users_as_dict)