import java.util.List;

public class Ingredients {
    //    "success": true,
    //    "data": []
    boolean success;
    List<IngredientsData> data;

    public Ingredients(){};

    public Ingredients(boolean success, List<IngredientsData> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<IngredientsData> getData() {
        return data;
    }

    public void setData(List<IngredientsData> data) {
        this.data = data;
    }
}
