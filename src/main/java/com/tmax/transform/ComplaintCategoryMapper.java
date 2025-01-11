package com.tmax.transform;

import com.tmax.domain.enums.ComplaintCategory;
import java.util.*;

public class ComplaintCategoryMapper {
    private static final Map<String, ComplaintCategory> inputToCategoryMap = new HashMap<>();

    static {
        for (ComplaintCategory category : ComplaintCategory.values()) {
            String inputCode = generateCode(category);
            inputToCategoryMap.put(inputCode, category);
        }
    }

    private static String generateCode(ComplaintCategory category) {
        StringBuilder codeBuilder = new StringBuilder();

        ComplaintCategory current = category;
        while (current.getParentCategory().isPresent()) {

            int orderIndex = current.getParentCategory().get().getChildCategories().indexOf(current);
            if(current.getChildCategories().isEmpty()) {
                codeBuilder.append(category.getParentCategory().get().getChildCategories().indexOf(current)+1);
            } else {
                codeBuilder.insert(0, (char) ('A' + orderIndex));
            }

            current = current.getParentCategory().get();
        }

        return codeBuilder.toString();
    }

    public static ComplaintCategory getCategoryByInput(String inputCode) {
        return inputToCategoryMap.get(inputCode);
    }
}
