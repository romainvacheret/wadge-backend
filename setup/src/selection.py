from abc import ABC, abstractmethod
from random import randint, sample
from typing import Tuple


class RecipeSelector(ABC):
    def __init__(self, recipe_count=40, threshold=5):
        self.recipe_count = recipe_count + randint(-threshold, threshold + 1)

    @abstractmethod
    def select(self, recipes: list[dict]) -> list[dict]:
        pass


class RandomRecipeSelector(RecipeSelector):
    def __init__(self, recipe_count=40, threshold=5):
        super().__init__(recipe_count, threshold)

    # recipes -> (recipe_score, recipe)
    def select(self, recipes: list[Tuple[int, dict]]) -> list[dict]:
        return sample(recipes, self.recipe_count)

