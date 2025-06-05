package thara.restaurant_pos.payload.request;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public class CreateCategoryRequest {
    private String name;
    private Integer sort_order;

    public CreateCategoryRequest() {
    }

    public CreateCategoryRequest(String name, Integer sort_order) {
        this.name = name;
        this.sort_order = sort_order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort_order() {
        return sort_order;
    }

    public void setSort_order(Integer sort_order) {
        this.sort_order = sort_order;
    }
}
