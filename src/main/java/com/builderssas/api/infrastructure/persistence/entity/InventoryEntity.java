package com.builderssas.api.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad persistente para el stock vivo.
 */
@Table("inventory")
public class InventoryEntity {

    @Id
    @Column("id")
    private final Long id;

    @Column("material_id")
    private final Long materialId;

    @Column("stock")
    private final Double stock;

    @Column("updated_at")
    private final LocalDateTime updatedAt;

    public InventoryEntity(Long id, Long materialId, Double stock, LocalDateTime updatedAt) {
        this.id = id;
        this.materialId = materialId;
        this.stock = stock;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getMaterialId() { return materialId; }
    public Double getStock() { return stock; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
