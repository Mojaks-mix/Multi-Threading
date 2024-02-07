public enum Color {
    BLACK("\033[0;30m"), RED("\033[0;91m"), YELLOW("\033[0;93m"),
    GREEN("\033[0;92m"), BLUE("\033[0;96m"), WHITE("\033[0;97m"),
    //background colors
    BLACK_B("\033[40m"), BLACK_BRIGHT_B("\033[100m"), RED_B("\033[101m"), YELLOW_B("\033[103m"),
    GREEN_B("\033[102m"), BLUE_B("\033[106m"), WHITE_B("\033[107m"), RESET("\033[92;40m");
    private String sColor;

    private Color(String c){sColor = c;}

    @Override
    public String toString() {return sColor;}
}
