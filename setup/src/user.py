from typing import NoReturn

from src.profile import Profile
from src.selection import RecipeSelector


class User:
    def __init__(self, name: str, profile: Profile, selector: RecipeSelector):
        self.name = name
        self.profile = profile
        self.selector = selector
        self.scored_recipe = None
        self.selected_recipes = None

    def score_recipes(self, recipes: list[dict]) -> NoReturn:
        self.scored_recipe = self.profile.score_recipes(recipes)

    def select(self, recipes: list[dict]) -> NoReturn:
        self.selected_recipes = self.selector.select(self.scored_recipe)

    def to_dict(self) -> dict:
        return {
            'name': self.name,
            'recipes': self.selected_recipes,
            'category': self.profile.name.upper()
        }