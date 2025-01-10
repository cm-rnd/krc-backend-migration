package org.transform;

import org.domain.enums.ComplaintCategory;

import java.util.*;
import java.util.stream.Collectors;

public class ComplaintCategoryMapper {

    private static final Map<String, ComplaintCategory> inputToCategoryMap = new HashMap<>();

    static {
        for (ComplaintCategory category : ComplaintCategory.values()) {
            if (category.getParentCategory() != null) {
                String inputCode = generateCode(category);
                inputToCategoryMap.put(inputCode, category);
            }
        }
    }

    private static String generateCode(ComplaintCategory category) {
        StringBuilder codeBuilder = new StringBuilder();

        ComplaintCategory current = category;
        ComplaintCategory complaintCategory = current.getParentCategory().orElse(null);
        while (complaintCategory != null) {
            int orderIndex = complaintCategory.getChildCategories().indexOf(current);
            codeBuilder.insert(0, (char) ('A' + orderIndex));

            current = complaintCategory;
        }

        if (category.getChildCategories().isEmpty()) {
            codeBuilder.append(category.getOrder());
        }

        return codeBuilder.toString();
    }

    public static ComplaintCategory getCategoryByInput(String inputCode) {
        return inputToCategoryMap.get(inputCode);
    }
}
