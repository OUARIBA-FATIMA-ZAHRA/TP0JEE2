package com.example.inventory_management.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "product_categories",
        uniqueConstraints = @UniqueConstraint(columnNames = "category_ref"),
        indexes = {@Index(name = "idx_category_ref", columnList = "category_ref")})
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @Column(name = "category_ref", nullable = false, length = 30)
    private String categoryRef;

    @Column(name = "category_title", nullable = false, length = 150)
    private String categoryTitle;

    @Column(length = 1000)
    private String description;

    @Column(name = "is_enabled")
    private Boolean enabled = true;

    @Column(name = "sort_priority")
    private Integer sortPriority = 0;

    @Column(name = "parent_ref_id")
    private Long parentRefId;

    @Column(name = "created_timestamp", updatable = false)
    private LocalDateTime createdTimestamp;

    @Column(name = "modified_timestamp")
    private LocalDateTime modifiedTimestamp;

    public ItemCategory() {
        this.createdTimestamp = LocalDateTime.now();
        this.modifiedTimestamp = LocalDateTime.now();
    }

    public ItemCategory(String categoryRef, String categoryTitle, String description) {
        this();
        this.categoryRef = categoryRef;
        this.categoryTitle = categoryTitle;
        this.description = description;
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedTimestamp = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (enabled == null) enabled = true;
        if (sortPriority == null) sortPriority = 0;
        if (createdTimestamp == null) createdTimestamp = LocalDateTime.now();
        if (modifiedTimestamp == null) modifiedTimestamp = LocalDateTime.now();
    }

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public String getCategoryRef() { return categoryRef; }
    public void setCategoryRef(String categoryRef) { this.categoryRef = categoryRef; }
    public String getCategoryTitle() { return categoryTitle; }
    public void setCategoryTitle(String categoryTitle) { this.categoryTitle = categoryTitle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public Integer getSortPriority() { return sortPriority; }
    public void setSortPriority(Integer sortPriority) { this.sortPriority = sortPriority; }
    public Long getParentRefId() { return parentRefId; }
    public void setParentRefId(Long parentRefId) { this.parentRefId = parentRefId; }
    public LocalDateTime getCreatedTimestamp() { return createdTimestamp; }
    public void setCreatedTimestamp(LocalDateTime createdTimestamp) { this.createdTimestamp = createdTimestamp; }
    public LocalDateTime getModifiedTimestamp() { return modifiedTimestamp; }
    public void setModifiedTimestamp(LocalDateTime modifiedTimestamp) { this.modifiedTimestamp = modifiedTimestamp; }

    public String getFormattedCreationDate() {
        if (createdTimestamp != null) {
            return createdTimestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
        return "N/A";
    }
}