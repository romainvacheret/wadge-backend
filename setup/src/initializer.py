from random import choice

from src.profile import Profile
from src.user import User
from src.selection import RandomRecipeSelector


class UserInitializer:
    def __init__(self):
        self.profile_categories = ['vegan']
        self.initialized_profiles = None
        self.initialized_users = None

    def _get_vegan_profile(self):
        vegan_disliked_ingredients = ['poulet', 'boeuf', 'porc', 'oeuf', 'fromage', 'chèvre']
        vegan_liked_ingredients = ['tomate', 'courgette', 'carotte', 'potimaron',
            'poivron', 'aubergine', 'poire', 'banane', 'orange', 'ananas',
            'cerise', 'tofu', 'pois chiche']

        return Profile(
            vegan_liked_ingredients,
            vegan_disliked_ingredients)

    def _initialize_profiles(self):
        return [getattr(self, f'_get_{category}_profile')() for category in self.profile_categories]

    def _initialize_users(self, user_count):
        return [User(str(count), choice(self.initialized_profiles), RandomRecipeSelector()) for count in range(user_count)]

    def initialize(self, recipes: list[dict], user_count: int=50) -> list[dict]:
        self.initialized_profiles = self._initialize_profiles()
        self.initialized_users = self._initialize_users(user_count)
        for user in self.initialized_users:
            user.score_recipes(recipes)
            user.select(recipes)

        return self.initialized_users
