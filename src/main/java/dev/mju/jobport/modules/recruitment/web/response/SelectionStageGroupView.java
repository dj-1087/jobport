package dev.mju.jobport.modules.recruitment.web.response;

import dev.mju.jobport.modules.recruitment.domain.SelectionStage;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record SelectionStageGroupView(String applyFieldLabel, List<SelectionStage> stages) {

    public static List<SelectionStageGroupView> fromStages(List<SelectionStage> stages) {
        if (stages == null || stages.isEmpty()) {
            return List.of();
        }

        Map<String, List<SelectionStage>> grouped = stages.stream()
                .collect(
                        LinkedHashMap::new,
                        (map, stage) -> {
                            String key = stage != null && StringUtils.hasText(stage.getApplyField())
                                    ? stage.getApplyField()
                                    : "";
                            map.computeIfAbsent(key, ignored -> new java.util.ArrayList<>()).add(stage);
                        },
                        (left, right) -> right.forEach((k, v) ->
                                left.merge(k, v, (l, r) -> {
                                    l.addAll(r);
                                    return l;
                                }))
                );

        return grouped.entrySet().stream()
                .map(entry -> new SelectionStageGroupView(
                        StringUtils.hasText(entry.getKey()) ? entry.getKey() : "공통 지원",
                        entry.getValue()
                ))
                .toList();
    }
}
