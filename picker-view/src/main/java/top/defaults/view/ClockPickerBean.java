package top.defaults.view;

import java.util.List;

public class ClockPickerBean implements Division {

    private String name;

    public ClockPickerBean(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Division> getChildren() {
        return null;
    }

    @Override
    public Division getParent() {
        return null;
    }

    @Override
    public String getText() {
        return this.name;
    }
}
