package dev.mju.jobport.modules.recruitment.web;

import dev.mju.jobport.config.security.MemberDetails;
import dev.mju.jobport.modules.recruitment.application.BookmarkService;
import dev.mju.jobport.modules.recruitment.application.RecruitTypeService;
import dev.mju.jobport.modules.recruitment.application.RecruitmentFilterOptionService;
import dev.mju.jobport.modules.recruitment.application.RecruitmentService;
import dev.mju.jobport.modules.recruitment.domain.Recruitment;
import dev.mju.jobport.modules.recruitment.domain.RecruitmentAttachment;
import dev.mju.jobport.modules.recruitment.domain.RecruitmentAttachmentType;
import dev.mju.jobport.modules.recruitment.web.response.RecruitmentAttachmentView;
import dev.mju.jobport.modules.recruitment.web.response.SelectionStageGroupView;
import dev.mju.jobport.modules.recruitment.web.request.RecruitmentSearchCondition;
import dev.mju.jobport.modules.recruitment.web.request.RecruitmentStatusFilter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import org.hibernate.Hibernate;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recruitments")
public class RecruitmentController {
    private final RecruitmentService recruitmentService;
    private final BookmarkService bookmarkService;
    private final RecruitTypeService recruitTypeService;
    private final RecruitmentFilterOptionService recruitmentFilterOptionService;

    @GetMapping("")
    public String index(
            @ModelAttribute("searchCondition") RecruitmentSearchCondition searchCondition,
            Model model,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Recruitment> recruitmentPage = recruitmentService.search(searchCondition, pageable);

        model.addAttribute("recruitments", recruitmentPage.getContent());
        model.addAttribute("page", recruitmentPage);
        model.addAttribute("searchCondition", searchCondition);
        model.addAttribute("recruitTypes", recruitTypeService.getAvailableTypes());
        model.addAttribute("statusOptions", RecruitmentStatusFilter.values());
        model.addAttribute("workLocations", recruitmentFilterOptionService.getWorkLocations());
        model.addAttribute("employmentTypes", recruitmentFilterOptionService.getEmploymentTypes());
        model.addAttribute("educationLevels", recruitmentFilterOptionService.getEducationLevels());
        model.addAttribute("workFields", recruitmentFilterOptionService.getWorkFields());
        model.addAttribute("ncsList", recruitmentFilterOptionService.getNcsList());
        return "pages/recruitment/index";
    }

    @GetMapping("/{recruitmentId}")
    public String detail(
            @PathVariable long recruitmentId,
            @AuthenticationPrincipal MemberDetails memberDetails,
            Model model
    ) throws Exception {
        Recruitment recruitment = recruitmentService.find(recruitmentId);

        model.addAttribute("recruitment", recruitment);
        model.addAttribute(
                "attachmentViews",
                buildAttachmentViews(recruitmentId, getAttachmentOrNull(recruitment))
        );
        model.addAttribute(
                "selectionStageGroups",
                SelectionStageGroupView.fromStages(recruitment.getSelectionStages())
        );

        boolean bookmarked = false;
        if (memberDetails != null) {
            Long memberId = memberDetails.getMember().getId();
            bookmarked = bookmarkService.isBookmarked(recruitmentId, memberId);
        }
        model.addAttribute("bookmarked", bookmarked);
        return "pages/recruitment/detail";
    }

    @GetMapping("/{recruitmentId}/attachments/{attachmentType}/download")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable long recruitmentId,
            @PathVariable("attachmentType") String attachmentTypeKey
    ) throws Exception {
        RecruitmentAttachmentType attachmentType;
        try {
            attachmentType = RecruitmentAttachmentType.fromKey(attachmentTypeKey);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 첨부파일 유형입니다.");
        }
        Recruitment recruitment = recruitmentService.find(recruitmentId);
        var attachment = getAttachmentOrNull(recruitment);
        if (attachment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "첨부파일 정보가 없습니다.");
        }

        String filePath = attachmentType.getFilePath(attachment)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "요청한 첨부파일이 없습니다."
                ));

        if (isExternalUrl(filePath)) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(buildExternalUri(filePath))
                    .build();
        }

        Resource resource = loadLocalResource(filePath);
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "첨부파일을 찾을 수 없습니다.");
        }

        String filename = resource.getFilename();
        String encodedFilename = UriUtils.encode(
                filename != null ? filename : attachmentType.getKey(),
                StandardCharsets.UTF_8
        );

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + encodedFilename + "\""
                )
                .body(resource);
    }

    private List<RecruitmentAttachmentView> buildAttachmentViews(
            long recruitmentId,
            RecruitmentAttachment attachment
    ) {
        return Arrays.stream(RecruitmentAttachmentType.values())
                .map(type -> RecruitmentAttachmentView.of(recruitmentId, type, attachment))
                .toList();
    }

    private RecruitmentAttachment getAttachmentOrNull(Recruitment recruitment) {
        try {
            RecruitmentAttachment attachment = recruitment.getRecruitmentAttachment();
            if (attachment == null) {
                return null;
            }
            Hibernate.initialize(attachment);
            return (RecruitmentAttachment) Hibernate.unproxy(attachment);
        } catch (EntityNotFoundException ex) {
            return null;
        }
    }

    private boolean isExternalUrl(String filePath) {
        String lowerCase = filePath.toLowerCase(Locale.ROOT);
        return lowerCase.startsWith("http://") || lowerCase.startsWith("https://");
    }

    private URI buildExternalUri(String rawPath) {
        try {
            return UriComponentsBuilder.fromUriString(rawPath).build(true).toUri();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 첨부파일 경로입니다.");
        }
    }

    private Resource loadLocalResource(String filePath) throws MalformedURLException {
        Path resolvedPath = resolveLocalPath(filePath);
        return new UrlResource(resolvedPath.normalize().toUri());
    }

    private Path resolveLocalPath(String filePath) {
        String trimmedPath = filePath.startsWith("/uploads/")
                ? filePath.substring(1)
                : filePath;
        Path path = Paths.get(trimmedPath);
        if (path.isAbsolute()) {
            return path;
        }
        return Paths.get(System.getProperty("user.dir")).resolve(trimmedPath);
    }
}
