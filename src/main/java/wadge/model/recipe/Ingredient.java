package wadge.model.recipe;

public class Ingredient {
    private String name;
    private String quantity;

    public Ingredient() {}

    public Ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public String getQuantity() { return quantity; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(String quantity) { this.quantity = quantity; }


    public enum Unit { KG, G, NONE }

    @Override
    public String toString() {
        return "Ingredient [name=" + name + ", quantity=" + quantity + "]";
    }

    public static Unit getUnit(String name) {
        Unit rtr = Unit.NONE;
        if(name.startsWith("kg de")) {
            rtr = Unit.KG;
        } else if(name.startsWith("g de")) {
            rtr = Unit.G;
        }

        return rtr;
    }

    public static String extractName(Ingredient ingredient) {
        String[] arr = ingredient.getName().split(" de ");
        return arr[arr.length - 1];
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ingredient other = (Ingredient) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public static void main(String[] args) {
        Ingredient i1 = new Ingredient("tomate", "12");
        Ingredient i2 = new Ingredient("kg de tomate", "1");
        Ingredient i3 = new Ingredient("g de tomate", "1000");

        System.out.println(Ingredient.extractName(i1));
        System.out.println(Ingredient.extractName(i2));
        System.out.println(Ingredient.extractName(i3));
    }
}
