package com.builderssas.api.domain.model.userrole;

import java.time.LocalDateTime;

public record UserRoleRecord(
        Long id,
        Long userId,
        Long roleId,
        LocalDateTime assignedAt,
        Boolean active
) {}
