from random import choice

from src.profile import Profile
from src.user import User
from src.selection import RandomRecipeSelector


class UserInitializer:
    def __init__(self):
        self.profile_categories = ['vegan', 'sporty', 'delbot', 'youth'] 
        self.initialized_profiles = None
        self.initialized_users = None

    def _get_vegan_profile(self):
        disliked_ingredients = ['poulet', 'boeuf', 'porc', 'oeuf', 'fromage', 'chèvre']
        liked_ingredients = ['tomate', 'courgette', 'carotte', 'potimarron',
            'poivron', 'aubergine', 'poire', 'banane', 'orange', 'ananas',
            'cerise', 'tofu', 'pois chiche', 'pomme', 'lentille', 'noix', 'citron', 
            'quorn', 'avocat', 'patates douces', 'lait de coco']

        return Profile(
            liked_ingredients,
            disliked_ingredients,
            'vegan')

    def _get_sporty_profile(self):
        disliked_ingredients = ['boeuf', 'choux', 'carotte', 'café', 'avocat', 'pâtes', 'sucre', 'beurre']
        liked_ingredients = ['poulet', 'veau', 'saumon', 'lapin', 'oeuf', 'lait', 'tomate', 
            'pain', 'pommes de terre', 'thon', 'pomme', 'betterave', 'maïs', 'mangue', 'ananas', 'pêche',
            'noisette', 'dinde']

        return Profile(
            liked_ingredients,
            disliked_ingredients, 
            'sporty')

    def _get_delbot_profile(self):
        disliked_ingredients = ['brocolis', 'porc', 'canelle', 'ail', 'coriande']
        liked_ingredients = ['pâtes', 'poulet', 'lardon', 'patates douces', 'potiron',
            'carotte', 'curry', 'céréales', 'gigembre', 'poireaux', 'beurre', 'pommes de terre', 
            'gruyère', 'fromage', 'oignon', 'tomate', 'chocolat', 'raisins', 'noisettes', 'amandes']

        return Profile(
            liked_ingredients,
            disliked_ingredients, 
            'delbot')

    def _get_youth_profile(self):
        disliked_ingredients = ['poireaux', 'épinard', 'poisson', 'courgette', 'navet', 
            'oignon', 'poivron', 'champignon', 'radis']
        liked_ingredients = ['pommes de terre', 'chocolat', 'sucre', 'beurre', 'pâtes', 
            'rhum', 'glace', 'yaourt', 'fromage', 'noix de coco', 'porc', 'boeuf', 
            'poulet', 'confiture', 'framboise', 'fraise']
        scoring_function = lambda recipe: (prep := recipe['preparation']) < 30 * prep != -1 

        return Profile(
            liked_ingredients,
            disliked_ingredients, 
            'youth',
            scoring_function)

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