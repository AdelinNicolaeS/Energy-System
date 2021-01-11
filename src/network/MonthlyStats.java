package network;

import java.util.ArrayList;

public class MonthlyStats {
    private int month;
    private ArrayList<Integer> ids = new ArrayList<>();

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }
}
