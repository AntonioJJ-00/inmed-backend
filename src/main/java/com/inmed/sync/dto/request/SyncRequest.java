package com.inmed.sync.dto.request;

import com.inmed.sync.dto.SyncItem;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SyncRequest {

    @NotEmpty
    private List<SyncItem> items;

}

