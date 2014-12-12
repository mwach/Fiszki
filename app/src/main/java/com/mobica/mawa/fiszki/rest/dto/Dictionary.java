package com.mobica.mawa.fiszki.rest.dto;

/**
 * Created by mawa on 08/12/14.
 */
public class Dictionary {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;

    private String uuid;
    private String name;
    private String description;

    private Long baseLanguageId;
    private Long refLanguageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBaseLanguageId() {
        return baseLanguageId;
    }

    public void setBaseLanguageId(Long baseLanguageId) {
        this.baseLanguageId = baseLanguageId;
    }

    public Long getRefLanguageId() {
        return refLanguageId;
    }

    public void setRefLanguageId(Long refLanguageId) {
        this.refLanguageId = refLanguageId;
    }
}
