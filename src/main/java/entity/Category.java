package entity;

public enum Category {
    ROCK("Rock"),
    POP("Pop"),
    ELECTRONIC("Electronic"),
    COUNTRY("Country"),
    REGGAE("Reggae"),
    POLKA("Polka"),
    HIP_HOP("Hip hop"),
    CLASSIC("Classic");


    private final String text;

    Category(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static Category getCategoryByText(String text){
        if(text != null) {
            Category category;
            StringBuilder builder = new StringBuilder();
            String[] textWithoutSpaces = text.split(" ");
            for (String s : textWithoutSpaces)
                builder.append(s);
            switch (builder.toString().toLowerCase()) {
                case "hiphop":
                case "hip-hop":
                    category = HIP_HOP;
                    break;
                default:
                    try {
                        category = Category.valueOf(text.toUpperCase());
                        break;
                    } catch (IllegalArgumentException e){
                        return null;
                    }
            }
            return category;
        } else return null;
    }
}
