package com.backenEDS.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents the functional category of a service order.
 */
@Schema(
    description = "Type of service order requested by the station",
    example = "INVOICE"
)
public enum OrderType {

    /**
     * Order related to billing or invoice issues.
     */
    INVOICE,

    /**
     * Order related to technical or operational support.
     */
    SUPPORT,

    /**
     * Order related to redemption processes.
     */
    REDEMPTION
}
