package com.apartmentseller.apartmentseller.services.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnnouncementNotFoundException extends RuntimeException {
}
