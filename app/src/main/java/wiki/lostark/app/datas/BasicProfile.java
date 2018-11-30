package wiki.lostark.app.datas;

public class BasicProfile {
    private String name;
    private String value;
    private int primaryColor;

    public BasicProfile(String name, String value, int primaryColor) {
        this.name = name;
        this.value = value;
        this.primaryColor = primaryColor;
    }

    public static BasicProfile init(String name, String value, int primaryColor) {
        BasicProfile basicProfile = new BasicProfile(name, value, primaryColor);
        return basicProfile;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}