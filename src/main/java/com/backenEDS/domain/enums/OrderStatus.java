package com.backenEDS.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents the lifecycle status of a service order.
 *
 * Business rules:
 * - DONE cannot transition back to IN_PROGRESS.
 * - CANCELLED is a terminal state.
 */
@Schema(
    description = "Current lifecycle status of the service order",
    example = "CREATED"
)
public enum OrderStatus {

    /**
     * Order has been created but not yet processed.
     */
    CREATED,

    /**
     * Order is currently being processed.
     */
    IN_PROGRESS,

    /**
     * Order has been successfully completed.
     */
    DONE,

    /**
     * Order has been cancelled.
     * This is a terminal state.
     */
    CANCELLED
}